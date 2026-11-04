package excepciones;
public class PagoRechazadoException extends Exception {
    public PagoRechazadoException(String mensaje) {
        super(mensaje);
    }
}