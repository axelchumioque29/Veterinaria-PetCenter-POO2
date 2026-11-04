package controller.caja;

public class CoordinadorFinanciero {
    public boolean validarCuadreCaja(double ingresosCalculados, double efectivoFisicoDeclarado) {
        double diferencia = ingresosCalculados - efectivoFisicoDeclarado;
        if (Math.abs(diferencia) < 0.01) {
            System.out.println("La caja cuadra perfectamente.");
            return true;
        } else {
            System.err.println("error, descuadre de caja. Diferencia: S/ " + diferencia);
            return false;
        }
    }
}
