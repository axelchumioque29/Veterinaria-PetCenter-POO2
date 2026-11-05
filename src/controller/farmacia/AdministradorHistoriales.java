package controller.farmacia;

import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;
import model.farmacia.*;
import java.util.ArrayList;

public class AdministradorHistoriales {

    private final RepositorioGenerico<HistoriaClinica> repositorio = new RepositorioGenerico<>();
    public synchronized HistoriaClinica obtener(Mascota mascota) {
        if (mascota == null) throw new IllegalArgumentException("Seleccione una mascota.");
        String id = ProductoFarmacia.validarTexto(mascota.getIdMascota());
        HistoriaClinica historia = repositorio.buscar(elemento -> elemento.getMascota().getIdMascota().equals(id));
        if (historia == null) {
            historia = new HistoriaClinica(mascota);
            repositorio.agregar(historia);
        }
        return historia;
    }

    public synchronized ArrayList<HistoriaClinica> listar() {
        return new ArrayList<>(repositorio.listarTodos());
    }
}

