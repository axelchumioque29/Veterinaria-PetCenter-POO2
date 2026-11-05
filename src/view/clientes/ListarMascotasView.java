package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;

public class ListarMascotasView {
    private VeterinariaFacade facade;

    public ListarMascotasView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\n--- LISTA DE MASCOTAS ---");
        var mascotas = facade.listarMascotas();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            for (Mascota m : mascotas) {
                System.out.println("Codigo: " + m.getCodigo() + " | Nombre: " + m.getNombre() + " | Raza: " + m.getRaza());
            }
        }
    }
}