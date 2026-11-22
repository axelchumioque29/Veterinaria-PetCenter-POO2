package view.caja;

import javax.swing.JOptionPane;

public class AlertaCajaView {
    public void mostrarErrorCritico(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Caja", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Operacion Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
}