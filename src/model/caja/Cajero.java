package model.caja;

public class Cajero {
    private String id;
    private String nombre;

    public Cajero(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}