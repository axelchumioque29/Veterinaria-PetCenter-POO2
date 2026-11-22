package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.EstadoCita;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.List;

public class PanelResumenCitasSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final JLabel lblClientes;
    private final JLabel lblMascotas;
    private final JLabel lblPendientes;
    private final JLabel lblAtendidas;

    public PanelResumenCitasSwing(VeterinariaFacade facade) {
        this.facade = facade;
        this.lblClientes = new JLabel("Clientes: 0");
        this.lblMascotas = new JLabel("Mascotas: 0");
        this.lblPendientes = new JLabel("Citas pendientes: 0");
        this.lblAtendidas = new JLabel("Citas atendidas: 0");

        inicializarComponentes();
        actualizarResumen();
    }

    private void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 16, 6));
        setBorder(BorderFactory.createTitledBorder("Resumen del Módulo"));

        add(lblClientes);
        add(lblMascotas);
        add(lblPendientes);
        add(lblAtendidas);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarResumen());
        add(btnActualizar);
    }

    public void actualizarResumen() {
        int totalClientes = facade.listarClientes().size();
        int totalMascotas = facade.listarMascotas().size();
        List<Cita> todasLasCitas = facade.listarTodasLasCitas();

        int pendientes = 0;
        int atendidas = 0;
        for (Cita c : todasLasCitas) {
            if (c.getEstado() == EstadoCita.ATENDIDA) {
                atendidas++;
            } else {
                pendientes++;
            }
        }

        lblClientes.setText("Clientes: " + totalClientes);
        lblMascotas.setText("Mascotas: " + totalMascotas);
        lblPendientes.setText("Citas pendientes: " + pendientes);
        lblAtendidas.setText("Citas atendidas: " + atendidas);
    }
}
