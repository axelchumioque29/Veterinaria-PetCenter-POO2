package controller.caja;

public class SincronizadorCaja {
    public void sincronizarConServidor() {
        new Thread(() -> {
            System.out.println("Subiendo respaldo de caja a la nube");
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("Sincronizacion completada.");
        }).start();
    }
}