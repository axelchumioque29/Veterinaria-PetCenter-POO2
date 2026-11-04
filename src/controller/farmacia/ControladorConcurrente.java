package controller.farmacia;

public class ControladorConcurrente {
    private final ControladorInventario inventario;
    private final GestorAlertas alertas;
    private Thread vencimientos;
    private Thread stock;

    public ControladorConcurrente(ControladorInventario inventario, GestorAlertas alertas) {
        this.inventario = inventario;
        this.alertas = alertas;
    }

    public synchronized void iniciar() {
        if (estaActivo()) return;
        vencimientos = new VerificadorVencimientoThread(inventario, alertas);
        stock = new Thread(new MonitoreoStockRunnable(inventario, alertas), "Monitor de stock");
        stock.setDaemon(true);
        vencimientos.start();
        stock.start();
    }
    public synchronized void detener() {
        if (vencimientos != null) vencimientos.interrupt();
        if (stock != null) stock.interrupt();
    }
    public synchronized boolean estaActivo() {
        return (vencimientos != null && vencimientos.isAlive()) || (stock != null && stock.isAlive());
    }
}

