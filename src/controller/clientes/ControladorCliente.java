package controller.clientes;

import model.clientes.Cliente;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorCliente {
    private RepositorioGenerico<Cliente> repositorioClientes;

    public ControladorCliente(RepositorioGenerico<Cliente> repoClientes) {
        this.repositorioClientes = repoClientes;
    }

    public void registrarCliente(Cliente cliente) throws Exception {
        // Validar si ya existe un cliente con el mismo DNI
        if (buscarClientePorDni(cliente.getDni()) != null) {
            throw new Exception("Error: Ya existe un cliente registrado con el DNI " + cliente.getDni());
        }
        repositorioClientes.agregar(cliente);
    }

    public List<Cliente> listarClientes() {
        return repositorioClientes.listarTodos();
    }

    public Cliente buscarClientePorDni(String dni) {
        return BuscadorClienteController.buscarPorDni(repositorioClientes.listarTodos(), dni);
    }
}