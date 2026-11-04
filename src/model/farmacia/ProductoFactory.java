package model.farmacia;

public abstract class ProductoFactory {
    public abstract ProductoFarmacia crearProducto(String codigo, String nombre, double precio);
}

