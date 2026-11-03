package model.caja;

public interface MetodoPago {
    boolean procesarPago(double monto);
    String getTipo();
}