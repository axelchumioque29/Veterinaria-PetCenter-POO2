package model.clientes;

public class Perro extends Mascota{
    private String nivelEnergia;

    public Perro(String idMascota, String nombre, int edad, String raza, double peso, String nivelEnergia) {
        super(idMascota, nombre, edad, raza, peso);
        this.nivelEnergia = nivelEnergia;
    }

    public String getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(String nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }
}

