package view.clientes;



import controller.clientes.ControladorCita;
import controller.clientes.ControladorCliente;
import controller.clientes.ControladorMascota;
import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;

import controller.caja.ControladorCaja;
import model.caja.Cajero;
import view.caja.MenuCajaView;
import model.caja.AuditoriaFinanciera;

import java.util.Scanner;

public class MainSistemaIntegrado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // modulo clientes

        RepositorioGenerico<Cliente> repoClientes = new RepositorioGenerico<>();
        RepositorioGenerico<Mascota> repoMascotas = new RepositorioGenerico<>();
        RepositorioGenerico<Cita> repoCitas = new RepositorioGenerico<>();

        ControladorCliente ctrlCliente = new ControladorCliente(repoClientes);
        ControladorMascota ctrlMascota = new ControladorMascota(repoMascotas);
        ControladorCita ctrlCita = new ControladorCita(repoCitas);

        VeterinariaFacade facadeAxel = new VeterinariaFacade(ctrlCliente, ctrlMascota, ctrlCita);


        // modulo caja

        System.out.println("=== CONFIGURACIÓN INICIAL DE CAJA ===");
        System.out.print("Ingrese ID del Cajero: ");
        String idCajero = scanner.nextLine();
        System.out.print("Ingrese Nombre del Cajero: ");
        String nombreCajero = scanner.nextLine();
        System.out.print("Ingrese el saldo inicial en caja (S/): ");
        double saldoInicial = scanner.nextDouble();

        Cajero cajeroTurno = new Cajero(idCajero, nombreCajero);
        ControladorCaja controladorCaja = new ControladorCaja();
        controladorCaja.iniciarTurno(cajeroTurno, saldoInicial);
        AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja con S/ " + saldoInicial);


        // modulo integrado

        int opcionPrincipal = 0;
        while (opcionPrincipal != 3) {
            System.out.println("\n=== SISTEMA VETERINARIO INTEGRADO ===");
            System.out.println("1. Módulo de Recepción (Clientes y Citas)");
            System.out.println("2. Módulo de Facturación (Caja y Cobros)");
            System.out.println("3. Apagar Sistema");
            System.out.print("Seleccione un módulo: ");
            opcionPrincipal = scanner.nextInt();

            if (opcionPrincipal == 1) {

                view.clientes.MenuPrincipalView menuAxel = new view.clientes.MenuPrincipalView(facadeAxel);
                menuAxel.mostrar();

            } else if (opcionPrincipal == 2) {

                ejecutarModuloCaja(controladorCaja, cajeroTurno, repoClientes);
            }
        }
        System.out.println("Sistema apagado correctamente.");
        scanner.close();
    }


    private static void ejecutarModuloCaja(ControladorCaja controladorCaja, Cajero cajeroTurno, RepositorioGenerico<Cliente> repoClientes) {
        Scanner scanner = new Scanner(System.in);
        MenuCajaView menu = new MenuCajaView();
        int opcion = 0;

        while (opcion != 4) {
            opcion = menu.mostrarMenuYCapturarOpcion();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- REGISTRO DE NUEVO COBRO ---");

                    System.out.print("Ingrese DNI del cliente a cobrar: ");
                    String dniBusqueda = scanner.nextLine();

                    Cliente clienteReal = repoClientes.buscar(c -> c.getDni().equals(dniBusqueda));

                    if (clienteReal == null) {
                        System.err.println("Error: Cliente no encontrado. Registre al cliente en el Módulo de Recepción primero.");
                        break;
                    }

                    System.out.print("Ingrese ID de la transacción (Ej: TX-001): ");
                    String idTx = scanner.nextLine();
                    System.out.print("Ingrese método de pago (EFECTIVO, TARJETA, YAPE, PLIN): ");
                    String tipoPago = scanner.nextLine();


                    model.caja.DetalleComprobante detalle = new model.caja.DetalleComprobante();
                    model.caja.ConceptoCobro concepto = new model.caja.ConceptoCobro("SRV-VET", "Servicio Veterinario", 150.0);
                    detalle.agregarItem(new model.caja.ItemVenta(concepto, 1));

                    model.caja.Comprobante boleta = new model.caja.Boleta("B001", "000001", detalle, model.caja.Moneda.PEN, clienteReal);

                    controladorCaja.registrarCobro(idTx, boleta, tipoPago);
                    break;

                case 2:
                    System.out.println("\n--- CERRANDO CAJA ---");
                    controladorCaja.getCajaActual().cerrarCaja();
                    AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja. Saldo: S/ " + controladorCaja.getCajaActual().getSaldoFinal());
                    System.out.println("Caja cerrada. Ya no se admiten cobros.");
                    break;

                case 3:
                    System.out.println("\n=== REPORTE DE CAJA DIARIA ===");
                    model.caja.CajaDiaria caja = controladorCaja.getCajaActual();
                    System.out.println("Cajero         : " + cajeroTurno.getNombre());
                    System.out.println("Estado de caja : " + caja.getEstado());
                    System.out.println("Saldo Inicial  : S/ " + caja.getSaldoInicial());
                    System.out.println("Saldo Final    : S/ " + caja.getSaldoFinal());
                    System.out.println("Ingresos netos : S/ " + (caja.getSaldoFinal() - caja.getSaldoInicial()));
                    break;

                case 4:
                    System.out.println("\nVolviendo al menú principal del sistema...");
                    break;
            }
        }
    }
}