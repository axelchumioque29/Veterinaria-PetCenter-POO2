package controller.farmacia;

public class MonitoreoStockRunnable implements Runnable {
    private final ControladorInventario inventario;
    private final GestorAlertas alertas;
    public MonitoreoStockRunnable(ControladorInventario inventario, GestorAlertas alertas) {
        this.inventario = inventario;
        this.alertas = alertas;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            alertas.actualizarStock(inventario.revisarStock());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException excepcion) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

