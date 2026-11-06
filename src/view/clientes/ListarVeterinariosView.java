package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Veterinario;
import java.util.Arrays;
import java.util.List;

public class ListarVeterinariosView {
    private VeterinariaFacade facade;

    public ListarVeterinariosView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\n--- STAFF MEDICO VETERINARIO ---");

        // Creamos una lista fija de ejemplo para el staff
        List<Veterinario> veterinarios = Arrays.asList(
                new Veterinario("Dr. Carlos Ruiz", "45123456", "987654321", "Cirugía General", "Mañana"),
                new Veterinario("Dra. Ana Torres", "46234567", "912345678", "Medicina Felina",  "Tarde"),
                new Veterinario("Dr. Jorge Mendoza", "47345678", "998877665", "Emergencias / Exóticos",  "Noche")
        );

        for (Veterinario v : veterinarios) {
            System.out.println("Dr(a).: " + v.getNombre()
                    + " | Especialidad: " + v.getEspecialidad()
                    + " | Turno: " + v.getTurno()
                    + " | Tel: " + v.getTelefono());
        }
    }
}