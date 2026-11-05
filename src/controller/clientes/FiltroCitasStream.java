package controller.clientes;

import model.clientes.Cita;
import model.clientes.EstadoCita;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroCitasStream {

    // Filtrar citas pendientes usando Stream API
    public List<Cita> obtenerCitasPendientes(List<Cita> todasLasCitas) {
        return todasLasCitas.stream()
                .filter(c -> c.getEstado() == EstadoCita.PENDIENTE)
                .collect(Collectors.toList());
    }

    // Filtrar citas por DNI del cliente usando expresiones Lambda y Streams
    public List<Cita> buscarCitasPorCliente(List<Cita> todasLasCitas, String dni) {
        return todasLasCitas.stream()
                .filter(c -> c.getCliente().getDni().equals(dni))
                .collect(Collectors.toList());
    }
}