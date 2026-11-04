package model.farmacia;

public class Antibiotico extends Medicamento {
    public Antibiotico(String codigo, String nombre, double precio, String principioActivo, int stockMinimo) {
        super(codigo, nombre, precio, principioActivo, stockMinimo);
    }

    @Override
    public String getTipo() { return "Antibiótico"; }
}

