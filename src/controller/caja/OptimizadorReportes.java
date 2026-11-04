package controller.caja;

public class OptimizadorReportes {
    public void limpiarCache() {
        System.out.println("Limpiando datos de reportes para liberar memoria");
        System.gc(); // Sugiere al recolector de basura actuar
    }
}