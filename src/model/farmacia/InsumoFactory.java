package model.farmacia;

public class InsumoFactory extends ProductoFactory {
    private final boolean esteril;

    public InsumoFactory(boolean esteril) { this.esteril = esteril; }

    @Override
    public InsumoQuirurgico crearProducto(String codigo, String nombre, double precio) {
        return new InsumoQuirurgico(codigo, nombre, precio, esteril);
    }
}

