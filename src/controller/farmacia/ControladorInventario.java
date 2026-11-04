package controller.farmacia;

import model.farmacia.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorInventario {
    private final ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private final ValidadorLotes validador = new ValidadorLotes();
    private final AuditoriaClinica auditoria;

    public ControladorInventario(AuditoriaClinica auditoria) { this.auditoria = auditoria; }

    public synchronized void registrar(Medicamento medicamento) {
        if (medicamento == null) throw new IllegalArgumentException("Indique el medicamento.");
        for (Medicamento existente : medicamentos) {
            if (existente.getCodigo().equals(medicamento.getCodigo())) {
                throw new IllegalArgumentException("El código del medicamento ya existe.");
            }
        }
        medicamentos.add(medicamento);
        auditoria.registrar("Medicamento registrado: " + medicamento);
    }

    public synchronized ArrayList<Medicamento> listar() { return new ArrayList<>(medicamentos); }
    public synchronized Medicamento buscar(String codigo) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getCodigo().equals(codigo)) return medicamento;
        }
        throw new IllegalArgumentException("No existe el medicamento: " + codigo);
    }

    public synchronized void registrarLote(String codigo, String lote, LocalDate fecha, int cantidad) {
        LoteMedicamento nuevo = new LoteMedicamento(lote, fecha, cantidad);
        validador.validarRegistro(nuevo);
        buscar(codigo).registrarLote(nuevo);
        auditoria.registrar("Lote registrado: " + codigo + " / " + lote);
    }

    public synchronized void agregarStock(String codigo, String lote, int cantidad)
            throws MedicamentoVencidoException {
        buscar(codigo).agregarStock(lote, cantidad);
        auditoria.registrar("Ingreso de " + cantidad + " unidades: " + codigo);
    }

    public synchronized void descontarStock(String codigo, int cantidad)
            throws StockInsuficienteException, MedicamentoVencidoException {
        buscar(codigo).descontarStock(cantidad);
        auditoria.registrar("Salida de " + cantidad + " unidades: " + codigo);
    }

    public synchronized ArrayList<String> revisarVencimientos() {
        ArrayList<String> alertas = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            for (LoteMedicamento lote : medicamento.getLotes()) {
                if (validador.estaVencido(lote) && lote.getCantidad() > 0) {
                    alertas.add(medicamento.getNombre() + " | lote " + lote.getCodigo()
                            + " vencido: " + lote.getFechaVencimiento());
                }
            }
        }
        return alertas;
    }

    public synchronized ArrayList<String> revisarStock() {
        ArrayList<String> alertas = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.tieneStockBajo()) {
                alertas.add(medicamento.getNombre() + ": stock bajo (" + medicamento.getStockDisponible() + ")");
            }
        }
        return alertas;
    }
}

