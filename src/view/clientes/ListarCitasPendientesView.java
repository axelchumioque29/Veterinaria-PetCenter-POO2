package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;

public class ListarCitasPendientesView {
    private VeterinariaFacade facade;

    public ListarCitasPendientesView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\nCITAS PENDIENTES (Stream API)");
        var citas = facade.obtenerCitasPendientes();
        if (citas.isEmpty()) {
            System.out.println("No hay citas pendientes.");
        } else {
            for (Cita c : citas) {
                // Ejemplo de cómo debe imprimirse para que vuelva a mostrar la hora:
                System.out.println("ID: " + c.getIdCita()
                        + " | Cliente DNI: " + c.getCliente().getDni()
                        + " | Mascota: " + c.getMascota().getNombre()
                        + " | Fecha y Hora: " + c.getFechaHora()
                        + " | Prioridad: " + c.getPrioridad());
            }
        }
    }
}