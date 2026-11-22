package controller.farmacia;

import model.clientes.*;
import controller.clientes.VeterinariaFacade;
import model.farmacia.*;
import java.util.ArrayList;

public class ControladorAtencion {
    // Las listas locales se usan únicamente en la modalidad independiente.
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Mascota> mascotas;
    private final VeterinariaFacade veterinaria;
    private final AdministradorHistoriales historiales;
    private final PlanificadorInternamiento planificador;
    private final ProcesadorRecetas procesador;
    private final AuditoriaClinica auditoria;

    public ControladorAtencion(ControladorInventario inventario, AuditoriaClinica auditoria) {
        this(inventario, auditoria, null);
    }

    public ControladorAtencion(ControladorInventario inventario, AuditoriaClinica auditoria,
                              VeterinariaFacade veterinaria) {
        this.veterinaria = veterinaria;
        clientes = veterinaria == null ? new ArrayList<>() : null;
        mascotas = veterinaria == null ? new ArrayList<>() : null;
        this.auditoria = auditoria;
        historiales = new AdministradorHistoriales();
        planificador = new PlanificadorInternamiento(historiales);
        procesador = new ProcesadorRecetas(inventario, historiales);
    }

    public synchronized void registrarCliente(Cliente cliente) {
        validarRegistroLocal();
        if (cliente == null || cliente.getMascotas() == null) {
            throw new IllegalArgumentException("El cliente debe tener una lista de mascotas.");
        }
        for (Mascota mascota : cliente.getMascotas()) registrarMascota(mascota);
        if (!clientes.contains(cliente)) clientes.add(cliente);
    }
    public synchronized void registrarMascota(Mascota mascota) {
        validarRegistroLocal();
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
    private void validarRegistroLocal() {
        if (veterinaria != null) {
            throw new IllegalArgumentException("Registre clientes y mascotas desde el Módulo 1.");
        }
    }

    public synchronized ArrayList<Mascota> listarMascotas() {
        return new ArrayList<>(veterinaria == null ? mascotas : veterinaria.listarMascotas());
    }
    public synchronized Mascota buscarMascota(String id) {
        for (Mascota mascota : listarMascotas()) if (mascota.getIdMascota().equals(id)) return mascota;
        throw new IllegalArgumentException("No existe la mascota con ID " + id + ". Regístrela primero en Clientes.");
    }

    public ArrayList<Cita> listarCitas() {
        return veterinaria == null ? new ArrayList<>() : new ArrayList<>(veterinaria.listarTodasLasCitas());
    }

    public ArrayList<Cita> obtenerCitasPendientes() {
        return veterinaria == null ? new ArrayList<>() : new ArrayList<>(veterinaria.obtenerCitasPendientes());
    }

    private Cita buscarCita(String idCita, Mascota mascota) {
        String id = ProductoFarmacia.validarTexto(idCita);
        for (Cita cita : listarCitas()) {
            if (cita.getIdCita().equals(id)) {
                if (cita.getMascota() != mascota) {
                    throw new IllegalArgumentException("La cita no pertenece a la mascota seleccionada.");
                }
                return cita;
            }
        }
        throw new IllegalArgumentException("No existe la cita " + id + ". Regístrela primero en el Módulo 1.");
    }

    // Recetas y seguimientos pueden vincularse a una cita pendiente o ya atendida.
    // Esta vinculación no cambia el estado de la cita.
    private String validarReferenciaCita(String idCita, Mascota mascota) {
        if (idCita == null || idCita.trim().isEmpty()) return null;
        Cita cita = buscarCita(idCita, mascota);
        if (cita.getEstado() != EstadoCita.PENDIENTE && cita.getEstado() != EstadoCita.ATENDIDA) {
            throw new IllegalArgumentException("No se puede vincular una operación a una cita " + cita.getEstado() + ".");
        }
        return cita.getIdCita();
    }

    public synchronized AtencionMedica registrarAtencion(String idCita, String idMascota, String motivo,
                                                          String diagnostico, String tratamiento, String veterinario) {
        Mascota mascota = buscarMascota(idMascota);
        Cita cita = buscarCita(idCita, mascota);
        synchronized (cita) {
            if (cita.getEstado() != EstadoCita.PENDIENTE) {
                throw new IllegalArgumentException("Solo se pueden atender citas PENDIENTE. Estado actual: " + cita.getEstado());
            }
            AtencionMedica atencion = new AtencionMedica(cita.getIdCita(), mascota, motivo,
                    diagnostico, tratamiento, veterinario);
            HistoriaClinica historia = historiales.obtener(mascota);
            synchronized (historia) {
                historia.agregarAtencion(atencion);
                try {
                    if (!this.veterinaria.marcarCitaComoAtendida(cita.getIdCita())
                            || cita.getEstado() != EstadoCita.ATENDIDA) {
                        throw new IllegalArgumentException("No se pudo actualizar la cita. La atención no fue guardada.");
                    }
                } catch (RuntimeException error) {
                    historia.retirarAtencion(atencion);
                    cita.setEstado(EstadoCita.PENDIENTE);
                    throw new IllegalArgumentException("No se pudo completar la atención: " + error.getMessage(), error);
                }
            }
            auditoria.registrar("Atención médica: cita " + cita.getIdCita() + " / mascota " + idMascota);
            return atencion;
        }
    }
    public HistoriaClinica consultarHistoria(String id) { return historiales.obtener(buscarMascota(id)); }
    public RecetaMedica registrarReceta(String codigo, String id, String medicamento, int cantidad,
                                        String indicaciones, String veterinario)
            throws StockInsuficienteException, MedicamentoVencidoException {
        return registrarReceta(codigo, id, medicamento, cantidad, indicaciones, veterinario, null);
    }
    public RecetaMedica registrarReceta(String codigo, String id, String medicamento, int cantidad,
                                        String indicaciones, String veterinario, String idCita)
            throws StockInsuficienteException, MedicamentoVencidoException {
        Mascota mascota = buscarMascota(id);
        String referencia = validarReferenciaCita(idCita, mascota);
        RecetaMedica receta = procesador.procesar(codigo, mascota, medicamento, cantidad, indicaciones, veterinario, referencia);
        auditoria.registrar("Receta dispensada: " + codigo + " / mascota " + id);
        return receta;
    }
    public void registrarEvolucion(String id, String observacion, String veterinario) {
        registrarEvolucion(id, observacion, veterinario, null);
    }
    public void registrarEvolucion(String id, String observacion, String veterinario, String idCita) {
        Mascota mascota = buscarMascota(id);
        String referencia = validarReferenciaCita(idCita, mascota);
        historiales.obtener(mascota).agregarEvolucion(new EvolucionClinica(observacion, veterinario, referencia));
        auditoria.registrar("Evolución registrada: " + id);
    }
    public Internamiento internar(String id, String motivo) {
        return internar(id, motivo, null);
    }
    public Internamiento internar(String id, String motivo, String idCita) {
        Mascota mascota = buscarMascota(id);
        String referencia = validarReferenciaCita(idCita, mascota);
        Internamiento internamiento = planificador.registrar(mascota, motivo, referencia);
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

