package controller.caja;

import model.caja.Transaccion;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroIngresosStream {
    public List<Transaccion> filtrarPorMontoMayorA(List<Transaccion> transacciones, double montoMinimo) {
        return transacciones.stream()
                .filter(t -> t.getTotal() >= montoMinimo)
                .collect(Collectors.toList());
    }
}