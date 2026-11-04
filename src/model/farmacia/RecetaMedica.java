package model.farmacia;

import model.clientes.Mascota;
import java.time.LocalDate;
import java.util.Objects;

public class RecetaMedica {
    private final String codigo;
    private final Mascota mascota;
    private final Medicamento medicamento;
    private final int cantidad;
    private final String indicaciones;
    private final String veterinario;
    private final LocalDate fecha = LocalDate.now();

    public RecetaMedica(String codigo, Mascota mascota, Medicamento medicamento,
                        int cantidad, String indicaciones, String veterinario) {
        this.codigo = ProductoFarmacia.validarTexto(codigo);
        this.mascota = Objects.requireNonNull(mascota, "Seleccione una mascota.");
        this.medicamento = Objects.requireNonNull(medicamento, "Seleccione un medicamento.");
        if (cantidad <= 0) throw new IllegalArgumentException("Ingrese una cantidad positiva.");
        this.cantidad = cantidad;
        this.indicaciones = ProductoFarmacia.validarTexto(indicaciones);
        this.veterinario = ProductoFarmacia.validarTexto(veterinario);
    }

    public String getCodigo() { return codigo; }
    public Mascota getMascota() { return mascota; }
    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public String getVeterinario() { return veterinario; }
    public LocalDate getFecha() { return fecha; }
    @Override
    public String toString() {
        return codigo + " | " + fecha + " | " + medicamento.getNombre() + " x " + cantidad
                + " | " + indicaciones + " | " + veterinario;
    }
}

