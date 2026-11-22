package view.farmacia;

import controller.farmacia.ClinicaFacade;
import controller.farmacia.ControladorFarmacia;
import model.clientes.Cita;
import model.clientes.Mascota;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelMedicoView extends JPanel {
    private final ClinicaFacade clinica;
    private final DefaultTableModel datosCitas = new DefaultTableModel(
            new String[]{"ID cita", "ID mascota", "Mascota", "Cliente", "Fecha", "Prioridad", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) { return false; }
    };
    private final JTable citas = new JTable(datosCitas);
    private final JCheckBox soloPendientes = new JCheckBox("Solo pendientes", true);
    private final JTextArea listado = new JTextArea(4, 40);
    private final JTextField idCita;
    private final JTextField idMascota;
    private final JLabel seleccion = new JLabel("Seleccione una cita en la tabla.");
    private final JLabel resultado = new JLabel(" ");

    public PanelMedicoView(ClinicaFacade clinica) {
        this.clinica = clinica;
        ControladorFarmacia controlador = new ControladorFarmacia(clinica);
        setLayout(new BorderLayout(5, 5));
        JPanel superior = new JPanel(new BorderLayout());
        listado.setEditable(false);
        superior.add(new JScrollPane(listado), BorderLayout.CENTER);
        JPanel acciones = new JPanel();
        acciones.add(soloPendientes);
        acciones.add(NotificacionFarmaciaView.crearBoton("Actualizar mascotas y citas", this::actualizar));
        soloPendientes.addActionListener(evento -> actualizar());
        superior.add(acciones, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);
        citas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(citas), BorderLayout.CENTER);

        JPanel inferior = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        idCita = NotificacionFarmaciaView.agregarCampo(formulario, "Cita seleccionada");
        idMascota = NotificacionFarmaciaView.agregarCampo(formulario, "ID de mascota");
        idCita.setEditable(false);
        idMascota.setEditable(false);
        JTextField motivo = NotificacionFarmaciaView.agregarCampo(formulario, "Motivo");
        JTextField diagnostico = NotificacionFarmaciaView.agregarCampo(formulario, "Diagnóstico");
        JTextField tratamiento = NotificacionFarmaciaView.agregarCampo(formulario, "Tratamiento");
        JTextField veterinario = NotificacionFarmaciaView.agregarCampo(formulario, "Veterinario");
        formulario.add(NotificacionFarmaciaView.crearBoton("Guardar atención médica", () -> {
            controlador.registrarAtencion(idCita.getText(), idMascota.getText(), motivo.getText(),
                    diagnostico.getText(), tratamiento.getText(), veterinario.getText());
            String atendida = idCita.getText();
            motivo.setText("");
            diagnostico.setText("");
            tratamiento.setText("");
            actualizar();
            resultado.setText("Atención guardada. Cita " + atendida + ": ATENDIDA. Consulte Historias.");
        }));
        inferior.add(seleccion, BorderLayout.NORTH);
        inferior.add(formulario, BorderLayout.CENTER);
        inferior.add(resultado, BorderLayout.SOUTH);
        add(inferior, BorderLayout.SOUTH);
        citas.getSelectionModel().addListSelectionListener(evento -> {
            int fila = citas.getSelectedRow();
            if (fila < 0) return;
            idCita.setText(datosCitas.getValueAt(fila, 0).toString());
            idMascota.setText(datosCitas.getValueAt(fila, 1).toString());
            Mascota mascota = clinica.getAtencion().buscarMascota(idMascota.getText());
            seleccion.setText(mascota.getNombre() + " | Edad: " + mascota.getEdad()
                    + " | Raza: " + mascota.getRaza() + " | Peso: " + mascota.getPeso());
            resultado.setText(" ");
        });
        actualizar();
    }

    public void actualizar() {
        listado.setText("MASCOTAS REGISTRADAS (use el ID en recetas, historias y seguimientos)\n");
        for (Mascota mascota : clinica.getAtencion().listarMascotas()) {
            listado.append(mascota.getIdMascota() + " | " + mascota.getNombre()
                    + " | edad: " + mascota.getEdad() + " | raza: " + mascota.getRaza() + "\n");
        }
        datosCitas.setRowCount(0);
        idCita.setText("");
        idMascota.setText("");
        seleccion.setText("Seleccione una cita PENDIENTE para registrar la atención.");
        for (Cita cita : soloPendientes.isSelected() ? clinica.obtenerCitasPendientes() : clinica.listarCitas()) {
            datosCitas.addRow(new Object[]{cita.getIdCita(), cita.getMascota().getIdMascota(),
                    cita.getMascota().getNombre(), cita.getCliente().getNombre(), cita.getFechaHora(),
                    cita.getPrioridad(), cita.getEstado()});
        }
    }
}