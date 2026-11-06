package controller.clientes;

import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorMascota {
    private RepositorioGenerico<Mascota> repositorioMascotas;

    public ControladorMascota(RepositorioGenerico<Mascota> repoMascotas) {
        this.repositorioMascotas = repoMascotas;
    }

    public void registrarMascota(Mascota mascota) throws Exception {
        // Validar si ya existe una mascota con el mismo código
        if (buscarMascotaPorCodigo(mascota.getCodigo()) != null) {
            throw new Exception("Error: Ya existe una mascota registrada con el código " + mascota.getCodigo());
        }
        repositorioMascotas.agregar(mascota);
    }

    public List<Mascota> listarMascotas() {
        return repositorioMascotas.listarTodos();
    }

    // Delegando la búsqueda a la micro-clase BuscadorMascotaController
    public Mascota buscarMascotaPorCodigo(String codigo) {
        return BuscadorMascotaController.buscarPorCodigo(repositorioMascotas.listarTodos(), codigo);
    }
}