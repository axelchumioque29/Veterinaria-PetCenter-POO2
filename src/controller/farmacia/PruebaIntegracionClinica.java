package controller.farmacia;

import controller.clientes.*;
import model.clientes.*;
import model.farmacia.*;
import view.clientes.PanelTodasLasCitasSwing;
import view.farmacia.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PruebaIntegracionClinica {
    private static int comprobaciones;

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        comprobaciones++;
    }

    private static void rechazar(Runnable accion, String mensaje) {
        try {
            accion.run();
            throw new AssertionError(mensaje);
        } catch (IllegalArgumentException esperada) {
            comprobar(esperada.getMessage() != null && !esperada.getMessage().isEmpty(), mensaje);
        }
    }

    private static VeterinariaFacade crearVeterinaria() {
        return new VeterinariaFacade(new ControladorCliente(new RepositorioGenerico<>()),
                new ControladorMascota(new RepositorioGenerico<>()), new ControladorCita(new RepositorioGenerico<>()));
    }

    private static Cita agendar(VeterinariaFacade veterinaria, String id, int dia) throws Exception {
        veterinaria.agendarCita(id, "12345678", "P001", LocalDateTime.now().plusDays(dia), new AtencionGeneral());
        return veterinaria.listarTodasLasCitas().get(veterinaria.listarTodasLasCitas().size() - 1);
    }

    private static void preparar(VeterinariaFacade veterinaria) throws Exception {
        veterinaria.registrarCliente(new Cliente("12345678", "Cliente real", "999999999", "Lima"));
        veterinaria.registrarMascota(new Perro("P001", "Luna", 3, "Mestiza", 10.5, "Medio"));
    }

    public static void main(String[] args) throws Exception {
        VeterinariaFacade veterinaria = crearVeterinaria();
        ClinicaFacade clinica = new ClinicaFacade(veterinaria);
        comprobar(clinica.getAtencion().listarMascotas().isEmpty(), "Sin mascotas de demostración en modo integrado");
        preparar(veterinaria);
        Mascota mascota = veterinaria.listarMascotas().get(0);
        comprobar(clinica.getAtencion().buscarMascota("P001") == mascota, "Misma mascota registrada después de crear Farmacia");
        veterinaria.registrarMascota(new Gato("G001", "Michi", 2, "Mestiza", 4, false));
        comprobar(clinica.getAtencion().listarMascotas().size() == 2, "Nuevas mascotas visibles sin sincronizar copias");
        try {
            veterinaria.registrarMascota(new Perro("P001", "Duplicado", 2, "Mestiza", 4, "Bajo"));
            throw new AssertionError("Código duplicado permitido");
        } catch (Exception esperada) {
            comprobar(veterinaria.listarMascotas().size() == 2, "Módulo 1 rechaza códigos duplicados");
        }
        rechazar(() -> clinica.getAtencion().registrarMascota(mascota), "No crear un registro paralelo en Farmacia");
        clinica.getAtencion().listarMascotas().clear();
        comprobar(veterinaria.listarMascotas().size() == 2, "Listar no expone la lista del repositorio");

        Cita cita = agendar(veterinaria, "C001", 1);
        comprobar(clinica.listarCitas().get(0) == cita, "Misma cita del Módulo 1");
        comprobar(clinica.obtenerCitasPendientes().size() == 1, "Consultar pendientes");
        HistoriaClinica historia = clinica.consultarHistoria("P001");
        comprobar(historia.getMascota() == mascota && historia == clinica.consultarHistoria("P001"), "Historia única con mascota real");
        comprobar(cita.getEstado() == EstadoCita.PENDIENTE, "Consultar historia no atiende la cita");
        rechazar(() -> clinica.registrarAtencion("C001", "NO-EXISTE", "M", "D", "T", "V"), "Mascota inexistente");
        rechazar(() -> clinica.registrarAtencion("NO-EXISTE", "P001", "M", "D", "T", "V"), "Cita inexistente");
        rechazar(() -> clinica.registrarAtencion("C001", "G001", "M", "D", "T", "V"), "Mascota de otra cita");
        rechazar(() -> clinica.registrarAtencion("C001", "P001", "M", " ", "T", "V"), "Diagnóstico obligatorio");
        comprobar(historia.getAtenciones().isEmpty() && cita.getEstado() == EstadoCita.PENDIENTE,
                "Validaciones fallidas no alteran historia ni cita");
        AtencionMedica atencion = clinica.registrarAtencion("C001", "P001", "Control", "Estable", "Reposo", "Dra. Ana");
        comprobar(atencion.getMascota() == mascota && atencion.getIdCita().equals("C001")
                && atencion.getFecha() != null && atencion.getDiagnostico().equals("Estable"), "Datos de atención");
        comprobar(historia.getAtenciones().size() == 1 && historia.getAtenciones().get(0) == atencion, "Atención conservada en historia");
        comprobar(veterinaria.listarTodasLasCitas().get(0).getEstado() == EstadoCita.ATENDIDA
                && clinica.listarCitas().get(0).getEstado() == EstadoCita.ATENDIDA, "Estado compartido en ambos módulos");
        comprobar(veterinaria.obtenerCitasPendientes().isEmpty() && clinica.obtenerCitasPendientes().isEmpty(), "Atendida sale de pendientes");
        rechazar(() -> clinica.registrarAtencion("C001", "P001", "M", "D", "T", "V"), "No atender dos veces");
        Cita cancelada = agendar(veterinaria, "C002", 2);
        cancelada.setEstado(EstadoCita.CANCELADA);
        rechazar(() -> clinica.registrarAtencion("C002", "P001", "M", "D", "T", "V"), "No atender cancelada");
        Cita confirmada = agendar(veterinaria, "C003", 3);
        confirmada.setEstado(EstadoCita.CONFIRMADA);
        rechazar(() -> clinica.registrarAtencion("C003", "P001", "M", "D", "T", "V"), "Solo pendientes, no confirmadas");

        clinica.registrarMedicamento("Medicamento", "MED", "Prueba", 5, "Principio", 1);
        clinica.registrarLote("MED", "L1", LocalDate.now().plusMonths(1), 10);
        RecetaMedica receta = clinica.registrarReceta("R1", "P001", "MED", 2, "Dosis", "Ana", "C001");
        Internamiento internamiento = clinica.registrarInternamiento("P001", "Observación", "C001");
        clinica.getAtencion().registrarEvolucion("P001", "Mejora", "Ana", "C001");
        comprobar(receta.getMascota() == mascota && internamiento.getMascota() == mascota, "Operaciones con mascota real");
        comprobar("C001".equals(receta.getIdCita()) && "C001".equals(internamiento.getIdCita())
                && "C001".equals(historia.getEvoluciones().get(0).getIdCita()), "Referencias de cita conservadas");
        comprobar(clinica.getInventario().buscar("MED").getStockDisponible() == 8, "Receta descuenta stock");
        clinica.getAtencion().darAlta("P001");
        comprobar(!internamiento.estaActivo(), "Alta de mascota compartida");
        comprobar(clinica.registrarReceta("R2", "P001", "MED", 1, "Dosis", "Ana").getIdCita() == null,
                "Recetas sin cita compatibles");
        comprobar(clinica.registrarInternamiento("P001", "Observación").getIdCita() == null,
                "Internamientos sin cita compatibles");
        ControladorFarmacia controlador = new ControladorFarmacia(clinica);
        rechazar(() -> controlador.registrarReceta("R3", "P001", "MED", "1", "Dosis", "Ana", "C002"), "Receta de cita cancelada");
        rechazar(() -> controlador.registrarReceta("R3", "G001", "MED", "1", "Dosis", "Ana", "C001"), "Receta de otra mascota");
        comprobar(clinica.getInventario().buscar("MED").getStockDisponible() == 7, "Vínculo inválido no descuenta stock");

        Cita pendiente = agendar(veterinaria, "C004", 4);
        clinica.registrarReceta("R4", "P001", "MED", 1, "Dosis", "Ana", "C004");
        comprobar(pendiente.getEstado() == EstadoCita.PENDIENTE, "Recetar no marca la cita atendida");
        SwingUtilities.invokeAndWait(() -> probarPanel(clinica, veterinaria));
        for (int modo = 0; modo < 3; modo++) probarFalloDeEstado(modo);
        System.out.println("CORRECTO: " + comprobaciones + " comprobaciones de integración clínica.");
    }

    private static void probarPanel(ClinicaFacade clinica, VeterinariaFacade veterinaria) {
        PanelMedicoView panel = new PanelMedicoView(clinica);
        JTable tabla = buscar(panel, JTable.class);
        comprobar(tabla.getRowCount() == 1 && tabla.getValueAt(0, 0).equals("C004"), "Vista muestra pendientes reales");
        comprobar(clinica.obtenerCitasPendientes().size() == 1, "Abrir panel no cambia estado");
        tabla.setRowSelectionInterval(0, 0);
        JPanel inferior = (JPanel) ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        JPanel formulario = (JPanel) ((BorderLayout) inferior.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        comprobar(((JTextField) formulario.getComponent(3)).getText().equals("P001"), "Seleccionar cita muestra mascota");
        for (int indice : new int[]{5, 7, 9, 11}) ((JTextField) formulario.getComponent(indice)).setText("Prueba de atención");
        ((JButton) formulario.getComponent(12)).doClick();
        comprobar(tabla.getRowCount() == 0 && clinica.consultarHistoria("P001").getAtenciones().size() == 2,
                "Botón guarda atención y actualiza pendientes");
        buscar(panel, JCheckBox.class).doClick();
        comprobar(tabla.getRowCount() == 4 && tabla.getValueAt(3, 6) == EstadoCita.ATENDIDA, "Historial general de citas conserva atendidas");
        JTable tablaAxel = buscar(new PanelTodasLasCitasSwing(veterinaria), JTable.class);
        comprobar(tablaAxel.getValueAt(3, 5) == EstadoCita.ATENDIDA, "Vista del Módulo 1 refleja atención");
        VistaHistoriaClinicaView vista = new VistaHistoriaClinicaView(clinica);
        JPanel consulta = (JPanel) vista.getComponent(0);
        ((JTextField) consulta.getComponent(1)).setText("P001");
        ((JButton) consulta.getComponent(2)).doClick();
        String texto = buscar(vista, JTextArea.class).getText();
        comprobar(texto.contains("Estable") && texto.contains("C004") && texto.contains("R1")
                && texto.contains("Mejora") && texto.contains("Alta:"), "Historia muestra atención y operaciones previas");
    }

    private static void probarFalloDeEstado(int modo) throws Exception {
        VeterinariaFacade falla = new VeterinariaFacade(new ControladorCliente(new RepositorioGenerico<>()),
                new ControladorMascota(new RepositorioGenerico<>()), new ControladorCita(new RepositorioGenerico<>())) {
            @Override
            public boolean marcarCitaComoAtendida(String id) {
                if (modo == 0) return false;
                if (modo == 1) return true; // Informa éxito sin modificar el estado.
                super.marcarCitaComoAtendida(id);
                throw new IllegalStateException("Fallo simulado después de actualizar");
            }
        };
        preparar(falla);
        Cita cita = agendar(falla, "FALLO", 1);
        ClinicaFacade clinica = new ClinicaFacade(falla);
        rechazar(() -> clinica.registrarAtencion("FALLO", "P001", "M", "D", "T", "V"), "Fallo de actualización controlado");
        comprobar(cita.getEstado() == EstadoCita.PENDIENTE && clinica.consultarHistoria("P001").getAtenciones().isEmpty(),
                "Fallo de actualización revierte historia y estado");
    }

    private static <T extends Component> T buscar(Container raiz, Class<T> tipo) {
        for (Component componente : raiz.getComponents()) {
            if (tipo.isInstance(componente)) return tipo.cast(componente);
            if (componente instanceof Container) {
                T encontrado = buscar((Container) componente, tipo);
                if (encontrado != null) return encontrado;
            }
        }
        return null;
    }
}
