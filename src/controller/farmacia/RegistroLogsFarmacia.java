package controller.farmacia;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegistroLogsFarmacia {
    private final ArrayList<String> mensajes = new ArrayList<>();
    public synchronized void registrar(String mensaje) {
        mensajes.add(LocalDateTime.now() + " | " + mensaje);
    }
    public synchronized ArrayList<String> listar() { return new ArrayList<>(mensajes); }
}

