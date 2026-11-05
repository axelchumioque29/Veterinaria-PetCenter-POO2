package controller.clientes;

import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorMascota {
    private RepositorioGenerico<Mascota> repositorioMascotas;

    public ControladorMascota(RepositorioGenerico<Mascota> repositorioMascotas) {
        this.repositorioMascotas = repositorioMascotas;
    }

    public void registrarMascota(Mascota mascota) {
        repositorioMascotas.agregar(mascota);
    }

    public List<Mascota> obtenerTodasLasMascotas() {
        return repositorioMascotas.listarTodos();
    }

    public Mascota buscarMascotaPorCodigo(String codigo) {
        return repositorioMascotas.listarTodos().stream()
                .filter(m -> m.getIdMascota().equals(codigo)) // <--- Cambiado a getIdMascota()
                .findFirst()
                .orElse(null);
    }
}