package controller.farmacia;

import view.farmacia.MenuFarmaciaView;
import javax.swing.SwingUtilities;

public class CoordinadorFarmacia {
    private final ClinicaFacade clinica;
    public CoordinadorFarmacia(ClinicaFacade clinica) { this.clinica = clinica; }
    public void iniciar() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::iniciar);
            return;
        }
        MenuFarmaciaView menu = new MenuFarmaciaView(new ControladorFarmacia(clinica));
        clinica.iniciarMonitoreo();
        menu.setVisible(true);
    }
}

