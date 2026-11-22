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

    private Cajero cajeroTurno;

    public VentanaCajaSwing(ControladorCaja controlador, Cajero cajeroInicial, RepositorioGenerico<Cliente> repoClientes) {
        this.cajeroTurno = cajeroInicial;

        setTitle("Modulo de Facturación y Caja - Cajero: " + cajeroTurno.getNombre());
        setSize(820, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton btnReporte = new JButton("Generar Reporte");
        JButton btnCerrar = new JButton("Cerrar Caja");
        JButton btnNuevoTurno = new JButton("Abrir Nuevo Turno");
        JButton btnVolver = new JButton("Volver al Menú Principal");

        botones.add(btnReporte);
        botones.add(btnCerrar);
        botones.add(btnNuevoTurno);
        botones.add(btnVolver);

        add(formCobro, BorderLayout.WEST);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // 1. Evento Cobrar
        formCobro.btnCobrar.addActionListener(e -> {
            if (controlador.getCajaActual().getEstado() == EstadoCaja.CERRADA) {
                alerta.mostrarErrorCritico("La caja está CERRADA. Use 'Abrir Nuevo Turno' para ingresar un nuevo cajero.");
                return;
            }
            Cliente cli = repoClientes.buscar(c -> c.getDni().equals(formCobro.txtDni.getText().trim()));
            if (cli == null) {
                alerta.mostrarAdvertencia("Cliente no encontrado. Regístrelo primero en Recepción.");
                return;
            }
            try {
                double monto = Double.parseDouble(formCobro.txtMonto.getText().trim());
                DetalleComprobante det = new DetalleComprobante();
                det.agregarItem(new ItemVenta(new ConceptoCobro("VTA-001", formCobro.txtConcepto.getText(), monto), 1));
                Comprobante boleta = new Boleta("B001", "000001", det, Moneda.PEN, cli);

                String pago = formCobro.selectorPago.obtenerMetodoSeleccionado();
                controlador.registrarCobro(formCobro.txtIdTx.getText(), boleta, pago);

                areaSalida.setText(vistaBoleta.generarTextoBoleta("B001", "000001", cli.getNombre(),
                        formCobro.txtConcepto.getText(), pago, boleta.getSubtotalBase(), boleta.getMontoIGV(), boleta.getTotal()));
            } catch (NumberFormatException ex) {
                alerta.mostrarErrorCritico("Ingrese un monto numérico válido.");
            }
        });

        // 2. Evento Reporte
        btnReporte.addActionListener(e -> {
            CajaDiaria caja = controlador.getCajaActual();
            double ingresos = caja.getSaldoFinal() - caja.getSaldoInicial();
            double neta = CalculadorIGV.extraerSubtotal(ingresos);
            areaSalida.setText(vistaReporte.generarTextoReporte(cajeroTurno.getNombre(), cajeroTurno.getId(),
                    caja.getEstado().toString(), caja.getSaldoInicial(), caja.getSaldoFinal(),
                    ingresos, CalculadorIGV.calcularIGV(neta), neta));
        });

        // 3. Evento Cerrar Caja
        btnCerrar.addActionListener(e -> {
            controlador.getCajaActual().cerrarCaja();
            AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Cierre de caja. Saldo: S/ " + controlador.getCajaActual().getSaldoFinal());
            alerta.mostrarExito("Caja cerrada correctamente. Ya puede abrir un nuevo turno con otro cajero.");
        });

        // 4. Evento Abrir Nuevo Turno (Nuevo Cajero)
        btnNuevoTurno.addActionListener(e -> {
            String nuevoId = JOptionPane.showInputDialog(this, "Ingrese ID del Nuevo Cajero:");
            if (nuevoId == null || nuevoId.trim().isEmpty()) return;

            String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese Nombre del Nuevo Cajero:");
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;

            String saldoStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo saldo inicial en caja (S/):", "100.0");
            try {
                double nuevoSaldo = Double.parseDouble(saldoStr.trim());
                cajeroTurno = new Cajero(nuevoId.trim(), nuevoNombre.trim());
                controlador.iniciarTurno(cajeroTurno, nuevoSaldo);
                AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de nuevo turno con S/ " + nuevoSaldo);

                setTitle("Módulo de Facturación y Caja - Cajero: " + cajeroTurno.getNombre());
                areaSalida.setText("Nuevo turno iniciado.\nCajero: " + cajeroTurno.getNombre() + "\nSaldo inicial: S/ " + nuevoSaldo);
                alerta.mostrarExito("Turno abierto para " + cajeroTurno.getNombre());
            } catch (Exception ex) {
                alerta.mostrarErrorCritico("Saldo inicial inválido.");
            }
        });

        // 5. Evento Volver al Menú Principal
        btnVolver.addActionListener(e -> dispose());
    }
}