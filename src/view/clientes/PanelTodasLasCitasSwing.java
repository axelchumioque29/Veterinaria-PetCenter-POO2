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

public class PanelTodasLasCitasSwing extends JPanel {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final VeterinariaFacade facade;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaCitas;

    public PanelTodasLasCitasSwing(VeterinariaFacade facade) {
        this.facade = facade;
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
        cargarTodasLasCitas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Todas las Citas Registradas"));

        add(new JScrollPane(tablaCitas), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarTodasLasCitas());

        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarTodasLasCitas() {
        modeloTabla.setRowCount(0);
        List<Cita> citas = facade.listarTodasLasCitas();
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
}