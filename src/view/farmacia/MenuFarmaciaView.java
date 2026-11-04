package view.farmacia;

import controller.farmacia.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuFarmaciaView extends JFrame {
    private final Timer temporizador;

    public MenuFarmaciaView(ControladorFarmacia controlador) {
        super("PetCenter - Farmacia y atención médica");
        ClinicaFacade clinica = controlador.getClinica();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        JTabbedPane pestanas = new JTabbedPane();
        PanelInventarioView inventario = new PanelInventarioView(controlador);
        DashboardClinicoView resumen = new DashboardClinicoView(clinica);
        AlertaVencimientoView alertas = new AlertaVencimientoView(clinica);
        pestanas.addTab("Resumen", resumen);
        pestanas.addTab("Inventario", inventario);
        pestanas.addTab("Stock y lotes", new ControlStockView(controlador));
        pestanas.addTab("Detalle", new DetalleMedicamentoView(clinica));
        pestanas.addTab("Atención", new PanelMedicoView(clinica));
        pestanas.addTab("Recetas", new FormularioRecetaView(controlador));
        pestanas.addTab("Historias", new VistaHistoriaClinicaView(clinica));
        pestanas.addTab("Internamientos", new FormularioInternamientoView(clinica));
        pestanas.addTab("Evoluciones", new VistaEvolucionView(clinica));
        pestanas.addTab("Alertas", alertas);
        pestanas.addChangeListener(evento -> {
            inventario.actualizar();
            resumen.actualizar();
        });
        add(pestanas);
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

