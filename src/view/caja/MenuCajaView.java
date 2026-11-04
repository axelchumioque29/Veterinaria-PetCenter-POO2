package view.caja;

import java.util.Scanner;

public class MenuCajaView {
    public int mostrarMenuYCapturarOpcion() {
        System.out.println("=== MÓDULO DE CAJA Y FACTURACIÓN ===");
        System.out.println("1. Registrar Nuevo Cobro");
        System.out.println("2. Cerrar Caja Diaria");
        System.out.println("3. Generar Reportes");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        return new Scanner(System.in).nextInt();
    }
}