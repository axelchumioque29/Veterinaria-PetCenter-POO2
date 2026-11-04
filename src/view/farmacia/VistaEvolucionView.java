package view.farmacia;

import controller.farmacia.ClinicaFacade;
import model.farmacia.EvolucionClinica;
import javax.swing.*;
import java.awt.*;

public class VistaEvolucionView extends JPanel {
    public VistaEvolucionView(ClinicaFacade clinica) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField mascota = NotificacionFarmaciaView.agregarCampo(formulario, "ID de mascota");
        JTextField observacion = NotificacionFarmaciaView.agregarCampo(formulario, "Observación");
        JTextField veterinario = NotificacionFarmaciaView.agregarCampo(formulario, "Veterinario");
        JTextArea listado = new JTextArea();
        listado.setEditable(false);
        formulario.add(NotificacionFarmaciaView.crearBoton("Registrar evolución", () -> {
            clinica.getAtencion().registrarEvolucion(mascota.getText().trim(), observacion.getText(), veterinario.getText());
            NotificacionFarmaciaView.mostrarExito(this, "Evolución registrada.");
        }));
        formulario.add(NotificacionFarmaciaView.crearBoton("Consultar evoluciones", () -> {
            listado.setText("");
            for (EvolucionClinica evolucion : clinica.consultarHistoria(mascota.getText().trim()).getEvoluciones()) {
                listado.append(evolucion + "\n");
            }
        }));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(listado), BorderLayout.CENTER);
    }
}

