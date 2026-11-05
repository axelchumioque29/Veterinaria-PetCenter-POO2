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
    public void agendarCita(Cita nuevaCita) throws CitaCruzadaException {
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