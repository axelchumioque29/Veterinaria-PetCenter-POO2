package view.clientes;

// Importaciones del Módulo 1
import controller.clientes.ControladorCita;
import controller.clientes.ControladorCliente;
import controller.clientes.ControladorMascota;
import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;

// Importaciones del Módulo 2
import controller.farmacia.ClinicaFacade;
import controller.farmacia.CoordinadorFarmacia;
import controller.farmacia.MainModulo2;

// Importaciones del Módulo 3
import controller.caja.ControladorCaja;
import controller.caja.CalculadorIGV;
import model.caja.Cajero;
import view.caja.MenuCajaView;
import model.caja.AuditoriaFinanciera;
import model.caja.EstadoCaja;

import java.util.Scanner;

public class MainSistemaIntegrado {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // modulo recepcion
        RepositorioGenerico<Cliente> repoClientes = new RepositorioGenerico<>();
        RepositorioGenerico<Mascota> repoMascotas = new RepositorioGenerico<>();
        RepositorioGenerico<Cita> repoCitas = new RepositorioGenerico<>();

        ControladorCliente ctrlCliente = new ControladorCliente(repoClientes);
        ControladorMascota ctrlMascota = new ControladorMascota(repoMascotas);
        ControladorCita ctrlCita = new ControladorCita(repoCitas);
        VeterinariaFacade facadeAxel = new VeterinariaFacade(ctrlCliente, ctrlMascota, ctrlCita);

        //modulo clinico
        ClinicaFacade facadeFabricio = MainModulo2.crearDemostracion();

        // modulo caja
        System.out.println("=== CONFIGURACIÓN INICIAL DE CAJA ===");
        System.out.print("Ingrese ID del Cajero: ");
        String idCajero = scanner.nextLine();
        System.out.print("Ingrese Nombre del Cajero: ");
        String nombreCajero = scanner.nextLine();
        System.out.print("Ingrese el saldo inicial en caja (S/): ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        Cajero cajeroTurno = new Cajero(idCajero, nombreCajero);
        ControladorCaja controladorCaja = new ControladorCaja();
        controladorCaja.iniciarTurno(cajeroTurno, saldoInicial);

        AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja inicial con S/ " + saldoInicial);

        // menu global
        int opcionPrincipal = 0;
        while (opcionPrincipal != 4) {
            System.out.println("\n=== SISTEMA VETERINARIO INTEGRADO ===");
            System.out.println("1. Módulo de Recepción (Clientes y Citas )");
            System.out.println("2. Módulo Clínico y Farmacia (Inventario y Recetas)");
            System.out.println("3. Módulo de Facturación (Caja y Cobros)");
            System.out.println("4. Apagar Sistema");
            System.out.print("Seleccione un módulo para operar: ");
            opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            if (opcionPrincipal == 1) {
                view.clientes.MenuPrincipalView menuAxel = new view.clientes.MenuPrincipalView(facadeAxel);
                menuAxel.mostrar();
            } else if (opcionPrincipal == 2) {
                new CoordinadorFarmacia(facadeFabricio).iniciar();
            } else if (opcionPrincipal == 3) {
                ejecutarModuloCaja(controladorCaja, cajeroTurno, repoClientes);
            }
        }
        System.out.println("\nApagando sistema veterinario");
        scanner.close();
    }

