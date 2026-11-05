package controller.clientes;

import model.clientes.Mascota;
import java.util.List;

public class BuscadorMascotaController {

    public static Mascota buscarPorCodigo(List<Mascota> mascotas, String codigo) {
        return mascotas.stream()
                .filter(m -> m.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
}