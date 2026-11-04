package view.farmacia;

import controller.farmacia.ClinicaFacade;
import model.clientes.Mascota;
import javax.swing.*;
import java.awt.*;

public class PanelMedicoView extends JPanel {
    public PanelMedicoView(ClinicaFacade clinica) {
        setLayout(new BorderLayout());
        JTextArea listado = new JTextArea();
        listado.setEditable(false);
        Runnable actualizar = () -> {
            listado.setText("MASCOTAS REGISTRADAS\nUse el ID en recetas, historias, evoluciones e internamientos.\n\n");
            for (Mascota mascota : clinica.getAtencion().listarMascotas()) {
                listado.append(mascota.getIdMascota() + " | " + mascota.getNombre()
                        + " | edad: " + mascota.getEdad() + " | raza: " + mascota.getRaza() + "\n");
            }
        };
        add(NotificacionFarmaciaView.crearBoton("Actualizar mascotas", actualizar), BorderLayout.NORTH);
        add(new JScrollPane(listado), BorderLayout.CENTER);
        actualizar.run();
    }
}

