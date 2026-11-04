package controller.caja;

import model.caja.CajaDiaria;

public class ControladorCierre {
    public void ejecutarCierreFinDeDia(CajaDiaria caja) {
        caja.cerrarCaja();
        System.out.println("Caja cerrada");
    }
}