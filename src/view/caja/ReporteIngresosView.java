package view.caja;

public class ReporteIngresosView {
    public String generarTextoReporte(String cajero, String id, String estado, double inicial, double fin, double recaudado, double igv, double neta) {
        return String.format(
                "REPORTE DE CAJA DIARIA \n" +
                        "Cajero a cargo     : %s (ID: %s)\n" +
                        "Estado de caja     : %s\n" +
                        "Saldo Inicial      : S/ %.2f\n" +
                        "Saldo Final        : S/ %.2f\n" +
                        "------------------------------\n" +
                        "Total Recaudado    : S/ %.2f\n" +
                        "IGV a declarar     : S/ %.2f\n" +
                        "Ganancia Neta Real : S/ %.2f\n",
                cajero, id, estado, inicial, fin, recaudado, igv, neta
        );
    }
}