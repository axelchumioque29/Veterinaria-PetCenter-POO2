package controller.farmacia;

import model.farmacia.*;
import view.farmacia.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PruebaModuloClinico {
    private static int comprobaciones;

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        comprobaciones++;
    }

    public static void main(String[] argumentos) throws Exception {
        ClinicaFacade clinica = MainModulo2.crearDemostracion();
        ControladorInventario inventario = clinica.getInventario();
        Medicamento medicamento = inventario.buscar("MED001");
        comprobar(clinica.listarMedicamentos().size() == 2, "Registro de medicamentos");
        comprobar(medicamento.getStockDisponible() == 20, "Excluir unidades vencidas");
        comprobar(inventario.revisarVencimientos().size() == 1, "Detectar vencimientos");
        comprobar(inventario.revisarStock().size() == 1, "Detectar stock bajo");
        inventario.agregarStock("MED001", "LOTE001", 5);
        comprobar(medicamento.getStockDisponible() == 25, "Agregar stock");
        inventario.registrarLote("MED001", "PROXIMO", LocalDate.now(), 2);
        inventario.descontarStock("MED001", 3);
        comprobar(medicamento.getStockDisponible() == 24, "Descontar stock");
        for (LoteMedicamento lote : medicamento.getLotes()) {
            if (lote.getCodigo().equals("PROXIMO")) comprobar(lote.getCantidad() == 0, "Priorizar vencimiento");
        }
        try {
            inventario.descontarStock("MED001", 100);
            throw new AssertionError("Se permitió stock insuficiente");
        } catch (StockInsuficienteException esperada) {
            comprobar(medicamento.getStockDisponible() == 24, "Salida fallida sin cambios");
        }
        try {
            inventario.agregarStock("MED001", "LOTE-VENCIDO", 2);
            throw new AssertionError("Se permitió reponer un lote vencido");
        } catch (MedicamentoVencidoException esperada) { comprobaciones++; }
        try {
            inventario.descontarStock("MED001", -1);
            throw new AssertionError("Se aceptó cantidad negativa");
        } catch (IllegalArgumentException esperada) { comprobaciones++; }
        try {
            clinica.registrarMedicamento("Medicamento", "MED001", "Repetido", 1, "Prueba", 0);
            throw new AssertionError("Se aceptó medicamento duplicado");
        } catch (IllegalArgumentException esperada) { comprobaciones++; }
        try {
            inventario.registrarLote("MED001", "LOTE001", LocalDate.now(), 1);
            throw new AssertionError("Se aceptó lote duplicado");
        } catch (IllegalArgumentException esperada) { comprobaciones++; }
        try {
            clinica.registrarMedicamento("Medicamento", "INVALIDO", "Prueba", Double.NaN, "Prueba", 0);
            throw new AssertionError("Se aceptó precio inválido");
        } catch (IllegalArgumentException esperada) { comprobaciones++; }

        clinica.registrarReceta("R001", "M001", "MED001", 4, "Indicación de prueba", "Veterinario");
        HistoriaClinica historia = clinica.consultarHistoria("M001");
        comprobar(historia.getRecetas().size() == 1 && medicamento.getStockDisponible() == 20, "Dispensar receta");
        try {
            clinica.registrarReceta(" R001 ", "M001", "MED001", 4, "Prueba", "Veterinario");
            throw new AssertionError("Se dispensó una receta duplicada");
        } catch (IllegalArgumentException esperada) {
            comprobar(medicamento.getStockDisponible() == 20, "Receta duplicada no descuenta stock");
        }
        try {
            clinica.registrarReceta("R002", "M001", "MED001", 100, "Prueba", "Veterinario");
            throw new AssertionError("Se dispensó receta sin stock");
        } catch (StockInsuficienteException esperada) {
            comprobar(historia.getRecetas().size() == 1, "Receta fallida no se registra");
        }
        clinica.getAtencion().registrarEvolucion("M001", "Observación de prueba", "Veterinario");
        comprobar(historia.getEvoluciones().size() == 1, "Registrar evolución");
        clinica.registrarInternamiento("M001", "Observación");
        comprobar(clinica.getAtencion().contarInternados() == 1, "Internar mascota");
        try {
            clinica.registrarInternamiento("M001", "Duplicado");
            throw new AssertionError("Se duplicó internamiento activo");
        } catch (IllegalArgumentException esperada) { comprobaciones++; }
        clinica.getAtencion().darAlta("M001");
        comprobar(clinica.getAtencion().contarInternados() == 0, "Dar alta");
        comprobar(historia.getInternamientos().get(0).getFechaAlta() != null, "Conservar alta en historia");

        ArrayList<LoteMedicamento> copia = medicamento.getLotes();
        copia.clear();
        comprobar(!medicamento.getLotes().isEmpty(), "Encapsular lista de lotes");
        LoteMedicamento loteCopia = medicamento.getLotes().get(0);
        int original = loteCopia.getCantidad();
        loteCopia.agregar(10);
        comprobar(medicamento.getLotes().get(0).getCantidad() == original, "Encapsular cantidad de lotes");
        clinica.listarMedicamentos().clear();
        comprobar(inventario.listar().size() == 2, "Encapsular lista de inventario");

        ProductoFactory fabrica = new MedicamentoFactory("Antibiotico", "Prueba", 0);
        comprobar(fabrica.crearProducto("A", "Prueba", 1) instanceof Antibiotico, "Factory Method antibiótico");
        comprobar(new CreadorMedicamentos().crear("Anestesico", "B", "Prueba", 1, "Prueba", 0)
                instanceof Anestesico, "Factory Method anestésico");
        comprobar(inventario.buscar("VAC001") instanceof Vacuna, "Factory Method vacuna");
        FabricaAbstractaInsumos insumos = new CreadorInsumos();
        InsumoQuirurgico insumo = insumos.crearEsteril("I", "Gasa", 2);
        insumo.agregarStock(3);
        insumo.descontarStock(1);
        comprobar(insumo.isEsteril() && insumo.getCantidad() == 2, "Crear y consumir insumo");
        comprobar(!insumos.crearNoEsteril("I2", "Prueba", 1).isEsteril(), "Crear insumo no estéril");

        Medicamento vencido = new Medicamento("V", "Vencido", 1, "Prueba", 0);
        vencido.registrarLote(new LoteMedicamento("V1", LocalDate.now().minusDays(1), 3));
        try {
            vencido.descontarStock(1);
            throw new AssertionError("Se dispensó un medicamento vencido");
        } catch (MedicamentoVencidoException esperada) { comprobaciones++; }

        AtomicInteger exitos = new AtomicInteger();
        AtomicInteger errores = new AtomicInteger();
        Runnable consumir = () -> {
            for (int indice = 0; indice < 20; indice++) {
                try {
                    inventario.descontarStock("MED001", 1);
                    exitos.incrementAndGet();
                } catch (StockInsuficienteException | MedicamentoVencidoException esperada) {
                } catch (RuntimeException inesperada) {
                    errores.incrementAndGet();
                }
            }
        };
        clinica.iniciarMonitoreo();
        try {
            Thread primero = new Thread(consumir);
            Thread segundo = new Thread(consumir);
            primero.start();
            segundo.start();
            primero.join(5000);
            segundo.join(5000);
            comprobar(!primero.isAlive() && !segundo.isAlive(), "Los consumidores terminan sin bloqueo");
            comprobar(exitos.get() == 20 && errores.get() == 0 && medicamento.getStockDisponible() == 0,
                    "Proteger stock concurrente");
            long limite = System.currentTimeMillis() + 4000;
            while (clinica.consultarAlertas().size() < 3 && System.currentTimeMillis() < limite) Thread.sleep(25);
            comprobar(clinica.consultarAlertas().size() == 3, "Thread y Runnable actualizan alertas");
        } finally {
            clinica.detenerMonitoreo();
        }
        long limite = System.currentTimeMillis() + 3000;
        while (clinica.getConcurrencia().estaActivo() && System.currentTimeMillis() < limite) Thread.sleep(10);
        comprobar(!clinica.getConcurrencia().estaActivo(), "Detener hilos");
        comprobar(!clinica.consultarAuditoria().isEmpty(), "Registrar auditoría");

        SwingUtilities.invokeAndWait(() -> {
            ControladorFarmacia controlador = new ControladorFarmacia(clinica);
            JPanel[] paneles = {
                new PanelInventarioView(controlador), new ControlStockView(controlador),
                new DetalleMedicamentoView(clinica), new FormularioRecetaView(controlador),
                new VistaHistoriaClinicaView(clinica), new FormularioInternamientoView(clinica),
                new VistaEvolucionView(clinica), new PanelMedicoView(clinica),
                new AlertaVencimientoView(clinica), new DashboardClinicoView(clinica)
            };
            comprobar(paneles.length == 10, "Construir vistas Swing en el hilo de eventos");
            JPanel formularioDetalle = (JPanel) paneles[2].getComponent(0);
            ((JTextField) formularioDetalle.getComponent(1)).setText("MED001");
            ((JButton) formularioDetalle.getComponent(2)).doClick();
            JTextArea detalle = (JTextArea) ((JScrollPane) paneles[2].getComponent(1)).getViewport().getView();
            comprobar(detalle.getText().contains("LOTE-VENCIDO"), "Botón de detalle conectado al inventario");
            JPanel formularioHistoria = (JPanel) paneles[4].getComponent(0);
            ((JTextField) formularioHistoria.getComponent(1)).setText("M001");
            ((JButton) formularioHistoria.getComponent(2)).doClick();
            JTextArea historiaVisible = (JTextArea) ((JScrollPane) paneles[4].getComponent(1)).getViewport().getView();
            comprobar(historiaVisible.getText().contains("R001") && historiaVisible.getText().contains("Alta:"),
                    "Botón de historia muestra receta e internamiento");
            ControladorFarmacia entrada = new ControladorFarmacia(clinica);
            entrada.registrar("Medicamento", "UI001", "Prueba de formulario", "2.5", "Prueba", "2");
            entrada.registrarLote("UI001", "UI-LOTE", LocalDate.now().plusDays(10).toString(), "5");
            entrada.agregarStock("UI001", "UI-LOTE", "2");
            entrada.descontarStock("UI001", "1");
            entrada.registrarReceta("R-UI", "M001", "UI001", "2", "Indicación de prueba", "Veterinario");
            comprobar(clinica.getInventario().buscar("UI001").getStockDisponible() == 4,
                    "Entradas de formularios conectadas a stock y recetas");
        });
        System.out.println("CORRECTO: " + comprobaciones + " comprobaciones del módulo clínico.");
    }
}

