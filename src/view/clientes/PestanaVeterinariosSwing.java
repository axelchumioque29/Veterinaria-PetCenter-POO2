package view.clientes;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class PestanaVeterinariosSwing extends JPanel {
    private final ValidadorEntradaSwing validador;
    private final DialogoMensajeSwing dialogo;

    private final DefaultTableModel modeloTabla;
    private final JTable tablaVeterinarios;

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JComboBox<String> cmbEspecialidad = new JComboBox<>(new String[]{
            "Medicina General",
            "Cirugía",
            "Emergencias",
            "Dermatología"
    });
    private final JComboBox<String> cmbTurno = new JComboBox<>(new String[]{
            "Mañana (08:00 - 14:00)",
            "Tarde (14:00 - 20:00)",
            "Guardia / Emergencia"
    });

    public PestanaVeterinariosSwing() {
        this.validador = new ValidadorEntradaSwing();
        this.dialogo = new DialogoMensajeSwing();
        this.modeloTabla = new DefaultTableModel(
                new Object[]{"Código", "Veterinario", "Especialidad", "Turno"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablaVeterinarios = new JTable(modeloTabla);

        inicializarComponentes();
        cargarVeterinariosIniciales();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Veterinario / Staff"));

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especialidad:"));
        panelFormulario.add(cmbEspecialidad);

        panelFormulario.add(new JLabel("Turno:"));
        panelFormulario.add(cmbTurno);

        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnAgregar = new JButton("Agregar a la Lista");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnAgregar.addActionListener(e -> agregarVeterinario());

        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnAgregar);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelFormulario, BorderLayout.NORTH);

        JPanel panelDerecho = new JPanel(new BorderLayout(8, 8));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Staff Clínico Disponible"));
        panelDerecho.add(new JScrollPane(tablaVeterinarios), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        JButton btnQuitar = new JButton("Quitar Seleccionado");
        btnQuitar.addActionListener(e -> quitarVeterinarioSeleccionado());
        panelAcciones.add(btnQuitar);
        panelDerecho.add(panelAcciones, BorderLayout.SOUTH);

        JPanel contenido = new JPanel(new GridLayout(1, 2, 12, 12));
        contenido.add(panelIzquierdo);
        contenido.add(panelDerecho);

        add(contenido, BorderLayout.CENTER);
    }

    private void cargarVeterinariosIniciales() {
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{"VET-01", "Dra. Carla Mendoza", "Medicina General", "Mañana (08:00 - 14:00)"});
        modeloTabla.addRow(new Object[]{"VET-02", "Dr. Luis Paredes", "Emergencias", "Guardia / Emergencia"});
        modeloTabla.addRow(new Object[]{"VET-03", "Dra. Sofía Quispe", "Cirugía", "Tarde (14:00 - 20:00)"});
    }

    private void agregarVeterinario() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (validador.estaVacio(codigo) || validador.estaVacio(nombre)) {
            dialogo.mostrarAdvertencia(this, "Validación", "El código y el nombre del veterinario son obligatorios.");
            return;
        }

        String especialidad = (String) cmbEspecialidad.getSelectedItem();
        String turno = (String) cmbTurno.getSelectedItem();

        modeloTabla.addRow(new Object[]{codigo, nombre, especialidad, turno});
        dialogo.mostrarInformacion(this, "Registro", "Veterinario agregado a la lista de recepción.");
        limpiarCampos();
    }

    private void quitarVeterinarioSeleccionado() {
        int fila = tablaVeterinarios.getSelectedRow();
        if (fila == -1) {
            dialogo.mostrarAdvertencia(this, "Selección requerida", "Selecciona un veterinario de la tabla.");
            return;
        }
        modeloTabla.removeRow(fila);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        cmbEspecialidad.setSelectedIndex(0);
        cmbTurno.setSelectedIndex(0);
    }
}