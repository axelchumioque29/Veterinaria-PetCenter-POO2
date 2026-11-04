package controller.farmacia;

import model.clientes.Mascota;
import model.farmacia.*;
import java.util.ArrayList;

public class PlanificadorInternamiento {
    private final AdministradorHistoriales historiales;
    public PlanificadorInternamiento(AdministradorHistoriales historiales) { this.historiales = historiales; }

    public synchronized Internamiento registrar(Mascota mascota, String motivo) {
        HistoriaClinica historia = historiales.obtener(mascota);
        for (Internamiento existente : historia.getInternamientos()) {
            if (existente.estaActivo()) throw new IllegalArgumentException("La mascota ya está internada.");
        }
        Internamiento internamiento = new Internamiento(historia.getMascota(), motivo);
        historia.agregarInternamiento(internamiento);
        return internamiento;
    }

    public synchronized void darAlta(Mascota mascota) {
        for (Internamiento internamiento : historiales.obtener(mascota).getInternamientos()) {
            if (internamiento.estaActivo()) {
                internamiento.darAlta();
                return;
            }
        }
        throw new IllegalArgumentException("La mascota no tiene internamiento activo.");
    }

    public synchronized int contarActivos() {
        int total = 0;
        for (HistoriaClinica historia : historiales.listar()) {
            for (Internamiento internamiento : historia.getInternamientos()) {
                if (internamiento.estaActivo()) total++;
            }
        }
        return total;
    }
}

