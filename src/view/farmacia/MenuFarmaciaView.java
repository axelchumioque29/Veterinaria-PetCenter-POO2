package view.farmacia;

import controller.farmacia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFarmaciaView extends JFrame {
    private final Timer temporizador;

    public MenuFarmaciaView(ControladorFarmacia controlador) {
        super("PetCenter - Farmacia y atención médica");
        ClinicaFacade clinica = controlador.getClinica();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane pestanas = new JTabbedPane();
        PanelInventarioView inventario = new PanelInventarioView(controlador);
        DashboardClinicoView resumen = new DashboardClinicoView(clinica);
        AlertaVencimientoView alertas = new AlertaVencimientoView(clinica);
        PanelMedicoView atencion = new PanelMedicoView(clinica);
        pestanas.addTab("Resumen", resumen);
        pestanas.addTab("Inventario", inventario);
        pestanas.addTab("Stock y lotes", new ControlStockView(controlador));
        pestanas.addTab("Detalle", new DetalleMedicamentoView(clinica));
        pestanas.addTab("Atención", atencion);
        pestanas.addTab("Recetas", new FormularioRecetaView(controlador));
        pestanas.addTab("Historias", new VistaHistoriaClinicaView(clinica));
        pestanas.addTab("Internamientos", new FormularioInternamientoView(clinica));
        pestanas.addTab("Evoluciones", new VistaEvolucionView(clinica));
        pestanas.addTab("Alertas", alertas);
        pestanas.addChangeListener(evento -> {
            inventario.actualizar();
            resumen.actualizar();
            if (pestanas.getSelectedComponent() == atencion) atencion.actualizar();
        });

        // Botón inferior para volver al menú principal
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.addActionListener(e -> dispose());
        panelInferior.add(btnVolver);

        add(pestanas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        temporizador = new Timer(1000, evento -> alertas.actualizar());
        temporizador.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evento) {
                temporizador.stop();
                clinica.detenerMonitoreo();
            }
        });
    }
}
