package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Recepcionista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class VentanaRecepcionSwing extends JFrame {
    private final VeterinariaFacade facade;
    private final PanelCabeceraSwing panelCabecera;
    private final PanelResumenCitasSwing panelResumen;
    private final PestanaClientesSwing pestanaClientes;
    private final PestanaMascotasSwing pestanaMascotas;
    private final PestanaCitasSwing pestanaCitas;
    private final PestanaVeterinariosSwing pestanaVeterinarios;
    private final PanelEstadoSwing panelEstado;
    private final JTabbedPane tabbedPane;

    public VentanaRecepcionSwing(VeterinariaFacade facade, Recepcionista recepcionista) {
        this.facade = facade;
        this.panelCabecera = new PanelCabeceraSwing(recepcionista);
        this.panelResumen = new PanelResumenCitasSwing(facade);
        this.pestanaClientes = new PestanaClientesSwing(facade, this::actualizarResumenYEstado);
        this.pestanaMascotas = new PestanaMascotasSwing(facade, this::actualizarResumenYEstado);
        this.pestanaCitas = new PestanaCitasSwing(facade, this::actualizarResumenYEstado);
        this.pestanaVeterinarios = new PestanaVeterinariosSwing();
        this.panelEstado = new PanelEstadoSwing();
        this.tabbedPane = new JTabbedPane();

        inicializarVentana();
        actualizarResumenYEstado();
    }

    private void inicializarVentana() {
        setTitle("Sistema de Gestión Veterinaria - Recepción");
        setSize(1080, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(panelCabecera, BorderLayout.NORTH);

        tabbedPane.addTab("Clientes", pestanaClientes);
        tabbedPane.addTab("Mascotas", pestanaMascotas);
        tabbedPane.addTab("Citas", pestanaCitas);
        tabbedPane.addTab("Veterinarios", pestanaVeterinarios);

        tabbedPane.addChangeListener(e -> refrescarPestanaActual());

        JPanel panelCentro = new JPanel(new BorderLayout(8, 8));
        panelCentro.add(panelResumen, BorderLayout.NORTH);
        panelCentro.add(tabbedPane, BorderLayout.CENTER);

        // Barra inferior con el estado y el botón de regreso
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.addActionListener(e -> dispose());

        JPanel panelInferior = new JPanel(new BorderLayout(8, 0));
        panelInferior.add(panelEstado, BorderLayout.CENTER);
        panelInferior.add(btnVolver, BorderLayout.EAST);

        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public void actualizarRecepcionista(Recepcionista recepcionista) {
        panelCabecera.actualizarRecepcionista(recepcionista);
    }

    private void refrescarPestanaActual() {
        int indice = tabbedPane.getSelectedIndex();
        if (indice == 0) {
            pestanaClientes.refrescarDatos();
        } else if (indice == 1) {
            pestanaMascotas.refrescarDatos();
        } else if (indice == 2) {
            pestanaCitas.refrescarDatos();
        }
        actualizarResumenYEstado();
    }

    private void actualizarResumenYEstado() {
        panelResumen.actualizarResumen();
        int totalClientes = (facade.listarClientes() != null) ? facade.listarClientes().size() : 0;
        int totalMascotas = (facade.listarMascotas() != null) ? facade.listarMascotas().size() : 0;
        int totalCitas = (facade.listarTodasLasCitas() != null) ? facade.listarTodasLasCitas().size() : 0;
        panelEstado.mostrarMensaje("Clientes: " + totalClientes + " | Mascotas: " + totalMascotas + " | Citas: " + totalCitas);
    }
}