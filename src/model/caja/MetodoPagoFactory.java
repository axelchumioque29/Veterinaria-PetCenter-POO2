package model.caja;
public class MetodoPagoFactory {
    public static MetodoPago crearMetodoPago(String tipo) {
        switch (tipo.toUpperCase()) {
            case "EFECTIVO": return new PagoEfectivo();
            case "TARJETA": return new PagoTarjeta();
            case "YAPE": return new PagoYape();
            case "PLIN": return new PagoPlin();
            default: throw new IllegalArgumentException("Método de pago no soportado: " + tipo);
        }
    }
}