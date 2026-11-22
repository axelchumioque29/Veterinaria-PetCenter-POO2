package view.caja;

import javax.swing.JComboBox;

public class VistaMetodoPagoView extends JComboBox<String> {
    public VistaMetodoPagoView() {
        super(new String[]{"EFECTIVO", "TARJETA", "YAPE", "PLIN"});
    }

    public String obtenerMetodoSeleccionado() {
        return (String) getSelectedItem();
    }
}