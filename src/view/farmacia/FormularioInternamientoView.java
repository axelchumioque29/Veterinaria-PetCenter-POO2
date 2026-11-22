package view.farmacia;

import controller.farmacia.ClinicaFacade;
import javax.swing.*;
import java.awt.*;

public class FormularioInternamientoView extends JPanel {
    public FormularioInternamientoView(ClinicaFacade clinica) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField mascota = NotificacionFarmaciaView.agregarCampo(formulario, "ID de mascota");
        JTextField motivo = NotificacionFarmaciaView.agregarCampo(formulario, "Motivo de internamiento");
        JTextField cita = NotificacionFarmaciaView.agregarCampo(formulario, "ID de cita (opcional)");
        formulario.add(NotificacionFarmaciaView.crearBoton("Registrar internamiento", () -> {
            clinica.registrarInternamiento(mascota.getText().trim(), motivo.getText(), cita.getText().trim());
            NotificacionFarmaciaView.mostrarExito(this, "Internamiento registrado.");
        }));
        formulario.add(NotificacionFarmaciaView.crearBoton("Dar de alta", () -> {
            clinica.getAtencion().darAlta(mascota.getText().trim());
            NotificacionFarmaciaView.mostrarExito(this, "Alta registrada.");
        }));
        add(formulario, BorderLayout.NORTH);
        add(NotificacionFarmaciaView.crearSelectorMascota(clinica, mascota), BorderLayout.SOUTH);
    }
}

