package controller.farmacia;

import view.clientes.*;
import view.farmacia.*;
import view.caja.*;
import model.clientes.EstadoCita;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/** Prueba de ventanas reales. Ejecutar con entorno gráfico, sin java.awt.headless=true. */
public class PruebaNavegacionIntegrada {
    private static int comprobaciones;
    private static String errorDialogo;
    private static int entrada;

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        comprobaciones++;
    }

    public static void main(String[] args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) throw new IllegalStateException("Se requiere un entorno gráfico.");
        // Contesta solo los diálogos de esta ejecución de prueba, en su propio proceso Java.
        Timer dialogos = new Timer(100, evento -> {
            for (Window ventana : Window.getWindows()) {
                if (!(ventana instanceof JDialog) || !ventana.isShowing()) continue;
                JOptionPane panel = buscar(ventana, JOptionPane.class);
                if (panel == null) continue;
                if (panel.getMessageType() == JOptionPane.ERROR_MESSAGE
                        || panel.getMessageType() == JOptionPane.WARNING_MESSAGE) {
                    errorDialogo = String.valueOf(panel.getMessage());
                }
                if (panel.getWantsInput()) {
                    String[] respuestas = {"PRUEBA", "Operador de prueba", "100"};
                    panel.setInputValue(respuestas[Math.min(entrada++, respuestas.length - 1)]);
                }
                panel.setValue(JOptionPane.OK_OPTION);
            }
        });
        SwingUtilities.invokeAndWait(dialogos::start);
        try {
            if (args.length > 0 && "--integrado".equals(args[0])) MainSistemaIntegradoSwing.main(new String[0]);
            else MainVeterinariaSwing.main(new String[0]);
            JFrame principal = esperarVentana("PetCenter - Sistema Veterinario Integrado");
            SwingUtilities.invokeAndWait(() -> {
                comprobar(principal.isShowing(), "Main muestra menú general");
                boton(principal, "1. Módulo 1: Clientes, Mascotas y Citas").doClick();
            });
            JFrame recepcion = esperarVentana("Sistema de Gestión Veterinaria - Recepción");
            SwingUtilities.invokeAndWait(() -> {
                PanelRegistroClienteSwing clientes = buscar(recepcion, PanelRegistroClienteSwing.class);
                mostrar(clientes);
                llenar(clientes, "12345678", "Cliente", "Integrado", "999999999", "Lima");
                boton(clientes, "Registrar Cliente").doClick();
                PanelRegistroPerroSwing perros = buscar(recepcion, PanelRegistroPerroSwing.class);
                mostrar(perros);
                llenar(perros, "P-UI", "Luna integrada", "3", "Mestiza", "10.5");
                boton(perros, "Registrar Perro").doClick();
                PanelAgendarCitaSwing citas = buscar(recepcion, PanelAgendarCitaSwing.class);
                mostrar(citas);
                llenar(citas, "C-UI", "12345678", "P-UI", LocalDateTime.now().plusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                boton(citas, "Agendar Cita").doClick();
                JTable pendientes = buscar(buscar(recepcion, PanelCitasPendientesSwing.class), JTable.class);
                comprobar(pendientes.getRowCount() == 1, "Registrar mascota y cita mediante los formularios del Módulo 1");
                boton(recepcion, "Volver al Menú Principal").doClick();
            });
            esperarVentana("PetCenter - Sistema Veterinario Integrado");
            SwingUtilities.invokeAndWait(() -> boton(principal, "2. Módulo 2: Farmacia, Inventarios y Atención Médica").doClick());
            JFrame farmacia = esperarVentana("PetCenter - Farmacia y atención médica");
            SwingUtilities.invokeAndWait(() -> {
                PanelMedicoView medico = buscar(farmacia, PanelMedicoView.class);
                mostrar(medico);
                comprobar(buscar(medico, JTextArea.class).getText().contains("P-UI | Luna integrada"), "Mascota de recepción visible en Farmacia");
                JTable citas = buscar(medico, JTable.class);
                comprobar(citas.getRowCount() == 1 && citas.getValueAt(0, 0).equals("C-UI"), "Cita de recepción visible en Farmacia");
                citas.setRowSelectionInterval(0, 0);
                ArrayList<JTextField> campos = todos(medico, JTextField.class);
                comprobar(campos.get(1).getText().equals("P-UI"), "Selección muestra mascota correspondiente");
                campos.get(2).setText("Control general");
                campos.get(3).setText("Paciente estable");
                campos.get(4).setText("Reposo e hidratación");
                campos.get(5).setText("Dra. Ana");
                boton(medico, "Guardar atención médica").doClick();
                comprobar(citas.getRowCount() == 0, "Atención completada sale de pendientes");
                buscar(medico, JCheckBox.class).doClick();
                comprobar(citas.getValueAt(0, 6) == EstadoCita.ATENDIDA, "Farmacia muestra ATENDIDA");
                VistaHistoriaClinicaView historia = buscar(farmacia, VistaHistoriaClinicaView.class);
                mostrar(historia);
                buscar(historia, JTextField.class).setText("P-UI");
                boton(historia, "Consultar historia").doClick();
                comprobar(buscar(historia, JTextArea.class).getText().contains("Paciente estable"), "Historia conserva atención desde el menú real");
                boton(farmacia, "Volver al Menú Principal").doClick();
            });
            esperarVentana("PetCenter - Sistema Veterinario Integrado");
            SwingUtilities.invokeAndWait(() -> boton(principal, "1. Módulo 1: Clientes, Mascotas y Citas").doClick());
            JFrame regreso = esperarVentana("Sistema de Gestión Veterinaria - Recepción");
            SwingUtilities.invokeAndWait(() -> {
                PanelTodasLasCitasSwing todas = buscar(regreso, PanelTodasLasCitasSwing.class);
                mostrar(todas);
                JTable tabla = buscar(todas, JTable.class);
                comprobar(tabla.getRowCount() == 1 && tabla.getValueAt(0, 5) == EstadoCita.ATENDIDA,
                        "Regreso a recepción conserva la cita ATENDIDA");
                comprobar(buscar(buscar(regreso, PanelCitasPendientesSwing.class), JTable.class).getRowCount() == 0,
                        "Recepción ya no muestra la cita entre pendientes");
                boton(regreso, "Volver al Menú Principal").doClick();
            });
            esperarVentana("PetCenter - Sistema Veterinario Integrado");
            SwingUtilities.invokeAndWait(() -> boton(principal, "3. Módulo 3: Facturación, Caja y Reportes").doClick());
            JFrame caja = esperarVentana("Módulo de Facturación y Caja - Cajero: Operador de prueba");
            SwingUtilities.invokeAndWait(() -> {
                FormularioCobroView cobro = buscar(caja, FormularioCobroView.class);
                cobro.txtDni.setText("12345678");
                cobro.txtIdTx.setText("TX-INTEGRACION");
                cobro.txtConcepto.setText("Consulta veterinaria");
                cobro.txtMonto.setText("25");
                cobro.btnCobrar.doClick();
                comprobar(buscar(caja, JTextArea.class).getText().contains("Cliente Integrado"), "Caja cobra al cliente registrado en recepción");
                boton(caja, "Generar Reporte").doClick();
                comprobar(buscar(caja, JTextArea.class).getText().contains("125"), "Caja mantiene saldo inicial más cobro");
                boton(caja, "Volver al Menú Principal").doClick();
            });
            esperarVentana("PetCenter - Sistema Veterinario Integrado");
            SwingUtilities.invokeAndWait(() -> boton(principal, "2. Módulo 2: Farmacia, Inventarios y Atención Médica").doClick());
            JFrame reabierta = esperarVentana("PetCenter - Farmacia y atención médica");
            SwingUtilities.invokeAndWait(() -> {
                VistaHistoriaClinicaView historia = buscar(reabierta, VistaHistoriaClinicaView.class);
                mostrar(historia);
                buscar(historia, JTextField.class).setText("P-UI");
                boton(historia, "Consultar historia").doClick();
                comprobar(buscar(historia, JTextArea.class).getText().contains("Paciente estable"), "Reabrir Farmacia conserva historia");
                comprobar(errorDialogo == null, "Ningún diálogo de error: " + errorDialogo);
            });
            System.out.println("CORRECTO: " + comprobaciones + " comprobaciones de navegación Swing desde "
                    + (args.length > 0 ? "MainSistemaIntegradoSwing" : "MainVeterinariaSwing") + ".");
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                dialogos.stop();
                for (Window ventana : Window.getWindows()) ventana.dispose();
            });
        }
    }

    private static JFrame esperarVentana(String titulo) throws Exception {
        long limite = System.currentTimeMillis() + 10000;
        JFrame[] encontrada = new JFrame[1];
        while (encontrada[0] == null && System.currentTimeMillis() < limite) {
            SwingUtilities.invokeAndWait(() -> {
                for (Window ventana : Window.getWindows()) {
                    if (ventana instanceof JFrame && ventana.isShowing() && ((JFrame) ventana).getTitle().equals(titulo)) {
                        encontrada[0] = (JFrame) ventana;
                    }
                }
            });
            if (encontrada[0] == null) Thread.sleep(50);
        }
        if (encontrada[0] == null) throw new AssertionError("No abrió la ventana: " + titulo);
        return encontrada[0];
    }

    private static void mostrar(Component componente) {
        while (componente.getParent() != null) {
            Container padre = componente.getParent();
            if (padre instanceof JTabbedPane) ((JTabbedPane) padre).setSelectedComponent(componente);
            componente = padre;
        }
    }

    private static void llenar(Container panel, String... valores) {
        ArrayList<JTextField> campos = todos(panel, JTextField.class);
        for (int indice = 0; indice < valores.length; indice++) campos.get(indice).setText(valores[indice]);
    }

    private static JButton boton(Container raiz, String texto) {
        for (JButton boton : todos(raiz, JButton.class)) if (boton.getText().equals(texto)) return boton;
        throw new AssertionError("No existe el botón: " + texto);
    }

    private static <T extends Component> T buscar(Container raiz, Class<T> tipo) {
        ArrayList<T> encontrados = todos(raiz, tipo);
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    private static <T extends Component> ArrayList<T> todos(Container raiz, Class<T> tipo) {
        ArrayList<T> encontrados = new ArrayList<>();
        for (Component componente : raiz.getComponents()) {
            if (tipo.isInstance(componente)) encontrados.add(tipo.cast(componente));
            if (componente instanceof Container) encontrados.addAll(todos((Container) componente, tipo));
        }
        return encontrados;
    }
}
