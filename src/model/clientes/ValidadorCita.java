package model.clientes;

import java.util.List;

public class ValidadorCita {
    public static boolean validarCruceHorario(List<Cita> citasExistentes, Cita nuevaCita)throws CitaCruzadaException{
        for(Cita c : citasExistentes){
            if(c.getFechaHora().equals(nuevaCita.getFechaHora()) && c.getEstado()!=EstadoCita.CANCELADA){
                throw new CitaCruzadaException("Error: Ya existe una cita programada para el horario: "+nuevaCita.getFechaHora());
            }
        }
        return true;
    }
}
