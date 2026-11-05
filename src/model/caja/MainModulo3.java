package model.caja;

import controller.caja.ControladorCaja;
import view.caja.MenuCajaView;
import model.clientes.Cliente;
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

        // Registro de auditoría
        AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja con S/ " + saldoInicial);

        MenuCajaView menu = new MenuCajaView();
        int opcion = 0;

        // 2. MENÚ PRINCIPAL
        while (opcion != 4) {
            opcion = menu.mostrarMenuYCapturarOpcion();

            switch (opcion) {
                case 1:
                    // OPCIÓN 1: REGISTRAR COBRO
                    System.out.println("\n--- REGISTRO DE NUEVO COBRO ---");
                    scanner.nextLine(); // Limpiar el buffer del scanner

                    System.out.print("Ingrese ID de la transacción (Ej: TX-001): ");
                    String idTx = scanner.nextLine();

                    System.out.print("Ingrese método de pago (EFECTIVO, TARJETA, YAPE, PLIN): ");
                    String tipoPago = scanner.nextLine();

                    // Simulamos los datos de los módulos 1 y 2
                    Cliente clientePrueba = new Cliente("76543210", "Juan Perez", "987654321", "Av. San Ignacio 123");
                    model.caja.DetalleComprobante detalle = new model.caja.DetalleComprobante();
                    model.caja.ConceptoCobro concepto = new model.caja.ConceptoCobro("SRV-TEST", "Consulta Médica General", 150.0);
                    detalle.agregarItem(new model.caja.ItemVenta(concepto, 1));

                    model.caja.Comprobante boletaTest = new model.caja.Boleta("B001", "000001", detalle, model.caja.Moneda.PEN, clientePrueba);


                    controladorCaja.registrarCobro(idTx, boletaTest, tipoPago);
                    break;

                case 2:
                    // OPCIÓN 2: CERRAR CAJA DIARIA
                    System.out.println("\n--- CERRANDO CAJA ---");
                    controladorCaja.getCajaActual().cerrarCaja();
                    double saldoCierre = controladorCaja.getCajaActual().getSaldoFinal();

                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja ejecutado. Saldo final: S/ " + saldoCierre);
                    System.out.println("Caja cerrada exitosamente. Ya no se admiten nuevos cobros.");
                    break;

                case 3:
                    // OPCIÓN 3: GENERAR REPORTES (Lectura dinámica en tiempo real)
                    System.out.println("\n=== REPORTE DE CAJA DIARIA ===");
                    model.caja.CajaDiaria caja = controladorCaja.getCajaActual();

                    System.out.println("Cajero a cargo : " + cajeroTurno.getNombre() + " (ID: " + cajeroTurno.getId() + ")");
                    System.out.println("Estado de caja : " + caja.getEstado());
                    System.out.println("Saldo Inicial  : S/ " + caja.getSaldoInicial());
                    System.out.println("Saldo Final    : S/ " + caja.getSaldoFinal());

                    double ingresos = caja.getSaldoFinal() - caja.getSaldoInicial();
                    System.out.println("Ingresos netos : S/ " + ingresos);
                    System.out.println("==============================\n");
                    break;

                case 4:
                    // OPCIÓN 4: SALIR DEL MÓDULO
                    System.out.println("\nSaliendo del Modulo de Caja... ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("\nOpcion invalida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}