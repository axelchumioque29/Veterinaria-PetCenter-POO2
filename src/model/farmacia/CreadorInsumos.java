package model.farmacia;

public class CreadorInsumos implements FabricaAbstractaInsumos {
    @Override
    public InsumoQuirurgico crearEsteril(String codigo, String nombre, double precio) {
        return new InsumoFactory(true).crearProducto(codigo, nombre, precio);
    }

    @Override
    public InsumoQuirurgico crearNoEsteril(String codigo, String nombre, double precio) {
        return new InsumoFactory(false).crearProducto(codigo, nombre, precio);
    }
}

