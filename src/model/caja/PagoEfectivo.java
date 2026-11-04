package model.caja;

public class PagoEfectivo implements MetodoPago {
    @Override public boolean procesarPago(double monto) { return true; }
    @Override public String getTipo() { return "Efectivo"; }
}