package view.caja;

import java.util.Scanner;

public class MenuCajaView {
    public int mostrarMenuYCapturarOpcion() {
        System.out.println("\n=== MÓDULO DE CAJA Y FACTURACIÓN ===");
        System.out.println("1. Registrar Nuevo Cobro");
        System.out.println("2. Cerrar Caja Diaria");
        System.out.println("3. Generar Reportes");
        System.out.println("4. Abrir Nuevo Turno");
        System.out.println("5. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        return new Scanner(System.in).nextInt();
    }
}