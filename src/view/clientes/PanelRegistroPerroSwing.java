package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;
import model.clientes.MascotaBuilder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelRegistroPerroSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final ValidadorEntradaSwing validador;
    private final DialogoMensajeSwing dialogo;
    private final Runnable onMascotaRegistrada;

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtRaza = new JTextField();
    private final JTextField txtPeso = new JTextField();
    private final JComboBox<String> cmbEnergia = new JComboBox<>(new String[]{"Alto", "Medio", "Bajo"});

    public PanelRegistroPerroSwing(VeterinariaFacade facade, Runnable onMascotaRegistrada) {
        this.facade = facade;
        this.validador = new ValidadorEntradaSwing();
        this.dialogo = new DialogoMensajeSwing();
        this.onMascotaRegistrada = onMascotaRegistrada;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Registrar Perro"));

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

        panelFormulario.add(new JLabel("Nivel de Energía:"));
        panelFormulario.add(cmbEnergia);

        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnRegistrar = new JButton("Registrar Perro");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> registrarPerro());

        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void registrarPerro() {
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
            String nivelEnergia = (String) cmbEnergia.getSelectedItem();

            Mascota perro = new MascotaBuilder()
                    .setCodigo(codigo)
                    .setNombre(nombre)
                    .setEdad(edad)
                    .setRaza(raza)
                    .setPeso(peso)
                    .buildPerro(nivelEnergia);

            facade.registrarMascota(perro);
            dialogo.mostrarInformacion(this, "Registro exitoso", "¡Perro registrado exitosamente!");
            limpiarCampos();

            if (onMascotaRegistrada != null) {
                onMascotaRegistrada.run();
            }
        } catch (Exception ex) {
            dialogo.mostrarError(this, "Error", "Error al registrar perro: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtRaza.setText("");
        txtPeso.setText("");
        cmbEnergia.setSelectedIndex(0);
    }
}