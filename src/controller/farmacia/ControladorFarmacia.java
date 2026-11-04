package controller.farmacia;

import model.farmacia.*;
import java.time.LocalDate;

public class ControladorFarmacia {
    private final ClinicaFacade clinica;
    public ControladorFarmacia(ClinicaFacade clinica) { this.clinica = clinica; }
    public ClinicaFacade getClinica() { return clinica; }

    public void registrar(String tipo, String codigo, String nombre, String precio, String principio, String minimo) {
        clinica.registrarMedicamento(tipo, codigo, nombre, Double.parseDouble(precio), principio, Integer.parseInt(minimo));
    }
    public void registrarLote(String codigo, String lote, String fecha, String cantidad) {
        clinica.registrarLote(codigo, lote, LocalDate.parse(fecha), Integer.parseInt(cantidad));
    }
    public void agregarStock(String codigo, String lote, String cantidad) {
        try {
            clinica.getInventario().agregarStock(codigo, lote, Integer.parseInt(cantidad));
        } catch (MedicamentoVencidoException excepcion) {
            throw new IllegalArgumentException(excepcion.getMessage(), excepcion);
        }
    }
    public void descontarStock(String codigo, String cantidad) {
        try {
            clinica.getInventario().descontarStock(codigo, Integer.parseInt(cantidad));
        } catch (StockInsuficienteException | MedicamentoVencidoException excepcion) {
            throw new IllegalArgumentException(excepcion.getMessage(), excepcion);
        }
    }
    public void registrarReceta(String codigo, String mascota, String medicamento, String cantidad,
                                String indicaciones, String veterinario) {
        try {
            clinica.registrarReceta(codigo, mascota, medicamento, Integer.parseInt(cantidad), indicaciones, veterinario);
        } catch (StockInsuficienteException | MedicamentoVencidoException excepcion) {
            throw new IllegalArgumentException(excepcion.getMessage(), excepcion);
        }
    }
}

