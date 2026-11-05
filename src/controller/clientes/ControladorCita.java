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

    // Agendar una nueva cita recibiendo la estrategia de atención (Strategy)
    public void agendarCita(String idCita, Cliente cliente, Mascota mascota, LocalDateTime fechaHora, IEstrategiaAtencion estrategiaAtencion) throws CitaCruzadaException {
        // Pasamos la estrategia al constructor actualizado de Cita
        Cita nuevaCita = new Cita(idCita, cliente, mascota, fechaHora, estrategiaAtencion);

        // Validamos usando el validador de cruce de horarios
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