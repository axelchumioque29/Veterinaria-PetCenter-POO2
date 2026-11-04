package model.farmacia;

public interface FabricaAbstractaInsumos {
    InsumoQuirurgico crearEsteril(String codigo, String nombre, double precio);
    InsumoQuirurgico crearNoEsteril(String codigo, String nombre, double precio);
}

