package controller.caja;

public class CalculadorIGV {
    private static final double TASA_IGV = 0.18;

    public static double calcularIGV(double subtotal) {
        return subtotal * TASA_IGV;
    }

    public static double extraerSubtotal(double totalConIGV) {
        return totalConIGV / (1 + TASA_IGV);
    }
}