package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.AtencionEmergencia;
import model.clientes.AtencionGeneral;
import model.clientes.IEstrategiaAtencion;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

public class PanelAgendarCitaSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final ValidadorEntradaSwing validador;
    private final DialogoMensajeSwing dialogo;
    private final Runnable onCitaAgendada;

    private final JTextField txtIdCita = new JTextField();
    private final JTextField txtDniCliente = new JTextField();
    private final JTextField txtCodigoMascota = new JTextField();
    private final JTextField txtFechaHora = new JTextField("2026-11-22 10:30");
    private final JComboBox<String> cmbTipoAtencion = new JComboBox<>(new String[]{
            "Consulta General (20 min)",
            "Emergencia (45 min - Alta Prioridad)"
    });

    public PanelAgendarCitaSwing(VeterinariaFacade facade, Runnable onCitaAgendada) {
        this.facade = facade;
        this.validador = new ValidadorEntradaSwing();
        this.dialogo = new DialogoMensajeSwing();
        this.onCitaAgendada = onCitaAgendada;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Agendar Nueva Cita"));

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID de la Cita:"));
        panelFormulario.add(txtIdCita);

        panelFormulario.add(new JLabel("DNI del Cliente Propietario:"));
        panelFormulario.add(txtDniCliente);

        panelFormulario.add(new JLabel("Código de la Mascota:"));
        panelFormulario.add(txtCodigoMascota);

        panelFormulario.add(new JLabel("Fecha y Hora (aaaa-MM-dd HH:mm):"));
        panelFormulario.add(txtFechaHora);

        panelFormulario.add(new JLabel("Tipo de Atención:"));
        panelFormulario.add(cmbTipoAtencion);

        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnAgendar = new JButton("Agendar Cita");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnAgendar.addActionListener(e -> agendarCita());

        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnAgendar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void agendarCita() {
        try {
            String idCita = txtIdCita.getText().trim();
            String dniCliente = txtDniCliente.getText().trim();
            String codigoMascota = txtCodigoMascota.getText().trim();

            if (validador.estaVacio(idCita) || validador.estaVacio(dniCliente) || validador.estaVacio(codigoMascota)) {
                dialogo.mostrarAdvertencia(this, "Validación", "ID de cita, DNI del cliente y código de mascota son obligatorios.");
                return;
            }

            LocalDateTime fechaHora = validador.parsearFechaHora(txtFechaHora.getText());
            IEstrategiaAtencion estrategia = (cmbTipoAtencion.getSelectedIndex() == 1)
                    ? new AtencionEmergencia()
                    : new AtencionGeneral();

            facade.agendarCita(idCita, dniCliente, codigoMascota, fechaHora, estrategia);

            dialogo.mostrarInformacion(
                    this,
                    "Cita agendada",
                    "¡Cita agendada con éxito! Prioridad: " + estrategia.obtenerPrioridad() + " | Estado: PENDIENTE"
            );
            limpiarCampos();

            if (onCitaAgendada != null) {
                onCitaAgendada.run();
            }
        } catch (Exception ex) {
            dialogo.mostrarError(this, "Error", "Error al agendar cita: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtIdCita.setText("");
        txtDniCliente.setText("");
        txtCodigoMascota.setText("");
        txtFechaHora.setText("");
        cmbTipoAtencion.setSelectedIndex(0);
    }
}