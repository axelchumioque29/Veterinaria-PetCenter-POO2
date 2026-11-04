package model.farmacia;

import java.time.LocalDateTime;

public class EvolucionClinica {
    private final LocalDateTime fecha = LocalDateTime.now();
    private final String observacion;
    private final String veterinario;

    public EvolucionClinica(String observacion, String veterinario) {
        this.observacion = ProductoFarmacia.validarTexto(observacion);
        this.veterinario = ProductoFarmacia.validarTexto(veterinario);
    }

    public LocalDateTime getFecha() { return fecha; }
    public String getObservacion() { return observacion; }
    public String getVeterinario() { return veterinario; }
    @Override
    public String toString() { return fecha + " | " + veterinario + ": " + observacion; }
}

