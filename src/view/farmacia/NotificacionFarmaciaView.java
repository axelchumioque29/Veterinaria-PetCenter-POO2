package view.farmacia;

import controller.farmacia.ClinicaFacade;
import model.clientes.Mascota;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeParseException;

public class NotificacionFarmaciaView {
    public static JPanel crearSelectorMascota(ClinicaFacade clinica, JTextField destino) {
        JPanel panel = new JPanel();
        JComboBox<Mascota> mascotas = new JComboBox<>();
        mascotas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                                                          boolean seleccionada, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionada, foco);
                if (valor instanceof Mascota) {
                    Mascota mascota = (Mascota) valor;
                    setText(mascota.getIdMascota() + " | " + mascota.getNombre());
                }
                return this;
            }
        });
        Runnable actualizar = () -> {
            mascotas.removeAllItems();
            for (Mascota mascota : clinica.getAtencion().listarMascotas()) mascotas.addItem(mascota);
            mascotas.setSelectedIndex(-1);
        };
        actualizar.run();
        mascotas.addActionListener(evento -> {
            Mascota mascota = (Mascota) mascotas.getSelectedItem();
            if (mascota != null) destino.setText(mascota.getIdMascota());
        });
        panel.add(new JLabel("Mascotas registradas:"));
        panel.add(mascotas);
        panel.add(crearBoton("Actualizar mascotas", actualizar));
        return panel;
    }

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

