package controller.caja;

public class ProcesadorEfectivo {
    public double calcularVuelto(double montoEntregado, double totalAPagar) {
        if (montoEntregado < totalAPagar) {
            throw new IllegalArgumentException("El monto entregado es insuficiente.");
        }
        return montoEntregado - totalAPagar;
    }
}