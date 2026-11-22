package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cliente;
import model.clientes.ClienteBuilder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelRegistroClienteSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final ValidadorEntradaSwing validador;
    private final DialogoMensajeSwing dialogo;
    private final Runnable onClienteRegistrado;

    private final JTextField txtDni = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtDireccion = new JTextField();

    public PanelRegistroClienteSwing(VeterinariaFacade facade, Runnable onClienteRegistrado) {
        this.facade = facade;
        this.validador = new ValidadorEntradaSwing();
        this.dialogo = new DialogoMensajeSwing();
        this.onClienteRegistrado = onClienteRegistrado;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Cliente"));

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("DNI:"));
        panelFormulario.add(txtDni);

        panelFormulario.add(new JLabel("Nombres:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellidos:"));
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnRegistrar = new JButton("Registrar Cliente");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> registrarCliente());

        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void registrarCliente() {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (validador.estaVacio(dni) || validador.estaVacio(nombre)) {
                dialogo.mostrarAdvertencia(this, "Validación", "DNI y Nombres son obligatorios.");
                return;
            }

            Cliente cliente = new ClienteBuilder()
                    .setDni(dni)
                    .setNombre(nombre)
                    .setApellido(apellido)
                    .setTelefono(telefono)
                    .setDireccion(direccion)
                    .build();

            facade.registrarCliente(cliente);
            dialogo.mostrarInformacion(this, "Registro exitoso", "¡Cliente registrado exitosamente!");
            limpiarCampos();

            if (onClienteRegistrado != null) {
                onClienteRegistrado.run();
            }
        } catch (Exception ex) {
            dialogo.mostrarError(this, "Error", "Error al registrar cliente: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }
}