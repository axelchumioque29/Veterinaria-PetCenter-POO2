package controller.farmacia;

import model.clientes.*;
import view.farmacia.MenuFarmaciaView;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainModulo2 {
    public static ClinicaFacade crearDemostracion() {
        ClinicaFacade clinica = new ClinicaFacade();
        Mascota mascota = new Mascota("M001", "Luna", 3, "Mestiza") {
            @Override
            public String emitirSonido() { return "Guau"; }
        };
        ArrayList<Mascota> mascotas = new ArrayList<>();
        mascotas.add(mascota);
        clinica.registrarCliente(new Cliente("00000000", "Cliente de demostración", "000000000",
                "Dirección de demostración", mascotas));
        clinica.registrarMedicamento("Medicamento", "MED001", "Producto de demostración", 15.0,
                "Principio de demostración", 5);
        clinica.registrarLote("MED001", "LOTE001", LocalDate.now().plusMonths(6), 20);
        clinica.registrarLote("MED001", "LOTE-VENCIDO", LocalDate.now().minusDays(1), 2);
        clinica.registrarMedicamento("Vacuna", "VAC001", "Vacuna de demostración", 30.0,
                "Principio de demostración", 3);
        return clinica;
    }

    public static void main(String[] argumentos) throws Exception {
        ClinicaFacade clinica = crearDemostracion();
        if (argumentos.length > 0 && "--consola".equals(argumentos[0])) {
            clinica.iniciarMonitoreo();
            try {
                clinica.registrarReceta("REC001", "M001", "MED001", 2, "Indicación de prueba", "Veterinario de prueba");
                clinica.registrarInternamiento("M001", "Observación de prueba");
                System.out.println("Módulo 2 iniciado. Medicamentos: " + clinica.listarMedicamentos().size());
                System.out.println("Stock MED001: " + clinica.getInventario().buscar("MED001").getStockDisponible());
                System.out.println("Recetas: " + clinica.consultarHistoria("M001").getRecetas().size());
            } finally {
                clinica.detenerMonitoreo();
            }
        } else if (argumentos.length > 0 && "--prueba-interfaz".equals(argumentos[0])) {
            SwingUtilities.invokeAndWait(() -> {
                MenuFarmaciaView menu = new MenuFarmaciaView(new ControladorFarmacia(clinica));
                clinica.iniciarMonitoreo();
                menu.setVisible(true);
                Timer cierre = new Timer(1500, evento -> menu.dispose());
                cierre.setRepeats(false);
                cierre.start();
                System.out.println("Interfaz Swing creada con sus diez pestañas.");
            });
        } else {
            new CoordinadorFarmacia(clinica).iniciar();
        }
    }
}

