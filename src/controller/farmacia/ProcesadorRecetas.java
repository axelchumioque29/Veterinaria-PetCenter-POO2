package controller.farmacia;

import model.clientes.Mascota;
import model.farmacia.*;
import java.util.ArrayList;

public class ProcesadorRecetas {
    private final ControladorInventario inventario;
    private final SincronizadorClinico sincronizador;
    private final AdministradorHistoriales historiales;
    private final ArrayList<RecetaMedica> recetas = new ArrayList<>();

    public ProcesadorRecetas(ControladorInventario inventario, AdministradorHistoriales historiales) {
        this.inventario = inventario;
        this.historiales = historiales;
        this.sincronizador = new SincronizadorClinico(historiales);
    }

    public synchronized RecetaMedica procesar(String codigo, Mascota mascota, String codigoMedicamento,
                                              int cantidad, String indicaciones, String veterinario)
            throws StockInsuficienteException, MedicamentoVencidoException {
        codigo = ProductoFarmacia.validarTexto(codigo);
        for (RecetaMedica receta : recetas) {
            if (receta.getCodigo().equals(codigo)) throw new IllegalArgumentException("La receta ya existe.");
        }
        HistoriaClinica historia = historiales.obtener(mascota);
        RecetaMedica receta = new RecetaMedica(codigo, historia.getMascota(), inventario.buscar(codigoMedicamento),
                cantidad, indicaciones, veterinario);
        inventario.descontarStock(codigoMedicamento, cantidad);
        sincronizador.registrarReceta(receta);
        recetas.add(receta);
        return receta;
    }

    public synchronized ArrayList<RecetaMedica> listar() { return new ArrayList<>(recetas); }
}

