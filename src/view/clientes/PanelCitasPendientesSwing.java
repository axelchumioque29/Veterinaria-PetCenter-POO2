package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelCitasPendientesSwing extends JPanel {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final VeterinariaFacade facade;
    private final DialogoMensajeSwing dialogo;
    private final Runnable onCitaAtendida;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaCitas;

    public PanelCitasPendientesSwing(VeterinariaFacade facade, Runnable onCitaAtendida) {
        this.facade = facade;
        this.dialogo = new DialogoMensajeSwing();
        this.onCitaAtendida = onCitaAtendida;
        this.modeloTabla = new DefaultTableModel(
                new Object[]{"ID Cita", "DNI Cliente", "Mascota", "Fecha y Hora", "Prioridad", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablaCitas = new JTable(modeloTabla);
        inicializarComponentes();
        cargarCitasPendientes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Citas Pendientes de Atención"));

        add(new JScrollPane(tablaCitas), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnMarcarAtendida = new JButton("Marcar como Atendida");

        btnActualizar.addActionListener(e -> cargarCitasPendientes());
        btnMarcarAtendida.addActionListener(e -> marcarSeleccionadaComoAtendida());

        panelBotones.add(btnActualizar);
        panelBotones.add(btnMarcarAtendida);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarCitasPendientes() {
        modeloTabla.setRowCount(0);
        List<Cita> citas = facade.obtenerCitasPendientes();
        if (citas == null) {
            return;
        }

        for (Cita c : citas) {
            String dniCliente = (c.getCliente() != null)
                    ? c.getCliente().getDni()
                    : "";
            String nombreMascota = (c.getMascota() != null)
                    ? c.getMascota().getNombre()
                    : "";
            String fechaTexto = (c.getFechaHora() != null)
                    ? c.getFechaHora().format(FORMATO_FECHA)
                    : "";
            String prioridad = (c.getPrioridad() != null)
                    ? c.getPrioridad()
                    : "General";

            modeloTabla.addRow(new Object[]{
                    c.getIdCita(),
                    dniCliente,
                    nombreMascota,
                    fechaTexto,
                    prioridad,
                    c.getEstado()
            });
        }
    }

    private void marcarSeleccionadaComoAtendida() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada == -1) {
            dialogo.mostrarAdvertencia(this, "Selección requerida", "Selecciona una cita pendiente de la tabla.");
            return;
        }

        try {
            String idCita = String.valueOf(modeloTabla.getValueAt(filaSeleccionada, 0));
            boolean actualizada = facade.marcarCitaComoAtendida(idCita);
            if (!actualizada) {
                dialogo.mostrarAdvertencia(this, "Aviso", "No se pudo marcar como atendida la cita " + idCita + ".");
                return;
            }

            dialogo.mostrarInformacion(this, "Cita atendida", "La cita " + idCita + " fue marcada como ATENDIDA.");
            cargarCitasPendientes();

            if (onCitaAtendida != null) {
                onCitaAtendida.run();
            }
        } catch (Exception ex) {
            dialogo.mostrarError(this, "Error", "No se pudo actualizar la cita: " + ex.getMessage());
        }
    }
}