package view.caja;

public class PanelCierreCajaView {
    public void mostrarPantallaCierre(double saldoFinalDeclarado) {
        System.out.println("=== PANEL DE CIERRE DE CAJA ===");
        System.out.println("Procesando transacciones del día...");
        System.out.println("Saldo final registrado: S/ " + saldoFinalDeclarado);
    }
}