package view.clientes;

import controller.clientes.VeterinariaFacade;

public class MenuClientesSubView {
    private VeterinariaFacade facade;

    public MenuClientesSubView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar Nuevo Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Volver al Menu Principal");
            opcion = ConsolaUtil.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    new RegistrarClienteView(facade).ejecutar();
                    break;
                case 2:
                    new ListarClientesView(facade).ejecutar();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 3);
    }
}