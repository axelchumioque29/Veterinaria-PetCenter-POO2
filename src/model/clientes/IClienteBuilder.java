package model.clientes;

// Interfaz para el Patrón Builder
public interface IClienteBuilder {
    IClienteBuilder setDni(String dni);
    IClienteBuilder setNombre(String nombre);
    IClienteBuilder setTelefono(String telefono);
    IClienteBuilder setDireccion(String direccion);
    Cliente build();

    IClienteBuilder setApellido(String apellido);
}