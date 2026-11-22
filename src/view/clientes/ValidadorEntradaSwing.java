package view.clientes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorEntradaSwing {
    private static final DateTimeFormatter FORMATO_ENTRADA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public int parsearEntero(String texto, String nombreCampo) throws Exception {
        if (estaVacio(texto)) {
            throw new Exception("El campo " + nombreCampo + " es obligatorio.");
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new Exception("El campo " + nombreCampo + " debe ser un número entero válido.");
        }
    }

    public double parsearDouble(String texto, String nombreCampo) throws Exception {
        if (estaVacio(texto)) {
            throw new Exception("El campo " + nombreCampo + " es obligatorio.");
        }
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new Exception("El campo " + nombreCampo + " debe ser un número decimal válido.");
        }
    }

    public LocalDateTime parsearFechaHora(String texto) throws Exception {
        if (estaVacio(texto)) {
            throw new Exception("La fecha y hora son obligatorias.");
        }
        try {
            return LocalDateTime.parse(texto.trim(), FORMATO_ENTRADA);
        } catch (DateTimeParseException e) {
            throw new Exception("Formato de fecha y hora incorrecto. Use aaaa-MM-dd HH:mm (Ej: 2026-11-22 10:30).");
        }
    }
}