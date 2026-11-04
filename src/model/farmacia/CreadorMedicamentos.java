package model.farmacia;

public class CreadorMedicamentos {
    public Medicamento crear(String tipo, String codigo, String nombre, double precio,
                             String principioActivo, int stockMinimo) {
        MedicamentoFactory fabrica = new MedicamentoFactory(tipo, principioActivo, stockMinimo);
        return fabrica.crearProducto(codigo, nombre, precio);
    }
}

