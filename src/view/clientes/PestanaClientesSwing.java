package view.clientes;

import controller.clientes.VeterinariaFacade;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PestanaClientesSwing extends JPanel {
    private final PanelRegistroClienteSwing panelRegistro;
    private final PanelBuscarClienteSwing panelBuscar;
    private final PanelTablaClientesSwing panelTabla;
    private final Runnable onDatosModificados;

    public PestanaClientesSwing(VeterinariaFacade facade, Runnable onDatosModificados) {
        this.onDatosModificados = onDatosModificados;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.panelTabla = new PanelTablaClientesSwing(facade);
        this.panelBuscar = new PanelBuscarClienteSwing(facade, panelTabla::mostrarListaClientes);
        this.panelRegistro = new PanelRegistroClienteSwing(facade, () -> {
            panelTabla.cargarClientes();
            if (this.onDatosModificados != null) {
                this.onDatosModificados.run();
            }
        });

        JPanel panelDerecho = new JPanel(new BorderLayout(8, 8));
        panelDerecho.add(panelBuscar, BorderLayout.NORTH);
        panelDerecho.add(panelTabla, BorderLayout.CENTER);

        JPanel contenidoPrincipal = new JPanel(new GridLayout(1, 2, 12, 12));
        contenidoPrincipal.add(panelRegistro);
        contenidoPrincipal.add(panelDerecho);

        add(contenidoPrincipal, BorderLayout.CENTER);
    }

    public void refrescarDatos() {
        panelTabla.cargarClientes();
    }
}