package model.farmacia;

public class MedicamentoFactory extends ProductoFactory {
    private final String tipo;
    private final String principioActivo;
    private final int stockMinimo;

    public MedicamentoFactory(String tipo, String principioActivo, int stockMinimo) {
        this.tipo = ProductoFarmacia.validarTexto(tipo);
        this.principioActivo = principioActivo;
        this.stockMinimo = stockMinimo;
    }

    @Override
    public Medicamento crearProducto(String codigo, String nombre, double precio) {
        switch (tipo) {
            case "Vacuna": return new Vacuna(codigo, nombre, precio, principioActivo, stockMinimo);
            case "Antibiotico": return new Antibiotico(codigo, nombre, precio, principioActivo, stockMinimo);
            case "Anestesico": return new Anestesico(codigo, nombre, precio, principioActivo, stockMinimo);
            case "Medicamento": return new Medicamento(codigo, nombre, precio, principioActivo, stockMinimo);
            default: throw new IllegalArgumentException("Tipo de medicamento desconocido.");
        }
    }
}

