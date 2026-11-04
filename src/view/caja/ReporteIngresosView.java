package view.caja;

public class ReporteIngresosView {
    public void imprimirReporte(int cantidadTransacciones, double ingresosTotales) {
        System.out.println("--- REPORTE DE INGRESOS ---");
        System.out.println("Transacciones procesadas: " + cantidadTransacciones);
        System.out.println("Monto total recaudado: S/ " + ingresosTotales);
    }
}