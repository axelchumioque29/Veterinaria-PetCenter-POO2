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
import controller.farmacia.ControladorFarmacia;
import controller.farmacia.MainModulo2;
import view.farmacia.MenuFarmaciaView;

// Módulo 3 (Caja y Facturación)
import controller.caja.ControladorCaja;
import model.caja.AuditoriaFinanciera;
import model.caja.Cajero;
import view.caja.VentanaCajaSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

            // 2. Inicializar Módulo 2 (Clínico y Farmacia - Fabricio)
            ClinicaFacade facadeFabricio = MainModulo2.crearDemostracion();

            // 3. Inicializar Módulo 3 (Caja - Billy) -> Define al operador de turno para todo el sistema
            String idOperador = JOptionPane.showInputDialog(null, "Ingrese ID / DNI del Operador de Turno:", "Inicio de Turno", JOptionPane.QUESTION_MESSAGE);
            String nombreOperador = JOptionPane.showInputDialog(null, "Ingrese Nombre del Operador de Turno:", "Inicio de Turno", JOptionPane.QUESTION_MESSAGE);
            String saldoStr = JOptionPane.showInputDialog(null, "Ingrese saldo inicial en caja (S/):", "100.0");

            double saldoInicial = 100.0;
            try {
                if (saldoStr != null && !saldoStr.trim().isEmpty()) {
                    saldoInicial = Double.parseDouble(saldoStr.trim());
                }
            } catch (NumberFormatException ignored) {}

            final Cajero[] cajeroActual = { new Cajero(
                    (idOperador != null && !idOperador.trim().isEmpty()) ? idOperador.trim() : "C01",
                    (nombreOperador != null && !nombreOperador.trim().isEmpty()) ? nombreOperador.trim() : "Operador"
            )};

            ControladorCaja controladorCaja = new ControladorCaja();
            controladorCaja.iniciarTurno(cajeroActual[0], saldoInicial);
            AuditoriaFinanciera.registrarAccion(cajeroActual[0].getId(), "Apertura de caja inicial con S/ " + saldoInicial);

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

            // Navegación Módulo 1 (Usa el mismo operador de turno que Caja)
            btnRecepcion.addActionListener(e -> {
                ventanaPrincipal.setVisible(false);
                Recepcionista recepcionistaActual = new Recepcionista(
                        cajeroActual[0].getId(),
                        cajeroActual[0].getNombre(),
                        "987654321",
                        "REC-" + cajeroActual[0].getId()
                );
                VentanaRecepcionSwing ventanaAxel = new VentanaRecepcionSwing(facadeAxel, recepcionistaActual);
                ventanaAxel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventanaAxel.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent ev) {
                        ventanaPrincipal.setVisible(true);
                    }
                });
                ventanaAxel.setVisible(true);
            });

            // Navegación Módulo 2
            btnFarmacia.addActionListener(e -> {
                ventanaPrincipal.setVisible(false);
                facadeFabricio.iniciarMonitoreo();
                MenuFarmaciaView ventanaFabricio = new MenuFarmaciaView(new ControladorFarmacia(facadeFabricio));
                ventanaFabricio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventanaFabricio.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent ev) {
                        facadeFabricio.detenerMonitoreo();
                        ventanaPrincipal.setVisible(true);
                    }
                });
                ventanaFabricio.setVisible(true);
            });

            // Navegación Módulo 3 (Actualiza al operador global si hubo cambio de turno)
            btnCaja.addActionListener(e -> {
                ventanaPrincipal.setVisible(false);
                VentanaCajaSwing ventanaBilly = new VentanaCajaSwing(controladorCaja, cajeroActual[0], repoClientes);
                ventanaBilly.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventanaBilly.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent ev) {
                        cajeroActual[0] = ventanaBilly.getCajeroTurno();
                        ventanaPrincipal.setVisible(true);
                    }
                });
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