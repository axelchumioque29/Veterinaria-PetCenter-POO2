package model.farmacia;

import java.time.LocalDate;
import java.util.Objects;

public class LoteMedicamento {
    private final String codigo;
    private final LocalDate fechaVencimiento;
    private int cantidad;

    public LoteMedicamento(String codigo, LocalDate fechaVencimiento, int cantidad) {
        this.codigo = ProductoFarmacia.validarTexto(codigo);
        this.fechaVencimiento = Objects.requireNonNull(fechaVencimiento, "Indique el vencimiento.");
        if (cantidad < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        this.cantidad = cantidad;
    }

    public String getCodigo() { return codigo; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public synchronized int getCantidad() { return cantidad; }
    public boolean estaVencido() { return fechaVencimiento.isBefore(LocalDate.now()); }
    public synchronized EstadoLote getEstado() {
        if (estaVencido()) return EstadoLote.VENCIDO;
        return cantidad == 0 ? EstadoLote.AGOTADO : EstadoLote.DISPONIBLE;
    }

    public synchronized void agregar(int unidades) {
        if (unidades <= 0 || unidades > Integer.MAX_VALUE - cantidad) {
            throw new IllegalArgumentException("Cantidad inválida.");
        }
        cantidad += unidades;
    }

    public synchronized void descontar(int unidades)
            throws StockInsuficienteException, MedicamentoVencidoException {
        if (unidades <= 0) throw new IllegalArgumentException("Ingrese una cantidad positiva.");
        if (estaVencido()) throw new MedicamentoVencidoException("El lote " + codigo + " está vencido.");
        if (unidades > cantidad) throw new StockInsuficienteException("Stock insuficiente en " + codigo);
        cantidad -= unidades;
    }

    public synchronized LoteMedicamento copiar() {
        return new LoteMedicamento(codigo, fechaVencimiento, cantidad);
    }
}

