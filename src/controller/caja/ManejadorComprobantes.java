package controller.caja;

public class ManejadorComprobantes {
    private int correlativoBoleta = 1;
    private int correlativoFactura = 1;

    public String generarNumeroBoleta() {
        return String.format("%06d", correlativoBoleta++);
    }

    public String generarNumeroFactura() {
        return String.format("%06d", correlativoFactura++);
    }
}