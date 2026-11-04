package view.farmacia;

import controller.farmacia.ClinicaFacade;
import javax.swing.*;
import java.awt.*;

public class AlertaVencimientoView extends JPanel {
    private final JTextArea mensajes = new JTextArea();
    private final ClinicaFacade clinica;

    public AlertaVencimientoView(ClinicaFacade clinica) {
        this.clinica = clinica;
        setLayout(new BorderLayout());
        mensajes.setEditable(false);
        add(new JLabel("Vencimientos y stock bajo: revisión en segundo plano cada segundo."), BorderLayout.NORTH);
        add(new JScrollPane(mensajes), BorderLayout.CENTER);
    }
    public void actualizar() {
        mensajes.setText("");
        for (String mensaje : clinica.consultarAlertas()) mensajes.append(mensaje + "\n");
        if (mensajes.getText().isEmpty()) mensajes.setText("Sin alertas detectadas.");
    }
}

