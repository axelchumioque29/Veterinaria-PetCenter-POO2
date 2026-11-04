package model.caja;

import model.farmacia.ProductoFarmacia;

public class ConceptoCobro {
    private String id;
    private String descripcion;
    private double precioUnitario;


    public ConceptoCobro(ProductoFarmacia producto) {
        this.id = producto.getCodigo();
        this.descripcion = producto.getNombre();
        this.precioUnitario = producto.getPrecio();
    }


    public ConceptoCobro(String id, String descripcion, double precioUnitario) {
        this.id = id;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
    }

    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioUnitario() { return precioUnitario; }
}