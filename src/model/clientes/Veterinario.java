package model.clientes;

public class Veterinario extends Persona {
    private String codigoColegiatura;
    private String especialidad;
    private String turno;

    public Veterinario(String dni, String nombre, String telefono, String especialidad, String turno) {
        super(dni, nombre, telefono);
        this.codigoColegiatura = codigoColegiatura;
        this.especialidad = especialidad;
        this.turno = turno;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getCodigoColegiatura() {
        return codigoColegiatura;
    }

    public void setCodigoColegiatura(String codigoColegiatura) {
        this.codigoColegiatura = codigoColegiatura;
    }
}