package controller.caja;

import model.caja.CajaDiaria;
import model.caja.Cajero;

public class ControladorCaja {
    private CajaDiaria cajaActual;

    public void iniciarTurno(Cajero cajero, double saldoInicial) {
        this.cajaActual = new CajaDiaria(cajero);
        System.out.println("Turno iniciado con saldo inicial: S/ " + saldoInicial);
    }

    public CajaDiaria getCajaActual() { return cajaActual; }
}
