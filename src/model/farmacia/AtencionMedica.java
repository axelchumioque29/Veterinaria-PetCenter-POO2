package model.farmacia;

import model.clientes.Mascota;
import java.time.LocalDateTime;
import java.util.Objects;

public class AtencionMedica {
    private final String idCita;
    private final Mascota mascota;
    private final String motivo;
    private final String diagnostico;
    private final String tratamiento;
    private final String veterinario;
    private final LocalDateTime fecha = LocalDateTime.now();

    public AtencionMedica(String idCita, Mascota mascota, String motivo, String diagnostico,
                         String tratamiento, String veterinario) {
        this.idCita = ProductoFarmacia.validarTexto(idCita);
        this.mascota = Objects.requireNonNull(mascota, "Seleccione una mascota.");
        this.motivo = ProductoFarmacia.validarTexto(motivo);
        this.diagnostico = ProductoFarmacia.validarTexto(diagnostico);
        this.tratamiento = ProductoFarmacia.validarTexto(tratamiento);
        this.veterinario = ProductoFarmacia.validarTexto(veterinario);
    }

    public String getIdCita() { return idCita; }
    public Mascota getMascota() { return mascota; }
    public String getMotivo() { return motivo; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getVeterinario() { return veterinario; }
    public LocalDateTime getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Cita: " + idCita + " | " + fecha + " | Motivo: " + motivo
                + " | Diagnóstico: " + diagnostico + " | Tratamiento: " + tratamiento
                + " | Veterinario: " + veterinario;
    }
}
