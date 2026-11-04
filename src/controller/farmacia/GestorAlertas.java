package controller.farmacia;

import java.util.ArrayList;

public class GestorAlertas {
    private ArrayList<String> vencimientos = new ArrayList<>();
    private ArrayList<String> stock = new ArrayList<>();
    public synchronized void actualizarVencimientos(ArrayList<String> mensajes) {
        vencimientos = new ArrayList<>(mensajes);
    }
    public synchronized void actualizarStock(ArrayList<String> mensajes) { stock = new ArrayList<>(mensajes); }
    public synchronized ArrayList<String> listar() {
        ArrayList<String> mensajes = new ArrayList<>(vencimientos);
        mensajes.addAll(stock);
        return mensajes;
    }
}

