package model.caja;

public class PagoPlin implements MetodoPago {
    @Override public boolean procesarPago(double monto) { return true; }
    @Override public String getTipo() { return "Plin"; }
}