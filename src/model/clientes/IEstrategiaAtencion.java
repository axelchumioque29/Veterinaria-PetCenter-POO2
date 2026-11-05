package model.clientes;

public interface IEstrategiaAtencion {
    int calcularMinutosEstimados();
    String obtenerPrioridad();
}