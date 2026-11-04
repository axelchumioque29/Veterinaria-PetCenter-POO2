package model.clientes;

public class Veterinario extends Persona {
    private String especialidad;
    private String turno;

    public Veterinario(String dni, String nombre, String telefono, String especialidad, String turno) {
        super(dni, nombre, telefono);
        this.especialidad = especialidad;
        this.turno = turno;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}