package model.clientes;

public class Gato extends Mascota{
    private boolean esArisco;

    public Gato(String idMascota, String nombre, int edad, String raza, double peso, boolean esArisco) {
        super(idMascota, nombre, edad, raza, peso);
        this.esArisco = esArisco;
    }

    public boolean isEsArisco() {
        return esArisco;
    }

    public void setEsArisco(boolean esArisco) {
        this.esArisco = esArisco;
    }
}
