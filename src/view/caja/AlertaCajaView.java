package view.caja;

public class AlertaCajaView {
    public void mostrarErrorCritico(String mensaje) {
        System.err.println(" error de caja: " + mensaje);
    }

    public void mostrarAdvertencia(String mensaje) {
        System.out.println("advertencia: " + mensaje);
    }
}