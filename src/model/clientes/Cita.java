package model.clientes;

import java.time.LocalDateTime;

public class Cita {
    private String idCita;
    private Cliente cliente;
    private Mascota mascota;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private String motivo;

    public Cita(String idCita, Cliente cliente, Mascota mascota, LocalDateTime fechaHora, String motivo) {
        this.idCita = idCita;
        this.cliente = cliente;
        this.mascota = mascota;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = EstadoCita.PENDIENTE; //Por defecto debe nacer en estado pendiente
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
