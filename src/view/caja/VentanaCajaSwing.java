package view.caja;

import controller.caja.CalculadorIGV;
import controller.caja.ControladorCaja;
import model.caja.*;
import model.clientes.Cliente;
import model.clientes.RepositorioGenerico;

import javax.swing.*;
import java.awt.*;

public class VentanaCajaSwing extends JFrame {

    private final FormularioCobroView formCobro = new FormularioCobroView();
    private final AlertaCajaView alerta = new AlertaCajaView();
    private final VistaBoletaView vistaBoleta = new VistaBoletaView();
    private final ReporteIngresosView vistaReporte = new ReporteIngresosView();
    private final JTextArea areaSalida = new JTextArea();

    public VentanaCajaSwing(ControladorCaja controlador, Cajero cajero, RepositorioGenerico<Cliente> repoClientes) {
        setTitle("Módulo de Facturación y Caja - " + cajero.getNombre());
        setSize(780, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnReporte = new JButton("Generar Reporte");
        JButton btnCerrar = new JButton("Cerrar Caja");
        botones.add(btnReporte);
        botones.add(btnCerrar);

        add(formCobro, BorderLayout.WEST);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // Evento Cobrar
        formCobro.btnCobrar.addActionListener(e -> {
            if (controlador.getCajaActual().getEstado() == EstadoCaja.CERRADA) {
                alerta.mostrarErrorCritico("La caja está CERRADA.");
                return;
            }
            Cliente cli = repoClientes.buscar(c -> c.getDni().equals(formCobro.txtDni.getText().trim()));
            if (cli == null) {
                alerta.mostrarAdvertencia("Cliente no encontrado. Regístrelo en Recepción.");
                return;
            }
            double monto = Double.parseDouble(formCobro.txtMonto.getText().trim());
            DetalleComprobante det = new DetalleComprobante();
            det.agregarItem(new ItemVenta(new ConceptoCobro("VTA-001", formCobro.txtConcepto.getText(), monto), 1));
            Comprobante boleta = new Boleta("B001", "000001", det, Moneda.PEN, cli);

            String pago = formCobro.selectorPago.obtenerMetodoSeleccionado();
            controlador.registrarCobro(formCobro.txtIdTx.getText(), boleta, pago);

            areaSalida.setText(vistaBoleta.generarTextoBoleta("B001", "000001", cli.getNombre(),
                    formCobro.txtConcepto.getText(), pago, boleta.getSubtotalBase(), boleta.getMontoIGV(), boleta.getTotal()));
        });

        // Evento Reporte
        btnReporte.addActionListener(e -> {
            CajaDiaria caja = controlador.getCajaActual();
            double ingresos = caja.getSaldoFinal() - caja.getSaldoInicial();
            double neta = CalculadorIGV.extraerSubtotal(ingresos);
            areaSalida.setText(vistaReporte.generarTextoReporte(cajero.getNombre(), cajero.getId(),
                    caja.getEstado().toString(), caja.getSaldoInicial(), caja.getSaldoFinal(),
                    ingresos, CalculadorIGV.calcularIGV(neta), neta));
        });

        // Evento Cerrar
        btnCerrar.addActionListener(e -> {
            controlador.getCajaActual().cerrarCaja();
            AuditoriaFinanciera.registrarAccion(cajero.getId(), "Cierre de caja. Saldo: S/ " + controlador.getCajaActual().getSaldoFinal());
            alerta.mostrarExito("Caja cerrada correctamente.");
        });
    }
}