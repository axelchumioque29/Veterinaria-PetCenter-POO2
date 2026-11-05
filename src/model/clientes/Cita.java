package model.clientes;

import java.time.LocalDateTime;

public class Cita {
    private String idCita;
    private Cliente cliente;
    private Mascota mascota;
    private LocalDateTime fechaHora;
    private IEstrategiaAtencion estrategiaAtencion; // Patrón Strategy integrado
    private EstadoCita estado;

    public Cita(String idCita, Cliente cliente, Mascota mascota, LocalDateTime fechaHora, IEstrategiaAtencion estrategiaAtencion) {
        this.idCita = idCita;
        this.cliente = cliente;
        this.mascota = mascota;
        this.fechaHora = fechaHora;
        this.estrategiaAtencion = estrategiaAtencion;
        this.estado = EstadoCita.PENDIENTE;
    }

    public LocalDateTime getFechaHoraFin() {
        int minutos = estrategiaAtencion.calcularMinutosEstimados();
        return fechaHora.plusMinutes(minutos);
    }

    public String getPrioridad() {
        return estrategiaAtencion.obtenerPrioridad();
    }

    // Getters y Setters tradicionales
    public String getIdCita() { return idCita; }
    public Cliente getCliente() { return cliente; }
    public Mascota getMascota() { return mascota; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
}