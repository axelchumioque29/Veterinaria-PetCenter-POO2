package excepciones;

public class CajaCerradaException extends Exception {
    public CajaCerradaException(String mensaje) {
        super(mensaje);
    }
}