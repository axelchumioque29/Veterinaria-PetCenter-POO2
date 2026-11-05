package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AgendarCitaView {
    private VeterinariaFacade facade;

    public AgendarCitaView(VeterinariaFacade facade) {
        this.facade = facade;
    }

    public void ejecutar() {
        try {
            System.out.println("\n--- AGENDAR CITA ---");
            String idCita = ConsolaUtil.leerTexto("Ingrese ID de la Cita: ");
            String dniCliente = ConsolaUtil.leerTexto("Ingrese DNI del Cliente Propietario: ");
            String codigoMascota = ConsolaUtil.leerTexto("Ingrese Codigo de la Mascota: ");

            String fechaStr = ConsolaUtil.leerTexto("Ingrese Fecha y Hora (Ej: 2026-10-15 10:30): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, formatter);

            System.out.println("Seleccione Tipo de Atencion:");
            System.out.println("1. Consulta General (20 min)");
            System.out.println("2. Emergencia (45 min - Alta Prioridad)");
            int tipo = ConsolaUtil.leerEntero("Elija opcion: ");

            IEstrategiaAtencion estrategia = (tipo == 2) ? new AtencionEmergencia() : new AtencionGeneral();

            facade.agendarCita(idCita, dniCliente, codigoMascota, fechaHora, estrategia);
            System.out.println("¡Cita agendada con exito! Prioridad: " + estrategia.obtenerPrioridad());

        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha y hora incorrecto.");
        } catch (Exception e) {
            System.out.println("Error al agendar cita: " + e.getMessage());
        }
    }
}