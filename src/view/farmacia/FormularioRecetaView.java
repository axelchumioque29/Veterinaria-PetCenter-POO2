package view.farmacia;

import controller.farmacia.ControladorFarmacia;
import javax.swing.*;
import java.awt.*;

public class FormularioRecetaView extends JPanel {
    public FormularioRecetaView(ControladorFarmacia controlador) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField codigo = NotificacionFarmaciaView.agregarCampo(formulario, "Código único de receta");
        JTextField mascota = NotificacionFarmaciaView.agregarCampo(formulario, "ID de mascota (ver Atención)");
        JTextField medicamento = NotificacionFarmaciaView.agregarCampo(formulario, "Código del medicamento");
        JTextField cantidad = NotificacionFarmaciaView.agregarCampo(formulario, "Unidades a dispensar");
        JTextField indicaciones = NotificacionFarmaciaView.agregarCampo(formulario, "Indicaciones del veterinario");
        JTextField veterinario = NotificacionFarmaciaView.agregarCampo(formulario, "Veterinario");
        formulario.add(NotificacionFarmaciaView.crearBoton("Registrar y dispensar", () -> {
            controlador.registrarReceta(codigo.getText().trim(), mascota.getText().trim(), medicamento.getText().trim(),
                    cantidad.getText().trim(), indicaciones.getText(), veterinario.getText());
            NotificacionFarmaciaView.mostrarExito(this, "Receta registrada en la historia. Stock descontado.");
        }));
        add(formulario, BorderLayout.NORTH);
    }
}

