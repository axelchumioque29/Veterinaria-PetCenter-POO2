package view.farmacia;

import controller.farmacia.ClinicaFacade;
import javax.swing.*;
import java.awt.*;

public class DashboardClinicoView extends JPanel {
    private final ClinicaFacade clinica;
    private final JTextArea resumen = new JTextArea();
    public DashboardClinicoView(ClinicaFacade clinica) {
        this.clinica = clinica;
        setLayout(new BorderLayout());
        resumen.setEditable(false);
        add(NotificacionFarmaciaView.crearBoton("Actualizar resumen y auditoría", this::actualizar), BorderLayout.NORTH);
        add(new JScrollPane(resumen), BorderLayout.CENTER);
        actualizar();
    }
    public void actualizar() {
        resumen.setText("PetCenter - Módulo 2 - Fabricio Agurto\n"
                + "Medicamentos: " + clinica.listarMedicamentos().size()
                + "\nMascotas: " + clinica.getAtencion().listarMascotas().size()
                + "\nRecetas: " + clinica.getAtencion().contarRecetas()
                + "\nInternamientos activos: " + clinica.getAtencion().contarInternados()
                + "\n\nAUDITORÍA DE LA SESIÓN\n");
        for (String registro : clinica.consultarAuditoria()) resumen.append(registro + "\n");
    }
}

