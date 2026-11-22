package view.clientes;

// Módulo 1 (Recepción)
import controller.clientes.ControladorCita;
import controller.clientes.ControladorCliente;
import controller.clientes.ControladorMascota;
import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.clientes.Mascota;
import model.clientes.Recepcionista;
import model.clientes.RepositorioGenerico;

// Módulo 2 (Clínico y Farmacia)
import controller.farmacia.ClinicaFacade;
import controller.farmacia.CoordinadorFarmacia;
import controller.farmacia.MainModulo2;

// Módulo 3 (Caja y Facturación)
import controller.caja.ControladorCaja;
import model.caja.AuditoriaFinanciera;
import model.caja.Cajero;
import view.caja.VentanaCajaSwing;

import javax.swing.*;
import java.awt.*;

public class MainSistemaIntegradoSwing {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // 1. Inicializar Módulo 1 (Recepción - Axel)
            RepositorioGenerico<Cliente> repoClientes = new RepositorioGenerico<>();
            RepositorioGenerico<Mascota> repoMascotas = new RepositorioGenerico<>();
            RepositorioGenerico<Cita> repoCitas = new RepositorioGenerico<>();

            VeterinariaFacade facadeAxel = new VeterinariaFacade(
                    new ControladorCliente(repoClientes),
                    new ControladorMascota(repoMascotas),
                    new ControladorCita(repoCitas)
            );
            Recepcionista recepcionistaTurno = new Recepcionista("74125896", "Valeria Rojas", "987654321", "REC-01");

            // 2. Inicializar Módulo 2 (Clínico y Farmacia - Fabricio)
            ClinicaFacade facadeFabricio = MainModulo2.crearDemostracion();

            // 3. Inicializar Módulo 3 (Caja - Billy)
            String idCajero = JOptionPane.showInputDialog(null, "Ingrese ID del Cajero:", "Configuración Inicial de Caja", JOptionPane.QUESTION_MESSAGE);
            String nombreCajero = JOptionPane.showInputDialog(null, "Ingrese Nombre del Cajero:", "Configuración Inicial de Caja", JOptionPane.QUESTION_MESSAGE);
            String saldoStr = JOptionPane.showInputDialog(null, "Ingrese saldo inicial en caja (S/):", "100.0");

            double saldoInicial = 100.0;
            try {
                if (saldoStr != null && !saldoStr.trim().isEmpty()) {
                    saldoInicial = Double.parseDouble(saldoStr.trim());
                }
            } catch (NumberFormatException ignored) {}

            Cajero cajeroTurno = new Cajero(
                    (idCajero != null && !idCajero.trim().isEmpty()) ? idCajero.trim() : "C01",
                    (nombreCajero != null && !nombreCajero.trim().isEmpty()) ? nombreCajero.trim() : "Cajero"
            );
            ControladorCaja controladorCaja = new ControladorCaja();
            controladorCaja.iniciarTurno(cajeroTurno, saldoInicial);
            AuditoriaFinanciera.registrarAccion(cajeroTurno.getId(), "Apertura de caja inicial con S/ " + saldoInicial);

            // 4. Ventana Principal del Sistema Integrado
            JFrame ventanaPrincipal = new JFrame("PetCenter - Sistema Veterinario Integrado");
            ventanaPrincipal.setSize(460, 320);
            ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventanaPrincipal.setLocationRelativeTo(null);
            ventanaPrincipal.setLayout(new GridLayout(4, 1, 12, 12));
            ((JPanel) ventanaPrincipal.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JLabel lblTitulo = new JLabel("SISTEMA VETERINARIO INTEGRADO", SwingConstants.CENTER);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

            JButton btnRecepcion = new JButton("1. Módulo de Recepción (Clientes y Citas)");
            JButton btnFarmacia = new JButton("2. Módulo Clínico y Farmacia (Inventario y Recetas)");
            JButton btnCaja = new JButton("3. Módulo de Facturación (Caja y Cobros)");

            btnRecepcion.addActionListener(e -> {
                VentanaRecepcionSwing ventanaAxel = new VentanaRecepcionSwing(facadeAxel, recepcionistaTurno);
                ventanaAxel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventanaAxel.setVisible(true);
            });

            btnFarmacia.addActionListener(e -> new CoordinadorFarmacia(facadeFabricio).iniciar());

            btnCaja.addActionListener(e -> {
                VentanaCajaSwing ventanaBilly = new VentanaCajaSwing(controladorCaja, cajeroTurno, repoClientes);
                ventanaBilly.setVisible(true);
            });

            ventanaPrincipal.add(lblTitulo);
            ventanaPrincipal.add(btnRecepcion);
            ventanaPrincipal.add(btnFarmacia);
            ventanaPrincipal.add(btnCaja);
            ventanaPrincipal.setVisible(true);
        });
    }
}