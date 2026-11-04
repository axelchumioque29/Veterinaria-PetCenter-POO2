package model.farmacia;

public class Vacuna extends Medicamento {
    public Vacuna(String codigo, String nombre, double precio, String principioActivo, int stockMinimo) {
        super(codigo, nombre, precio, principioActivo, stockMinimo);
    }

    @Override
    public String getTipo() { return "Vacuna"; }
}

