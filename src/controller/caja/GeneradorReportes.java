package controller.caja;

import model.caja.CajaDiaria;


public class GeneradorReportes {
    public void generarReporteDiarioAsincrono(CajaDiaria caja) {
        // Uso de Hilos (Threads) como pide el proyecto
        new Thread(() -> {
            System.out.println("Calculando estadísticas financieras en segundo plano...");
            System.out.println("Total transacciones: " + caja.getEstado());
        }).start();
    }
}