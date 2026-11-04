package model.caja;

public abstract class Comprobante {
    protected String serie;
    protected String numero;
    protected DetalleComprobante detalle;
    protected Moneda moneda;

    public double getTotal() { return detalle.calcularTotal(); }
}