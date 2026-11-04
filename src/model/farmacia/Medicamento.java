package model.farmacia;

import java.util.ArrayList;

public class Medicamento extends ProductoFarmacia {
    private final String principioActivo;
    private int stockMinimo;
    private final ArrayList<LoteMedicamento> lotes = new ArrayList<>();

    public Medicamento(String codigo, String nombre, double precio, String principioActivo, int stockMinimo) {
        super(codigo, nombre, precio);
        this.principioActivo = validarTexto(principioActivo);
        setStockMinimo(stockMinimo);
    }

    @Override
    public String getTipo() { return "Medicamento"; }
    public String getPrincipioActivo() { return principioActivo; }
    public synchronized int getStockMinimo() { return stockMinimo; }
    public synchronized void setStockMinimo(int stockMinimo) {
        if (stockMinimo < 0) throw new IllegalArgumentException("El mínimo no puede ser negativo.");
        this.stockMinimo = stockMinimo;
    }

    public synchronized void registrarLote(LoteMedicamento lote) {
        if (lote == null) throw new IllegalArgumentException("Indique el lote.");
        for (LoteMedicamento existente : lotes) {
            if (existente.getCodigo().equals(lote.getCodigo())) {
                throw new IllegalArgumentException("El lote ya existe.");
            }
        }
        lotes.add(lote.copiar());
    }

    public synchronized ArrayList<LoteMedicamento> getLotes() {
        ArrayList<LoteMedicamento> copia = new ArrayList<>();
        for (LoteMedicamento lote : lotes) copia.add(lote.copiar());
        return copia;
    }

    public synchronized long getStockDisponible() {
        long total = 0;
        for (LoteMedicamento lote : lotes) {
            if (!lote.estaVencido()) total += lote.getCantidad();
        }
        return total;
    }

    public synchronized boolean tieneStockBajo() { return getStockDisponible() <= stockMinimo; }

    public synchronized void agregarStock(String codigoLote, int cantidad) throws MedicamentoVencidoException {
        for (LoteMedicamento lote : lotes) {
            if (lote.getCodigo().equals(codigoLote)) {
                if (lote.estaVencido()) throw new MedicamentoVencidoException("No se repone un lote vencido.");
                lote.agregar(cantidad);
                return;
            }
        }
        throw new IllegalArgumentException("No existe el lote.");
    }

    public synchronized void descontarStock(int cantidad)
            throws StockInsuficienteException, MedicamentoVencidoException {
        if (cantidad <= 0) throw new IllegalArgumentException("Ingrese una cantidad positiva.");
        if (getStockDisponible() < cantidad) {
            long vencido = 0;
            for (LoteMedicamento lote : lotes) {
                if (lote.estaVencido()) vencido += lote.getCantidad();
            }
            if (getStockDisponible() == 0 && vencido > 0) {
                throw new MedicamentoVencidoException("Solo quedan unidades vencidas.");
            }
            throw new StockInsuficienteException("No hay suficientes unidades vigentes.");
        }
        lotes.sort((primero, segundo) -> primero.getFechaVencimiento().compareTo(segundo.getFechaVencimiento()));
        int pendiente = cantidad;
        for (LoteMedicamento lote : lotes) {
            if (!lote.estaVencido() && pendiente > 0 && lote.getCantidad() > 0) {
                int salida = Math.min(pendiente, lote.getCantidad());
                lote.descontar(salida);
                pendiente -= salida;
            }
        }
    }
}

