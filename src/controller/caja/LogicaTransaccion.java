package controller.caja;
import model.caja.Transaccion;

public class LogicaTransaccion {
    public boolean auditarTransaccion(Transaccion transaccion) {
        return transaccion != null && transaccion.getTotal() > 0;
    }
}
