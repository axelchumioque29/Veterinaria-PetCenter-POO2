package view.caja;

import java.util.Scanner;

public class VistaMetodoPagoView {
    public String solicitarMetodoPago() {
        System.out.println("Seleccione el Método de Pago:");
        System.out.println("[1] Efectivo  ");
        System.out.println("[2] Tarjeta  ");
        System.out.println("[3] Yape  ");
        System.out.println("[4] Plin");
        System.out.print("Opción: ");
        int opcion = new Scanner(System.in).nextInt();

        switch (opcion) {
            case 1:
                return "EFECTIVO";
            case 2:
                return "TARJETA";
            case 3:
                return "YAPE";
            case 4:
                return "PLIN";
            default:
                return "EFECTIVO";
        }
    }
}