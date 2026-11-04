package controller.caja;

public class ValidadorMontos {
    public void validar(double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("Error: El monto a cobrar no puede ser cero o negativo.");
        }
    }
}