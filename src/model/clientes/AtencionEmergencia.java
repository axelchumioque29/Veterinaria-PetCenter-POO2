package model.clientes;

public class AtencionEmergencia implements IEstrategiaAtencion {
    @Override
    public int calcularMinutosEstimados() { return 45; }
    @Override
    public String obtenerPrioridad() { return "ALTA (Urgencia)"; }
}