package view.caja;

import java.util.Scanner;

public class SelectorTarjetaView {
    public String pedirUltimosDigitos() {
        System.out.print("Ingrese los últimos 4 dígitos de la tarjeta: ");
        return new Scanner(System.in).nextLine();
    }
}