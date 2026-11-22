package view.clientes;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PanelEstadoSwing extends JPanel {
    private final JLabel lblEstado;

    public PanelEstadoSwing() {
        setLayout(new BorderLayout(6, 6));
        setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        this.lblEstado = new JLabel("Estado: Listo");
        add(lblEstado, BorderLayout.WEST);
    }

    public void mostrarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            lblEstado.setText("Estado: Listo");
        } else {
            lblEstado.setText("Estado: " + mensaje.trim());
        }
    }
}