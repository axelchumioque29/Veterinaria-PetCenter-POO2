package view.farmacia;

import controller.farmacia.ControladorFarmacia;
import model.farmacia.Medicamento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelInventarioView extends JPanel {
    private final ControladorFarmacia controlador;
    private final DefaultTableModel tabla = new DefaultTableModel(
            new String[]{"Código", "Nombre", "Tipo", "Precio", "Stock vigente", "Mínimo"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) { return false; }
    };

    public PanelInventarioView(ControladorFarmacia controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(8, 8));
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField codigo = NotificacionFarmaciaView.agregarCampo(formulario, "Código");
        JTextField nombre = NotificacionFarmaciaView.agregarCampo(formulario, "Nombre");
        JTextField precio = NotificacionFarmaciaView.agregarCampo(formulario, "Precio");
        JTextField principio = NotificacionFarmaciaView.agregarCampo(formulario, "Principio activo");
        JTextField minimo = NotificacionFarmaciaView.agregarCampo(formulario, "Stock mínimo");
        formulario.add(new JLabel("Tipo"));
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Medicamento", "Vacuna", "Antibiotico", "Anestesico"});
        formulario.add(tipo);
        formulario.add(NotificacionFarmaciaView.crearBoton("Registrar medicamento", () -> {
            controlador.registrar((String) tipo.getSelectedItem(), codigo.getText().trim(), nombre.getText(),
                    precio.getText().trim(), principio.getText(), minimo.getText().trim());
            actualizar();
            NotificacionFarmaciaView.mostrarExito(this, "Medicamento registrado. Agregue un lote en Stock y lotes.");
        }));
        formulario.add(NotificacionFarmaciaView.crearBoton("Actualizar lista", this::actualizar));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tabla)), BorderLayout.CENTER);
        actualizar();
    }

    public void actualizar() {
        tabla.setRowCount(0);
        for (Medicamento medicamento : controlador.getClinica().listarMedicamentos()) {
            tabla.addRow(new Object[]{medicamento.getCodigo(), medicamento.getNombre(), medicamento.getTipo(),
                    medicamento.getPrecio(), medicamento.getStockDisponible(), medicamento.getStockMinimo()});
        }
    }
}

