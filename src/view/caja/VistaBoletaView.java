package view.caja;

public class VistaBoletaView {
    public String generarTextoBoleta(String serie, String numero, String cliente, String concepto, String pago, double base, double igv, double total) {
        return String.format(
                "       BOLETA ELECTRONICA\n" +
                        " Nro: %s-%s\n" +
                        " Cliente  : %s\n" +
                        " Concepto : %s (%s)\n" +
                        "-----------------------------------\n" +
                        " Valor Base (Sin IGV): S/ %.2f\n" +
                        " IGV (18%%)           : S/ %.2f\n" +
                        " TOTAL A PAGAR       : S/ %.2f\n" +
                        "===================================\n",
                serie, numero, cliente, concepto, pago, base, igv, total
        );
    }
}