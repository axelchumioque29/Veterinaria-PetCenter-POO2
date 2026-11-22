package model.farmacia;

import model.clientes.Mascota;
import java.time.LocalDateTime;
import java.util.Objects;

public class Internamiento {
    private final Mascota mascota;
    private final String motivo;
    private final String idCita;
    private final LocalDateTime fechaIngreso = LocalDateTime.now();
    private LocalDateTime fechaAlta;

    public Internamiento(Mascota mascota, String motivo) {
        this(mascota, motivo, null);
    }

    public Internamiento(Mascota mascota, String motivo, String idCita) {
        this.idCita = idCita == null ? null : ProductoFarmacia.validarTexto(idCita);
        this.mascota = Objects.requireNonNull(mascota, "Seleccione una mascota.");
        this.motivo = ProductoFarmacia.validarTexto(motivo);
    }

    public Mascota getMascota() { return mascota; }
    public String getMotivo() { return motivo; }
    public String getIdCita() { return idCita; }
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public synchronized LocalDateTime getFechaAlta() { return fechaAlta; }
    public synchronized boolean estaActivo() { return fechaAlta == null; }
    public synchronized void darAlta() {
        if (!estaActivo()) throw new IllegalArgumentException("El internamiento ya tiene alta.");
        fechaAlta = LocalDateTime.now();
    }
    @Override
    public synchronized String toString() {
        return fechaIngreso + " | " + motivo + " | " + (estaActivo() ? "Internado" : "Alta: " + fechaAlta)
                + (idCita == null ? "" : " | Cita: " + idCita);
    }
}

