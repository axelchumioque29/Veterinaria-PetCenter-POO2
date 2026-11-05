package model.clientes;

import java.util.ArrayList; // <--- Importante
import java.util.List;

public class Cliente extends Persona {
    private String direccion;
    private List<Mascota> mascotas;

    public Cliente(String dni, String nombre, String telefono, String direccion) {
        super(dni, nombre, telefono);
        this.direccion = direccion;
        this.mascotas = new ArrayList<>(); // <--- Inicializamos la lista aquí correctamente
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void agregarMascota (Mascota mascota){
        this.mascotas.add(mascota);
    }
}