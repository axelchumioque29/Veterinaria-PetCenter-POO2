package controller.clientes;

import model.clientes.Cliente;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorCliente {
    private RepositorioGenerico<Cliente> repositorioClientes;

    public ControladorCliente(RepositorioGenerico<Cliente> repoClientes) {
        this.repositorioClientes = repoClientes;
    }

    // Registrar recibiendo parámetros separados (el que ya tenías)
    public void registrarCliente(String dni, String nombre, String telefono, String direccion) {
        Cliente nuevoCliente = new Cliente(dni, nombre, telefono, direccion);
        repositorioClientes.agregar(nuevoCliente);
    }

    // NUEVO: Registrar recibiendo directamente el objeto Cliente (para la Fachada)
    public void registrarCliente(Cliente cliente) {
        repositorioClientes.agregar(cliente);
    }

    // Buscar cliente por DNI
    public Cliente buscarClientePorDni(String dni) {
        return repositorioClientes.buscar(c -> c.getDni().equals(dni));
    }

    // Listar todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return repositorioClientes.listarTodos();
    }

    // Corregido para que devuelva la lista y la Fachada no falle
    public List<Cliente> listarClientes() {
        return repositorioClientes.listarTodos();
    }
}