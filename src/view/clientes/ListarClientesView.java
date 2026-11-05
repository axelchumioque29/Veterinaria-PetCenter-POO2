package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cliente;

public class ListarClientesView {
    private VeterinariaFacade facade;

    public ListarClientesView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        var clientes = facade.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                // Eliminamos c.getApellido() para evitar el error de compilación
                System.out.println("DNI: " + c.getDni() + " | Nombre: " + c.getNombre() + " | Teléfono: " + c.getTelefono());
            }
        }
    }
}