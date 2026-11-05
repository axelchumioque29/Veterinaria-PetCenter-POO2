package view.clientes;

import controller.clientes.VeterinariaFacade;

public class MenuCitasSubView {
    private VeterinariaFacade facade;
    private ListarVeterinariosView listarVeterinariosView;
    public MenuCitasSubView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE CITAS MEDICAS ---");
            System.out.println("1. Agendar Nueva Cita");
            System.out.println("2. Ver Citas Pendientes ");
            System.out.println("3. Ver Personal Veterinario");
            System.out.println("4. Volver al Menu Principal");
            opcion = ConsolaUtil.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    new AgendarCitaView(facade).ejecutar();
                    break;
                case 2:
                    new ListarCitasPendientesView(facade).ejecutar();
                    break;
                case 3:
                    new ListarVeterinariosView(facade).ejecutar();
                    break;
                case 4:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 4);
    }
}