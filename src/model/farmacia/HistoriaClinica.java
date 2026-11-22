package model.farmacia;

import model.clientes.Mascota;
import java.util.ArrayList;
import java.util.Objects;

public class HistoriaClinica {
    private final Mascota mascota;
    private final ArrayList<AtencionMedica> atenciones = new ArrayList<>();
    private final ArrayList<RecetaMedica> recetas = new ArrayList<>();
    private final ArrayList<EvolucionClinica> evoluciones = new ArrayList<>();
    private final ArrayList<Internamiento> internamientos = new ArrayList<>();

    public HistoriaClinica(Mascota mascota) {
        this.mascota = Objects.requireNonNull(mascota, "Seleccione una mascota.");
    }
    public Mascota getMascota() { return mascota; }
    public synchronized ArrayList<AtencionMedica> getAtenciones() { return new ArrayList<>(atenciones); }
    public synchronized void agregarAtencion(AtencionMedica atencion) {
        if (atencion == null || atencion.getMascota() != mascota) {
            throw new IllegalArgumentException("La atención pertenece a otra mascota.");
        }
        for (AtencionMedica existente : atenciones) {
            if (existente.getIdCita().equals(atencion.getIdCita())) {
                throw new IllegalArgumentException("La cita ya tiene una atención registrada.");
            }
        }
        atenciones.add(atencion);
    }
    // Compensa el registro si no se pudo actualizar el estado en el Módulo 1.
    public synchronized void retirarAtencion(AtencionMedica atencion) { atenciones.remove(atencion); }
    public synchronized ArrayList<RecetaMedica> getRecetas() { return new ArrayList<>(recetas); }
    public synchronized ArrayList<EvolucionClinica> getEvoluciones() { return new ArrayList<>(evoluciones); }
    public synchronized ArrayList<Internamiento> getInternamientos() { return new ArrayList<>(internamientos); }
    public synchronized void agregarReceta(RecetaMedica receta) {
        if (receta == null || receta.getMascota() != mascota) {
            throw new IllegalArgumentException("La receta pertenece a otra mascota.");
        }
        recetas.add(receta);
    }
    public synchronized void agregarEvolucion(EvolucionClinica evolucion) {
        evoluciones.add(Objects.requireNonNull(evolucion));
    }
    public synchronized void agregarInternamiento(Internamiento internamiento) {
        if (internamiento == null || internamiento.getMascota() != mascota) {
            throw new IllegalArgumentException("El internamiento pertenece a otra mascota.");
        }
        internamientos.add(internamiento);
    }
}

