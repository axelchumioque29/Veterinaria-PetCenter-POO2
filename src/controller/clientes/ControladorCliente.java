package controller.clientes;

import model.clientes.Cliente;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorCliente {
    private RepositorioGenerico<Cliente> repositorioClientes;

    public ControladorCliente(RepositorioGenerico<Cliente> repoClientes) {
        this.repositorioClientes = repoClientes;
    }

    public void registrarCliente(Cliente cliente) {
        repositorioClientes.agregar(cliente);
    }

    public List<Cliente> listarClientes() {
        return repositorioClientes.listarTodos();
    }

    // Delegando la búsqueda a la micro-clase BuscadorClienteController
    public Cliente buscarClientePorDni(String dni) {
        return BuscadorClienteController.buscarPorDni(repositorioClientes.listarTodos(), dni);
    }
}