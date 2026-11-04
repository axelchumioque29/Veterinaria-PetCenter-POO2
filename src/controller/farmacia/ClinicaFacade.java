package controller.farmacia;

import model.clientes.Cliente;
import model.farmacia.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClinicaFacade {
    private final AuditoriaClinica auditoria = new AuditoriaClinica();
    private final ControladorInventario inventario = new ControladorInventario(auditoria);
    private final ControladorAtencion atencion = new ControladorAtencion(inventario, auditoria);
    private final GestorAlertas alertas = new GestorAlertas();
    private final ControladorConcurrente concurrencia = new ControladorConcurrente(inventario, alertas);

    public ControladorInventario getInventario() { return inventario; }
    public ControladorAtencion getAtencion() { return atencion; }
    public ControladorConcurrente getConcurrencia() { return concurrencia; }
    public ArrayList<String> consultarAuditoria() { return auditoria.consultar(); }
    public ArrayList<String> consultarAlertas() { return alertas.listar(); }
    public void registrarCliente(Cliente cliente) { atencion.registrarCliente(cliente); }
    public void registrarMedicamento(String tipo, String codigo, String nombre, double precio,
                                     String principioActivo, int minimo) {
        inventario.registrar(new CreadorMedicamentos().crear(tipo, codigo, nombre, precio, principioActivo, minimo));
    }
    public void registrarLote(String codigo, String lote, LocalDate fecha, int cantidad) {
        inventario.registrarLote(codigo, lote, fecha, cantidad);
    }
    public ArrayList<Medicamento> listarMedicamentos() { return inventario.listar(); }
    public RecetaMedica registrarReceta(String codigo, String mascota, String medicamento, int cantidad,
                                        String indicaciones, String veterinario)
            throws StockInsuficienteException, MedicamentoVencidoException {
        return atencion.registrarReceta(codigo, mascota, medicamento, cantidad, indicaciones, veterinario);
    }
    public HistoriaClinica consultarHistoria(String mascota) { return atencion.consultarHistoria(mascota); }
    public Internamiento registrarInternamiento(String mascota, String motivo) { return atencion.internar(mascota, motivo); }
    public void iniciarMonitoreo() { concurrencia.iniciar(); }
    public void detenerMonitoreo() { concurrencia.detener(); }
}

