package view.farmacia;

import controller.farmacia.ClinicaFacade;
import model.farmacia.*;
import javax.swing.*;
import java.awt.*;

public class VistaHistoriaClinicaView extends JPanel {
    public VistaHistoriaClinicaView(ClinicaFacade clinica) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel();
        JTextField mascota = NotificacionFarmaciaView.agregarCampo(formulario, "ID de mascota");
        JTextArea detalle = new JTextArea();
        detalle.setEditable(false);
        formulario.add(NotificacionFarmaciaView.crearBoton("Consultar historia", () -> {
            HistoriaClinica historia = clinica.consultarHistoria(mascota.getText().trim());
            StringBuilder texto = new StringBuilder("Mascota: " + historia.getMascota().getNombre() + "\n\nRECETAS\n");
            for (RecetaMedica receta : historia.getRecetas()) texto.append(receta).append("\n");
            texto.append("\nEVOLUCIONES\n");
            for (EvolucionClinica evolucion : historia.getEvoluciones()) texto.append(evolucion).append("\n");
            texto.append("\nINTERNAMIENTOS\n");
            for (Internamiento internamiento : historia.getInternamientos()) texto.append(internamiento).append("\n");
            detalle.setText(texto.toString());
        }));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(detalle), BorderLayout.CENTER);
    }
}

