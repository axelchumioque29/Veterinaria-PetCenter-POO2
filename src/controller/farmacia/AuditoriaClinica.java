package controller.farmacia;

import model.farmacia.ProductoFarmacia;
import java.util.ArrayList;

public class AuditoriaClinica {
    private final RegistroLogsFarmacia registros = new RegistroLogsFarmacia();
    public void registrar(String accion) { registros.registrar(ProductoFarmacia.validarTexto(accion)); }
    public ArrayList<String> consultar() { return registros.listar(); }
}

