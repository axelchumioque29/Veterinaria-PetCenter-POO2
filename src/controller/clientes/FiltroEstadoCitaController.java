package controller.clientes;

import model.clientes.Cita;
import model.clientes.EstadoCita;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroEstadoCitaController {

    public static List<Cita> filtrarPendientes(List<Cita> todasLasCitas) {
        return todasLasCitas.stream()
                .filter(c -> c.getEstado() == EstadoCita.PENDIENTE)
                .collect(Collectors.toList());
    }
}