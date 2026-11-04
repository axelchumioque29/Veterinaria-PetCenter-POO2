package model.clientes;

import java.time.LocalDate;

public class HistorialMedico {
    private String idHistorial;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fechaRegistro;

    public HistorialMedico(String idHistorial, String diagnostico, String tratamiento, LocalDate fechaRegistro) {
        this.idHistorial = idHistorial;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdHistorial() { return idHistorial; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
}