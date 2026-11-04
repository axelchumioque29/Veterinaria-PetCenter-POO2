package model.caja;

public class PagoYape implements MetodoPago {
    @Override public boolean procesarPago(double monto) { return true; }
    @Override public String getTipo() { return "Yape"; }
}