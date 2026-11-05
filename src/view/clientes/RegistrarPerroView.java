package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;
import model.clientes.MascotaBuilder;

public class RegistrarPerroView {
    private VeterinariaFacade facade;

    public RegistrarPerroView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() throws Exception {
        try {
            System.out.println("\n--- REGISTRAR PERRO ---");
            String codigo = ConsolaUtil.leerTexto("Ingrese Codigo de Mascota: ");
            String nombre = ConsolaUtil.leerTexto("Ingrese Nombre: ");
            int edad = ConsolaUtil.leerEntero("Ingrese Edad (anos): ");
            String raza = ConsolaUtil.leerTexto("Ingrese Raza: ");
            double peso = ConsolaUtil.leerDouble("Ingrese Peso (kg): ");
            String nivelEnergia = ConsolaUtil.leerTexto("Ingrese Nivel de Energia (Alto/Medio/Bajo): ");

            Mascota perro = new MascotaBuilder()
                    .setCodigo(codigo)
                    .setNombre(nombre)
                    .setEdad(edad)
                    .setRaza(raza)
                    .setPeso(peso)
                    .buildPerro(nivelEnergia);

            facade.registrarMascota(perro);
            System.out.println("¡Perro registrado exitosamente!");

        } catch (Exception e) {
            System.out.println("Error al registrar perro: " + e.getMessage());
        }
    }
}