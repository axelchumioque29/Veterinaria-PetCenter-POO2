package controller.farmacia;

import model.farmacia.*;

public class SincronizadorClinico {
    private final AdministradorHistoriales historiales;
    public SincronizadorClinico(AdministradorHistoriales historiales) { this.historiales = historiales; }
    public synchronized void registrarReceta(RecetaMedica receta) {
        historiales.obtener(receta.getMascota()).agregarReceta(receta);
    }
}

