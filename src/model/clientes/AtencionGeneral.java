package model.clientes;

public class AtencionGeneral implements IEstrategiaAtencion {
    @Override
    public int calcularMinutosEstimados() { return 20; }
    @Override
    public String obtenerPrioridad() { return "NORMAL"; }
}