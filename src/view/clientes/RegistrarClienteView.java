package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cliente;
import model.clientes.ClienteBuilder;

public class RegistrarClienteView {
    private VeterinariaFacade facade;

    public RegistrarClienteView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        String dni = ConsolaUtil.leerTexto("Ingrese DNI: ");
        String nombre = ConsolaUtil.leerTexto("Ingrese Nombres: ");
        String apellido = ConsolaUtil.leerTexto("Ingrese Apellidos: ");
        String telefono = ConsolaUtil.leerTexto("Ingrese Telefono: ");
        String direccion = ConsolaUtil.leerTexto("Ingrese Direccion: ");

        Cliente cliente = new ClienteBuilder()
                .setDni(dni)
                .setNombre(nombre)
                .setApellido(apellido)
                .setTelefono(telefono)
                .setDireccion(direccion)
                .build();

        facade.registrarCliente(cliente);
        System.out.println("¡Cliente registrado exitosamente!");
    }
}