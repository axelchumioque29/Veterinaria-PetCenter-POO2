package model.clientes;

// Clase concreta que implementa el Builder para la entidad Cliente
public class ClienteBuilder implements IClienteBuilder {
    private String dni;
    private String nombre;
    private String apellido; // Añadido para capturar el apellido
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
    public IClienteBuilder setApellido(String apellido) {
        this.apellido = apellido;
        return this; // ¡Ahora retorna 'this' para no romper la cadena!
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
        // Unimos el nombre y el apellido para enviárselo al constructor de Cliente
        String nombreCompleto = (apellido != null && !apellido.isEmpty()) ? (nombre + " " + apellido) : nombre;
        return new Cliente(dni, nombreCompleto, telefono, direccion);
    }
}