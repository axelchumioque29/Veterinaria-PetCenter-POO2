package controller.clientes;

import model.clientes.Cita;
import model.clientes.CitaCruzadaException;
import model.clientes.ValidadorCita;
import java.util.List;

public class ProcesadorCitaController {

    public static void validarYProcesar(List<Cita> citasExistentes, Cita nuevaCita) throws CitaCruzadaException {
        ValidadorCita.validarCruceHorario(citasExistentes, nuevaCita);
    }
}