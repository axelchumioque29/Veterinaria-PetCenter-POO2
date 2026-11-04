package controller.farmacia;

import model.farmacia.*;

public class ValidadorLotes {
    public void validarRegistro(LoteMedicamento lote) {
        if (lote == null || lote.getCantidad() <= 0) {
            throw new IllegalArgumentException("El lote debe tener unidades.");
        }
    }
    public boolean estaVencido(LoteMedicamento lote) { return lote.estaVencido(); }
}

