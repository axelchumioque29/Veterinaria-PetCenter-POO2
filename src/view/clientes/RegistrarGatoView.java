package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;
import model.clientes.MascotaBuilder;

public class RegistrarGatoView {
    private VeterinariaFacade facade;

    public RegistrarGatoView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() throws Exception {
        try {
            System.out.println("\n--- REGISTRAR GATO ---");
            String codigo = ConsolaUtil.leerTexto("Ingrese Codigo de Mascota: ");
            String nombre = ConsolaUtil.leerTexto("Ingrese Nombre: ");
            int edad = ConsolaUtil.leerEntero("Ingrese Edad (anos): ");
            String raza = ConsolaUtil.leerTexto("Ingrese Raza: ");
            double peso = ConsolaUtil.leerDouble("Ingrese Peso (kg): ");
            boolean esArisco = ConsolaUtil.leerBooleano("¿Es arisco? (true/false): ");

            Mascota gato = new MascotaBuilder()
                    .setCodigo(codigo)
                    .setNombre(nombre)
                    .setEdad(edad)
                    .setRaza(raza)
                    .setPeso(peso)
                    .buildGato(esArisco);

            facade.registrarMascota(gato);
            System.out.println("¡Gato registrado exitosamente!");

        } catch (Exception e) {
            System.out.println("Error al registrar gato: " + e.getMessage());
        }
    }
}