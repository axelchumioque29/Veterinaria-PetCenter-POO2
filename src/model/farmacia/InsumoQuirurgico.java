package model.farmacia;

public class InsumoQuirurgico extends ProductoFarmacia {
    private final boolean esteril;
    private int cantidad;

    public InsumoQuirurgico(String codigo, String nombre, double precio, boolean esteril) {
        super(codigo, nombre, precio);
        this.esteril = esteril;
    }

    @Override
    public String getTipo() { return "Insumo quirúrgico"; }
    public boolean isEsteril() { return esteril; }
    public synchronized int getCantidad() { return cantidad; }
    public synchronized void agregarStock(int unidades) {
        if (unidades <= 0 || unidades > Integer.MAX_VALUE - cantidad) {
            throw new IllegalArgumentException("Cantidad inválida.");
        }
        cantidad += unidades;
    }
    public synchronized void descontarStock(int unidades) throws StockInsuficienteException {
        if (unidades <= 0) throw new IllegalArgumentException("Ingrese una cantidad positiva.");
        if (unidades > cantidad) throw new StockInsuficienteException("Insumos insuficientes.");
        cantidad -= unidades;
    }
}

