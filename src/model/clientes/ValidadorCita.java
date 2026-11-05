package model.clientes;

import java.time.LocalDateTime;
import java.util.List;

public class ValidadorCita {
    public static void validarCruceHorario(List<Cita> citasExistentes, Cita nuevaCita) throws CitaCruzadaException {
        // Usamos calcularMinutosEstimados() que ya existe en tu estrategia
        int duracionNueva = nuevaCita.getEstrategiaAtencion().calcularMinutosEstimados();
        LocalDateTime inicioNueva = nuevaCita.getFechaHora();
        LocalDateTime finNueva = inicioNueva.plusMinutes(duracionNueva);

        for (Cita c : citasExistentes) {
            if (c.getEstado() == EstadoCita.CANCELADA) {
                continue;
            }

            int duracionExistente = c.getEstrategiaAtencion().calcularMinutosEstimados();
            LocalDateTime inicioExistente = c.getFechaHora();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);

            // Fórmula matemática para detectar solapamiento de rangos de tiempo
            boolean seCruzan = inicioNueva.isBefore(finExistente) && inicioExistente.isBefore(finNueva);

            if (seCruzan) {
                throw new CitaCruzadaException("¡Cruce de horario detectado! La cita se solapa con la cita ID: "
                        + c.getIdCita() + " (Horario ocupado: " + inicioExistente + " a " + finExistente + ")");
            }
        }
    }
}