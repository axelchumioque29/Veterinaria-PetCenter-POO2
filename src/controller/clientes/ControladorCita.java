package controller.clientes;
import controller.clientes.FiltroEstadoCitaController;
import controller.clientes.ProcesadorCitaController;
import model.clientes.Cita;
import model.clientes.CitaCruzadaException;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorCita {
    private RepositorioGenerico<Cita> repositorioCitas;

    public ControladorCita(RepositorioGenerico<Cita> repoCitas) {
        this.repositorioCitas = repoCitas;
    }

    // Agendar usando el nuevo ProcesadorCitaController
    public void agendarCita(Cita nuevaCita) throws Exception {
        // Validar si ya existe una cita con el mismo ID
        boolean existeId = repositorioCitas.listarTodos().stream()
                .anyMatch(c -> c.getIdCita().equals(nuevaCita.getIdCita()));

        if (existeId) {
            throw new Exception("Error: Ya existe una cita registrada con el ID " + nuevaCita.getIdCita());
        }

        // Validar cruce de horario
        ProcesadorCitaController.validarYProcesar(repositorioCitas.listarTodos(), nuevaCita);
        repositorioCitas.agregar(nuevaCita);
    }

    // Filtrar citas pendientes usando el FiltroEstadoCitaController
    public List<Cita> obtenerCitasPendientes() {
        return FiltroEstadoCitaController.filtrarPendientes(repositorioCitas.listarTodos());
    }

    // Método para listar todas las citas
    public List<Cita> listarCitas() {
        return repositorioCitas.listarTodos();
    }
}