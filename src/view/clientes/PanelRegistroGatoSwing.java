package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;
import model.clientes.MascotaBuilder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelRegistroGatoSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final ValidadorEntradaSwing validador;
    private final DialogoMensajeSwing dialogo;
    private final Runnable onMascotaRegistrada;

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtRaza = new JTextField();
    private final JTextField txtPeso = new JTextField();
    private final JCheckBox chkEsArisco = new JCheckBox("Sí, es arisco");

    public PanelRegistroGatoSwing(VeterinariaFacade facade, Runnable onMascotaRegistrada) {
        this.facade = facade;
        this.validador = new ValidadorEntradaSwing();
        this.dialogo = new DialogoMensajeSwing();
        this.onMascotaRegistrada = onMascotaRegistrada;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Registrar Gato"));

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Código de Mascota:"));
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Edad (años):"));
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Raza:"));
        panelFormulario.add(txtRaza);

        panelFormulario.add(new JLabel("Peso (kg):"));
        panelFormulario.add(txtPeso);

        panelFormulario.add(new JLabel("Temperamento:"));
        panelFormulario.add(chkEsArisco);

        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnRegistrar = new JButton("Registrar Gato");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> registrarGato());

        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void registrarGato() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String raza = txtRaza.getText().trim();

            if (validador.estaVacio(codigo) || validador.estaVacio(nombre)) {
                dialogo.mostrarAdvertencia(this, "Validación", "Código y Nombre son obligatorios.");
                return;
            }

            int edad = validador.parsearEntero(txtEdad.getText(), "Edad");
            double peso = validador.parsearDouble(txtPeso.getText(), "Peso");
            boolean esArisco = chkEsArisco.isSelected();

            Mascota gato = new MascotaBuilder()
                    .setCodigo(codigo)
                    .setNombre(nombre)
                    .setEdad(edad)
                    .setRaza(raza)
                    .setPeso(peso)
                    .buildGato(esArisco);

            facade.registrarMascota(gato);
            dialogo.mostrarInformacion(this, "Registro exitoso", "¡Gato registrado exitosamente!");
            limpiarCampos();

            if (onMascotaRegistrada != null) {
                onMascotaRegistrada.run();
            }
        } catch (Exception ex) {
            dialogo.mostrarError(this, "Error", "Error al registrar gato: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtRaza.setText("");
        txtPeso.setText("");
        chkEsArisco.setSelected(false);
    }
}