package view.clientes;

import controller.clientes.VeterinariaFacade;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class PestanaMascotasSwing extends JPanel {
    private final PanelRegistroPerroSwing panelPerro;
    private final PanelRegistroGatoSwing panelGato;
    private final PanelBuscarMascotaSwing panelBuscar;
    private final PanelTablaMascotasSwing panelTabla;
    private final Runnable onDatosModificados;

    public PestanaMascotasSwing(VeterinariaFacade facade, Runnable onDatosModificados) {
        this.onDatosModificados = onDatosModificados;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.panelTabla = new PanelTablaMascotasSwing(facade);
        this.panelBuscar = new PanelBuscarMascotaSwing(facade, panelTabla::mostrarListaMascotas);

        Runnable notificarRegistro = () -> {
            panelTabla.cargarMascotas();
            if (this.onDatosModificados != null) {
                this.onDatosModificados.run();
            }
        };

        this.panelPerro = new PanelRegistroPerroSwing(facade, notificarRegistro);
        this.panelGato = new PanelRegistroGatoSwing(facade, notificarRegistro);

        JTabbedPane pestanasRegistro = new JTabbedPane();
        pestanasRegistro.addTab("Registrar Perro", panelPerro);
        pestanasRegistro.addTab("Registrar Gato", panelGato);

        JPanel panelListadoCompleto = new JPanel(new BorderLayout(8, 8));
        panelListadoCompleto.add(panelBuscar, BorderLayout.NORTH);
        panelListadoCompleto.add(panelTabla, BorderLayout.CENTER);

        JTabbedPane pestanasMascotas = new JTabbedPane();
        pestanasMascotas.addTab("Registro de Mascotas", pestanasRegistro);
        pestanasMascotas.addTab("Consulta y Búsqueda", panelListadoCompleto);

        add(pestanasMascotas, BorderLayout.CENTER);
    }

    public void refrescarDatos() {
        panelTabla.cargarMascotas();
    }
}