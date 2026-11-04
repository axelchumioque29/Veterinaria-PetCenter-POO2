package model.farmacia;

public class Anestesico extends Medicamento {
    public Anestesico(String codigo, String nombre, double precio, String principioActivo, int stockMinimo) {
        super(codigo, nombre, precio, principioActivo, stockMinimo);
    }

    @Override
    public String getTipo() { return "Anestésico"; }
}

