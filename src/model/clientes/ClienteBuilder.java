package model.clientes;

// Clase concreta que implementa el Builder para la entidad Cliente
public class ClienteBuilder implements IClienteBuilder {
    private String dni;
    private String nombre;
    private String telefono;
    private String direccion;

    @Override
    public IClienteBuilder setDni(String dni) {
        this.dni = dni;
        return this;
    }

    @Override
    public IClienteBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public IClienteBuilder setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    @Override
    public IClienteBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    @Override
    public Cliente build() {
        // Aquí podríamos validar reglas antes de retornar el objeto
        return new Cliente(dni, nombre, telefono, direccion);
    }

    @Override
    public IClienteBuilder setApellido(String apellido) {
        return null;
    }
}