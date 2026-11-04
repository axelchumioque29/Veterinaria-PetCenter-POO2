package view.caja;

public class TicketView {
    public void generarVistaTicket(String idTransaccion, double montoPagado, String metodo) {
        System.out.println("--- TICKET DE PAGO ---");
        System.out.println("Tx: " + idTransaccion);
        System.out.println("Método: " + metodo);
        System.out.println("Monto Pagado: S/ " + montoPagado);

    }
}