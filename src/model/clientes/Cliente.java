package model.clientes;

public class Cliente extends Persona {
    private String direccion;
    private List<Mascota> mascotas;

    public Cliente(String dni, String nombre, String telefono, String direccion, List<Mascota> mascotas) {
        super(dni, nombre, telefono);
        this.direccion = direccion;
        this.mascotas = mascotas;
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
