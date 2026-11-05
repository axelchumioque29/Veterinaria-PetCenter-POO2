package controller.caja;

import model.caja.CajaDiaria;
import model.caja.Cajero;
import model.caja.Comprobante;

public class ControladorCaja {
    private CajaDiaria cajaActual;
    private FacturacionFacade facturacionFacade;

    public ControladorCaja() {

        this.facturacionFacade = new FacturacionFacade();
    }

    public void iniciarTurno(Cajero cajero, double saldoInicial) {
        this.cajaActual = new CajaDiaria(cajero, saldoInicial);
        System.out.println("Turno iniciado con saldo inicial: S/ " + saldoInicial);
    }

    public CajaDiaria getCajaActual() {
        return cajaActual;
    }


    public void registrarCobro(String idTransaccion, Comprobante comprobante, String tipoPago) {
        if (cajaActual == null) {
            System.err.println("Error: Debes iniciar turno antes de cobrar.");
            return;
        }

        try {

            facturacionFacade.procesarNuevaVenta(idTransaccion, comprobante, tipoPago, this.cajaActual);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado en el sistema de caja: " + e.getMessage());
        }
    }
}