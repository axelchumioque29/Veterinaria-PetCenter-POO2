package view.clientes;

import controller.clientes.VeterinariaFacade;

public class MenuPrincipalView {
    private VeterinariaFacade facade;

    public MenuPrincipalView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=VETERINARIA PETCENTER ");
            System.out.println("1. Modulo de Clientes");
            System.out.println("2. Modulo de Mascotas");
            System.out.println("3. Modulo de Citas Medicas");
            System.out.println("4. Salir");
            opcion = ConsolaUtil.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    new MenuClientesSubView(facade).mostrar();
                    break;
                case 2:
                    new MenuMascotasSubView(facade).mostrar();
                    break;
                case 3:
                    new MenuCitasSubView(facade).mostrar();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 4);
    }
}