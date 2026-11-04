package controller.caja;

import model.caja.MetodoPago;

public class EstrategiaPagoContexto {
    private MetodoPago estrategia;

    public void setEstrategia(MetodoPago estrategia) {
        this.estrategia = estrategia;
    }

    public boolean ejecutarPago(double monto) {
        if (estrategia == null) {
            throw new IllegalStateException("Método de pago no seleccionado");
        }
        return estrategia.procesarPago(monto);
    }
}