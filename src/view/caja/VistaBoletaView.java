package view.caja;

public class VistaBoletaView {
    public void renderizarBoleta(String serie, String numero, String datosCliente, double total) {

        System.out.println("       BOLETA ELECTRÓNICA          ");
        System.out.println(" Nro: " + serie + "-" + numero);
        System.out.println(" " + datosCliente);
        System.out.println("-----------------------------------");
        System.out.println(" TOTAL A PAGAR: S/ " + total);
        System.out.println("===================================");
    }
}