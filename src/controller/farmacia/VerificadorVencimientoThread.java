package controller.farmacia;

public class VerificadorVencimientoThread extends Thread {
    private final ControladorInventario inventario;
    private final GestorAlertas alertas;

    public VerificadorVencimientoThread(ControladorInventario inventario, GestorAlertas alertas) {
        super("Verificador de vencimientos");
        this.inventario = inventario;
        this.alertas = alertas;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            alertas.actualizarVencimientos(inventario.revisarVencimientos());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException excepcion) {
                interrupt();
            }
        }
    }
}

