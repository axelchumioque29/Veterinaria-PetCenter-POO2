package controller.clientes;

import model.clientes.Cliente;
import model.clientes.RepositorioGenerico;
import java.util.List;

public class ControladorCliente {
    private RepositorioGenerico<Cliente> repositorioClientes;

    public ControladorCliente() {
        this.repositorioClientes = new RepositorioGenerico<>();
    }

    // Registrar un nuevo cliente
    public void registrarCliente(String dni, String nombre, String telefono, String direccion) {
        Cliente nuevoCliente = new Cliente(dni, nombre, telefono, direccion);
        repositorioClientes.agregar(nuevoCliente);
    }

    // Buscar cliente por DNI usando Programación Funcional (Streams y Expresiones Lambda)
    public Cliente buscarClientePorDni(String dni) {
        return repositorioClientes.buscar(c -> c.getDni().equals(dni));
    }

    // Listar todos los clientes registrados
    public List<Cliente> obtenerTodosLosClientes() {
        return repositorioClientes.listarTodos();
    }
}