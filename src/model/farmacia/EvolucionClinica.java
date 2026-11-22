package model.farmacia;

import java.time.LocalDateTime;

public class EvolucionClinica {
    private final LocalDateTime fecha = LocalDateTime.now();
    private final String observacion;
    private final String veterinario;
    private final String idCita;

    public EvolucionClinica(String observacion, String veterinario) {
        this(observacion, veterinario, null);
    }

    public EvolucionClinica(String observacion, String veterinario, String idCita) {
        this.idCita = idCita == null ? null : ProductoFarmacia.validarTexto(idCita);
        this.observacion = ProductoFarmacia.validarTexto(observacion);
        this.veterinario = ProductoFarmacia.validarTexto(veterinario);
    }

    public LocalDateTime getFecha() { return fecha; }
    public String getObservacion() { return observacion; }
    public String getVeterinario() { return veterinario; }
    public String getIdCita() { return idCita; }
    @Override
    public String toString() {
        return fecha + " | " + veterinario + ": " + observacion + (idCita == null ? "" : " | Cita: " + idCita);
    }
}

