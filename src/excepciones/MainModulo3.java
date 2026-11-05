package excepciones;

import model.caja.Cajero;
import controller.caja.ControladorCaja;
import view.caja.MenuCajaView;
import excepciones.AuditoriaFinanciera; // Asumiendo que guardaste AuditoriaFinanciera aquí
import java.util.Scanner;

public class MainModulo3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== INICIANDO SISTEMA DE FACTURACION ===");

        System.out.print("Ingrese ID del Cajero: ");
        String idCajero = scanner.nextLine();
        System.out.print("Ingrese Nombre del Cajero: ");
        String nombreCajero = scanner.nextLine();

        Cajero cajeroTurno = new Cajero(idCajero, nombreCajero);

        System.out.print("Ingrese el saldo inicial en caja (S/): ");
        double saldoInicial = scanner.nextDouble();

        ControladorCaja controladorCaja = new ControladorCaja();
        controladorCaja.iniciarTurno(cajeroTurno, saldoInicial);

        AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja con S/ " + saldoInicial);

        MenuCajaView menu = new MenuCajaView();
        int opcion = 0;

        while (opcion != 4) {
            opcion = menu.mostrarMenuYCapturarOpcion();

            switch (opcion) {
                case 1:
                    System.out.println("Preparando entorno para nuevo cobro...");
                    break;
                case 2:
                    System.out.println("Cerrando caja...");
                    controladorCaja.getCajaActual().cerrarCaja();
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja ejecutado. Saldo final: S/ " + controladorCaja.getCajaActual().getSaldoFinal());
                    break;
                case 3:
                    System.out.println("Generando reportes de ingresos...");
                    break;
                case 4:
                    System.out.println("Saliendo del Modulo de Caja...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        scanner.close();
    }
}