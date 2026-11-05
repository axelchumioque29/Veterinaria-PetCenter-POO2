package model.caja;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditoriaFinanciera {
    public static void registrarAccion(String idCajero, String accion) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        System.out.println("auditoria " + fechaHora + " | Cajero: " + idCajero + " | Accion: " + accion);
    }
}