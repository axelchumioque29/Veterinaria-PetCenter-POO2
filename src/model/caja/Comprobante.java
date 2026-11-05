package model.caja;

import controller.caja.CalculadorIGV;

public abstract class Comprobante {
    protected String serie;
    protected String numero;
    protected DetalleComprobante detalle;
    protected Moneda moneda;

    // El total final a pagar (con IGV incluido)
    public double getTotal() {
        return detalle.calcularTotal();
    }


    public double getSubtotalBase() {
        return CalculadorIGV.extraerSubtotal(getTotal());
    }

    // Calcula el IGV sobre ese valor base
    public double getMontoIGV() {
        return CalculadorIGV.calcularIGV(getSubtotalBase());
    }
}