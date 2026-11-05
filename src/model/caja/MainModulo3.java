package model.caja;

import model.caja.Cajero;
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
        scanner.nextLine(); // Limpiar buffer

        ControladorCaja controladorCaja = new ControladorCaja();
        controladorCaja.iniciarTurno(cajeroTurno, saldoInicial);

        AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja con S/ " + saldoInicial);

        MenuCajaView menu = new MenuCajaView();
        int opcion = 0;

        while (opcion != 5) {
            opcion = menu.mostrarMenuYCapturarOpcion();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- REGISTRO DE NUEVO COBRO ---");

                    if (controladorCaja.getCajaActual().getEstado() == model.caja.EstadoCaja.CERRADA) {
                        System.err.println("Caja cerrada. Elija la opción 4 para abrir una nueva caja.");
                        break;
                    }

                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese ID de la transacción (Ej: TX-001): ");
                    String idTx = scanner.nextLine();

                    System.out.print("Ingrese método de pago (EFECTIVO, TARJETA, YAPE, PLIN): ");
                    String tipoPago = scanner.nextLine();

                    System.out.print("Ingrese la descripción del servicio o producto: ");
                    String descripcionServicio = scanner.nextLine();

                    System.out.print("Ingrese el monto total a cobrar (S/): ");
                    double montoCobro = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar buffer


                    Cliente clientePrueba = new Cliente("76543210", "Juan Perez", "987654321", "Av. San Ignacio 123");
                    model.caja.DetalleComprobante detalle = new model.caja.DetalleComprobante();
                    model.caja.ConceptoCobro concepto = new model.caja.ConceptoCobro("SRV-VET", descripcionServicio, montoCobro);
                    detalle.agregarItem(new model.caja.ItemVenta(concepto, 1));

                    model.caja.Comprobante boletaTest = new model.caja.Boleta("B001", "000001", detalle, model.caja.Moneda.PEN, clientePrueba);

                    controladorCaja.registrarCobro(idTx, boletaTest, tipoPago);
                    break;

                case 2:
                    System.out.println("\n--- CERRANDO CAJA ---");
                    controladorCaja.getCajaActual().cerrarCaja();
                    double saldoCierre = controladorCaja.getCajaActual().getSaldoFinal();
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja. Saldo final: S/ " + saldoCierre);
                    System.out.println("Caja cerrada. Ya no se admiten nuevos cobros.");
                    break;

                case 3:
                    System.out.println("\n=== REPORTE DE CAJA DIARIA ===");
                    model.caja.CajaDiaria caja = controladorCaja.getCajaActual();
                    System.out.println("Cajero a cargo : " + cajeroTurno.getNombre() + " (ID: " + cajeroTurno.getId() + ")");
                    System.out.println("Estado de caja : " + caja.getEstado());
                    System.out.println("Saldo Inicial  : S/ " + caja.getSaldoInicial());
                    System.out.println("Saldo Final    : S/ " + caja.getSaldoFinal());
                    double ingresos = caja.getSaldoFinal() - caja.getSaldoInicial();
                    System.out.println("Ingresos netos : S/ " + ingresos);

                    break;

                case 4:
                    System.out.println("\n--- ABRIENDO NUEVO TURNO ---");
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese ID del Nuevo Cajero: ");
                    String nuevoId = scanner.nextLine();
                    System.out.print("Ingrese Nombre del Nuevo Cajero: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Ingrese el nuevo saldo inicial en caja (S/): ");
                    double nuevoSaldo = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar buffer

                    cajeroTurno = new Cajero(nuevoId, nuevoNombre);
                    controladorCaja.iniciarTurno(cajeroTurno, nuevoSaldo);
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja con S/ " + nuevoSaldo);
                    break;

                case 5:
                    System.out.println("\nSaliendo del Modulo de Caja");
                    break;

                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}