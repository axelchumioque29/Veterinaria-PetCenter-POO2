package model.clientes;

public abstract class Mascota {
    private String idMascota;
    private String nombre;
    private int edad;
    private String raza;

    public Mascota(String idMascota, String nombre, int edad, String raza) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public abstract String emitirSonido();

}
