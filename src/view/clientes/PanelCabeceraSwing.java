package view.clientes;

import model.clientes.Recepcionista;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PanelCabeceraSwing extends JPanel {
    private final JLabel lblTitulo;
    private final JLabel lblRecepcionista;

    public PanelCabeceraSwing(Recepcionista recepcionista) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        this.lblTitulo = new JLabel("VETERINARIA PETCENTER - MODULO DE RECEPCION");
        String nombreRecep = (recepcionista != null) ? recepcionista.getNombre() : "No asignada";
        this.lblRecepcionista = new JLabel("Recepcionista de turno: " + nombreRecep);

        add(lblTitulo, BorderLayout.WEST);
        add(lblRecepcionista, BorderLayout.EAST);
    }

    public void actualizarRecepcionista(Recepcionista recepcionista) {
        String nombreRecep = (recepcionista != null) ? recepcionista.getNombre() : "No asignada";
        lblRecepcionista.setText("Recepcionista de turno: " + nombreRecep);
    }
}