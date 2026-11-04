package model.clientes;

public class Recepcionista extends Persona {
    private String codigoEmpleado;

    public Recepcionista(String dni, String nombre, String telefono, String codigoEmpleado) {
        super(dni, nombre, telefono);
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }
}