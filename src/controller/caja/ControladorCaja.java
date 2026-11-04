package controller.caja;

import model.caja.CajaDiaria;
import model.caja.Cajero;

import model.caja.CajaDiaria;
import model.caja.Cajero;

public class ControladorCaja {
    private CajaDiaria cajaActual;

    public void iniciarTurno(Cajero cajero, double saldoInicial) {
        // AQUÍ ESTABA EL ERROR: Ahora sí le pasamos ambos argumentos a CajaDiaria
        this.cajaActual = new CajaDiaria(cajero, saldoInicial);
        System.out.println("Turno iniciado con saldo inicial: S/ " + saldoInicial);
    }

    public CajaDiaria getCajaActual() {
        return cajaActual;
    }
}