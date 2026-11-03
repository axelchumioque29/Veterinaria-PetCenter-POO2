package model.caja;

public class ItemVenta {
    private ConceptoCobro concepto;
    private int cantidad;

    public ItemVenta(ConceptoCobro concepto, int cantidad) {
        this.concepto = concepto;
        this.cantidad = cantidad;
    }
    public double getSubtotal() { return concepto.getPrecioUnitario() * cantidad; }
}