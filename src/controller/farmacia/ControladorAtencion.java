package controller.farmacia;

import model.clientes.*;
import model.farmacia.*;
import java.util.ArrayList;

public class ControladorAtencion {
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Mascota> mascotas = new ArrayList<>();
    private final AdministradorHistoriales historiales;
    private final PlanificadorInternamiento planificador;
    private final ProcesadorRecetas procesador;
    private final AuditoriaClinica auditoria;

    public ControladorAtencion(ControladorInventario inventario, AuditoriaClinica auditoria) {
        this.auditoria = auditoria;
        historiales = new AdministradorHistoriales();
        planificador = new PlanificadorInternamiento(historiales);
        procesador = new ProcesadorRecetas(inventario, historiales);
    }

    public synchronized void registrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getMascotas() == null) {
            throw new IllegalArgumentException("El cliente debe tener una lista de mascotas.");
        }
        for (Mascota mascota : cliente.getMascotas()) registrarMascota(mascota);
        if (!clientes.contains(cliente)) clientes.add(cliente);
    }
    public synchronized void registrarMascota(Mascota mascota) {
        if (mascota == null) throw new IllegalArgumentException("Indique la mascota.");
        String id = ProductoFarmacia.validarTexto(mascota.getIdMascota());
        for (Mascota existente : mascotas) {
            if (existente.getIdMascota().equals(id)) {
                if (existente == mascota) return;
                throw new IllegalArgumentException("El identificador de mascota ya existe.");
            }
        }
        historiales.obtener(mascota);
        mascotas.add(mascota);
    }
    public synchronized ArrayList<Mascota> listarMascotas() { return new ArrayList<>(mascotas); }
    public synchronized Mascota buscarMascota(String id) {
        for (Mascota mascota : mascotas) if (mascota.getIdMascota().equals(id)) return mascota;
        throw new IllegalArgumentException("No existe la mascota.");
    }
    public HistoriaClinica consultarHistoria(String id) { return historiales.obtener(buscarMascota(id)); }
    public RecetaMedica registrarReceta(String codigo, String id, String medicamento, int cantidad,
                                        String indicaciones, String veterinario)
            throws StockInsuficienteException, MedicamentoVencidoException {
        RecetaMedica receta = procesador.procesar(codigo, buscarMascota(id), medicamento, cantidad, indicaciones, veterinario);
        auditoria.registrar("Receta dispensada: " + codigo + " / mascota " + id);
        return receta;
    }
    public void registrarEvolucion(String id, String observacion, String veterinario) {
        consultarHistoria(id).agregarEvolucion(new EvolucionClinica(observacion, veterinario));
        auditoria.registrar("Evolución registrada: " + id);
    }
    public Internamiento internar(String id, String motivo) {
        Internamiento internamiento = planificador.registrar(buscarMascota(id), motivo);
        auditoria.registrar("Internamiento: " + id);
        return internamiento;
    }
    public void darAlta(String id) {
        planificador.darAlta(buscarMascota(id));
        auditoria.registrar("Alta: " + id);
    }
    public int contarInternados() { return planificador.contarActivos(); }
    public int contarRecetas() { return procesador.listar().size(); }
}

