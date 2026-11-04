package controller.clientes;

import model.clientes.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorCita {
    private RepositorioGenerico<Cita> repositorioCitas;

    public ControladorCita() {
        this.repositorioCitas = new RepositorioGenerico<>();
    }

    // Agendar una nueva cita validando que no existan cruces de horario
    public void agendarCita(String idCita, Cliente cliente, Mascota mascota, LocalDateTime fechaHora, String motivo) throws CitaCruzadaException {
        Cita nuevaCita = new Cita(idCita, cliente, mascota, fechaHora, motivo);

        // Validamos usando la lista actual y la regla de negocio
        ValidadorCita.validarCruceHorario(repositorioCitas.listarTodos(), nuevaCita);

        // Si pasa la validación, se agrega al repositorio
        repositorioCitas.agregar(nuevaCita);
    }

    // Filtrar citas pendientes usando API Stream
    public List<Cita> obtenerCitasPendientes() {
        return repositorioCitas.listarTodos().stream()
                .filter(c -> c.getEstado() == EstadoCita.PENDIENTE)
                .collect(Collectors.toList());
    }

    // Listar todas las citas
    public List<Cita> obtenerTodasLasCitas() {
        return repositorioCitas.listarTodos();
    }
}