package view.clientes;

import controller.clientes.VeterinariaFacade;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class PestanaCitasSwing extends JPanel {
    private final PanelAgendarCitaSwing panelAgendar;
    private final PanelCitasPendientesSwing panelPendientes;
    private final PanelTodasLasCitasSwing panelTodasLasCitas;
    private final Runnable onDatosModificados;

    public PestanaCitasSwing(VeterinariaFacade facade, Runnable onDatosModificados) {
        this.onDatosModificados = onDatosModificados;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.panelTodasLasCitas = new PanelTodasLasCitasSwing(facade);
        this.panelPendientes = new PanelCitasPendientesSwing(facade, this::notificarCambioEnCitas);
        this.panelAgendar = new PanelAgendarCitaSwing(facade, this::notificarCambioEnCitas);

        JTabbedPane pestanasCitas = new JTabbedPane();
        pestanasCitas.addTab("Agendar Cita", panelAgendar);
        pestanasCitas.addTab("Citas Pendientes", panelPendientes);
        pestanasCitas.addTab("Todas las Citas", panelTodasLasCitas);

        add(pestanasCitas, BorderLayout.CENTER);
    }

    private void notificarCambioEnCitas() {
        refrescarDatos();
        if (this.onDatosModificados != null) {
            this.onDatosModificados.run();
        }
    }

    public void refrescarDatos() {
        panelPendientes.cargarCitasPendientes();
        panelTodasLasCitas.cargarTodasLasCitas();
    }
}