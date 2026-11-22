package view.caja;

import javax.swing.*;
import java.awt.*;

public class FormularioCobroView extends JPanel {
    public JTextField txtDni = new JTextField();
    public JTextField txtIdTx = new JTextField("TX-001");
    public VistaMetodoPagoView selectorPago = new VistaMetodoPagoView(); // Usa tu propia clase
    public JTextField txtConcepto = new JTextField();
    public JTextField txtMonto = new JTextField();
    public JButton btnCobrar = new JButton("Registrar Cobro");

    public FormularioCobroView() {
        setLayout(new GridLayout(6, 2, 8, 10));
        setBorder(BorderFactory.createTitledBorder("Registro de Nuevo Cobro"));

        add(new JLabel("DNI del Cliente:")); add(txtDni);
        add(new JLabel("ID Transacción:"));  add(txtIdTx);
        add(new JLabel("Metodo de Pago:"));  add(selectorPago);
        add(new JLabel("Concepto:"));        add(txtConcepto);
        add(new JLabel("Monto (S/):"));      add(txtMonto);
        add(new JLabel(""));                 add(btnCobrar);
    }
}