package controller.caja;

public class ProcesadorDigital {
    public boolean validarCodigoOperacion(String numeroOperacion) {
        System.out.println("Validando operación de billetera digital: " + numeroOperacion);
        return numeroOperacion != null && numeroOperacion.length() > 5;
    }
}