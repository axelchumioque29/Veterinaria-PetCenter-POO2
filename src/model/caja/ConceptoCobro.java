package model.caja;

public class ConceptoCobro {
    private String id;
    private String descripcion;
    private double precioUnitario;

    public ConceptoCobro(String id, ProductoFarmacia producto) {
        this.id = id;
        this.descripcion = producto.getNombre();
        this.precioUnitario = producto.getPrecio();
    }
    public double getPrecioUnitario() { return precioUnitario; }
}