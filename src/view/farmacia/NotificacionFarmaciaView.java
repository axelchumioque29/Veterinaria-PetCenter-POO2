package view.farmacia;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeParseException;

public class NotificacionFarmaciaView {
    public static JTextField agregarCampo(JPanel panel, String etiqueta) {
        panel.add(new JLabel(etiqueta));
        JTextField campo = new JTextField(16);
        panel.add(campo);
        return campo;
    }
    public static JButton crearBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(evento -> {
            try {
                accion.run();
            } catch (NumberFormatException excepcion) {
                mostrarError(boton, "Ingrese números válidos. Use punto para los decimales.");
            } catch (DateTimeParseException excepcion) {
                mostrarError(boton, "Ingrese una fecha válida con formato AAAA-MM-DD.");
            } catch (IllegalArgumentException excepcion) {
                mostrarError(boton, excepcion.getMessage());
            }
        });
        return boton;
    }
    public static void mostrarError(Component origen, String mensaje) {
        JOptionPane.showMessageDialog(origen, mensaje, "Farmacia", JOptionPane.ERROR_MESSAGE);
    }
    public static void mostrarExito(Component origen, String mensaje) {
        JOptionPane.showMessageDialog(origen, mensaje, "Farmacia", JOptionPane.INFORMATION_MESSAGE);
    }
}

