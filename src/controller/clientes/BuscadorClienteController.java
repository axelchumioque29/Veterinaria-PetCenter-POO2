package controller.clientes;

import model.clientes.Cliente;
import java.util.List;

public class BuscadorClienteController {

    public static Cliente buscarPorDni(List<Cliente> clientes, String dni) {
        return clientes.stream()
                .filter(c -> c.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }
}