package model.caja;

import java.util.ArrayList;
import java.util.List;

public class DetalleComprobante {
    private List<ItemVenta> items = new ArrayList<>();
    public void agregarItem(ItemVenta item) { items.add(item); }
    public double calcularTotal() {
        return items.stream().mapToDouble(ItemVenta::getSubtotal).sum();
    }
}