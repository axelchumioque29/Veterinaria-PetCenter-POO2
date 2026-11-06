package view.clientes;

import java.util.Scanner;

public class ConsolaUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return valor;
    }

    public static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingrese un valor decimal válido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        return valor;
    }

    public static boolean leerBooleano(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextBoolean()) {
            System.out.print("Ingrese un valor válido (true o false): ");
            scanner.next();
        }
        boolean valor = scanner.nextBoolean();
        scanner.nextLine(); // Limpiar buffer
        return valor;
    }
}