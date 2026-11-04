package model.caja;

public class PagoTarjeta implements MetodoPago {
    @Override public boolean procesarPago(double monto) { return true; }
    @Override public String getTipo() { return "Tarjeta"; }
}