    private static void ejecutarModuloCaja(ControladorCaja controladorCaja, Cajero cajeroTurno, RepositorioGenerico<Cliente> repoClientes) {
        Scanner scanner = new Scanner(System.in);
        MenuCajaView menu = new MenuCajaView();
        int opcion = 0;

        while (opcion != 5) {
            opcion = menu.mostrarMenuYCapturarOpcion();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- REGISTRO DE NUEVO COBRO ---");
                    if (controladorCaja.getCajaActual().getEstado() == EstadoCaja.CERRADA) {
                        System.err.println("Caja cerrada. Elija la opción 4 para abrir una nueva caja.");
                        break;
                    }

                    System.out.print("Ingrese DNI del cliente a cobrar: ");
                    String dniBusqueda = scanner.nextLine();

                    Cliente clienteReal = repoClientes.buscar(c -> c.getDni().equals(dniBusqueda));

                    if (clienteReal == null) {
                        System.err.println("Error: Cliente no encontrado. Registre al cliente en el Módulo 1 primero.");
                        break;
                    }

                    System.out.print("Ingrese ID de la transacción (Ej: TX-001): ");
                    String idTx = scanner.nextLine();
                    System.out.print("Ingrese método de pago (EFECTIVO, TARJETA, YAPE, PLIN): ");
                    String tipoPago = scanner.nextLine();

                    System.out.print("Ingrese descripción del concepto: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingrese monto a cobrar (S/): ");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();

                    model.caja.DetalleComprobante detalle = new model.caja.DetalleComprobante();
                    model.caja.ConceptoCobro concepto = new model.caja.ConceptoCobro("VTA-001", descripcion, monto);
                    detalle.agregarItem(new model.caja.ItemVenta(concepto, 1));

                    model.caja.Comprobante boleta = new model.caja.Boleta("B001", "000001", detalle, model.caja.Moneda.PEN, clienteReal);

                    controladorCaja.registrarCobro(idTx, boleta, tipoPago);

                    System.out.println("\n--- DESGLOSE DEL COMPROBANTE ---");
                    System.out.printf("Valor Base (Sin IGV) : S/ %.2f\n", boleta.getSubtotalBase());
                    System.out.printf("IGV (18%%)            : S/ %.2f\n", boleta.getMontoIGV());
                    System.out.printf("Total Cobrado        : S/ %.2f\n", boleta.getTotal());

                    break;

                case 2:
                    System.out.println("\n--- CERRANDO CAJA ---");
                    controladorCaja.getCajaActual().cerrarCaja();

                    // Auditoría activa al cerrar caja desde el menú general
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja. Saldo final: S/ " + controladorCaja.getCajaActual().getSaldoFinal());
                    System.out.println("Caja cerrada correctamente. Ya no se admiten cobros.");
                    break;

                case 3:
                    System.out.println("\n=== REPORTE DE CAJA DIARIA ===");
                    model.caja.CajaDiaria caja = controladorCaja.getCajaActual();
                    double ingresosTotales = caja.getSaldoFinal() - caja.getSaldoInicial();

                    double gananciaNeta = CalculadorIGV.extraerSubtotal(ingresosTotales);
                    double igvAcumulado = CalculadorIGV.calcularIGV(gananciaNeta);

                    System.out.println("Cajero a cargo     : " + cajeroTurno.getNombre() + " (ID: " + cajeroTurno.getId() + ")");
                    System.out.println("Estado de caja     : " + caja.getEstado());
                    System.out.println("Saldo Inicial      : S/ " + caja.getSaldoInicial());
                    System.out.println("Saldo Final        : S/ " + caja.getSaldoFinal());
                    System.out.println("------------------------------");
                    System.out.printf("Total Recaudado    : S/ %.2f\n", ingresosTotales);
                    System.out.printf("IGV a declarar     : S/ %.2f\n", igvAcumulado);
                    System.out.printf("Ganancia Neta Real : S/ %.2f\n", gananciaNeta);

                    break;

                case 4:
                    System.out.println("\n--- ABRIENDO NUEVO TURNO ---");
                    System.out.print("Ingrese ID del Nuevo Cajero: ");
                    String nuevoId = scanner.nextLine();
                    System.out.print("Ingrese Nombre del Nuevo Cajero: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Ingrese el nuevo saldo inicial en caja (S/): ");
                    double nuevoSaldo = scanner.nextDouble();
                    scanner.nextLine();

                    cajeroTurno = new Cajero(nuevoId, nuevoNombre);
                    controladorCaja.iniciarTurno(cajeroTurno, nuevoSaldo);

                    // Auditoría activa al cambiar de turno
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de nuevo turno con S/ " + nuevoSaldo);
                    break;

                case 5:
                    System.out.println("\nVolviendo al menú principal del sistema...");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
            }
        }
    }
}