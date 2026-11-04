package model.caja; // Tu carpeta

import model.clientes.Cliente; // La carpeta de Axel

public class Boleta extends Comprobante {
    private String dniCliente;
    private String nombreCliente;

    public Boleta(String serie, String numero, DetalleComprobante detalle, Moneda moneda, Cliente cliente) {
        this.serie = serie;
        this.numero = numero;
        this.detalle = detalle;
        this.moneda = moneda;

        // Extrae los datos usando los métodos que Axel heredó de Persona
        this.dniCliente = cliente.getDni();
        this.nombreCliente = cliente.getNombre();
    }

    public String getDatosCliente() {
        return "Cliente: " + nombreCliente + " - DNI: " + dniCliente;
    }
}