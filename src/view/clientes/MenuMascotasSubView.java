package view.clientes;

import controller.clientes.VeterinariaFacade;

public class MenuMascotasSubView {
    private VeterinariaFacade facade;

    public MenuMascotasSubView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE MASCOTAS ---");
            System.out.println("1. Registrar Perro");
            System.out.println("2. Registrar Gato");
            System.out.println("3. Listar Mascotas");
            System.out.println("4. Volver al Menu Principal");
            opcion = ConsolaUtil.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    new RegistrarPerroView(facade).ejecutar();
                    break;
                case 2:
                    new RegistrarGatoView(facade).ejecutar();
                    break;
                case 3:
                    new ListarMascotasView(facade).ejecutar();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 4);
    }
}