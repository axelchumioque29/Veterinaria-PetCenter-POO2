package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import java.time.format.DateTimeFormatter;

public class ListarCitasPendientesView {
    private VeterinariaFacade facade;

    public ListarCitasPendientesView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\nCITAS PENDIENTES (Stream API)");
        var citas = facade.obtenerCitasPendientes();
        DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (citas.isEmpty()) {
            System.out.println("No hay citas pendientes.");
        } else {
            for (Cita c : citas) {
                System.out.println("ID: " + c.getIdCita()
                        + " | Cliente DNI: " + c.getCliente().getDni()
                        + " | Mascota: " + c.getMascota().getNombre()
                        + " | Fecha y Hora: " + c.getFechaHora().format(formatoSalida)
                        + " | Prioridad: " + c.getPrioridad()
                        + " | Estado: " + c.getEstado());
            }
        }
    }
}