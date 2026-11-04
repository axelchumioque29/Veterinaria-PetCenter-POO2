package model.caja;

import model.clientes.Cliente;

public class Factura extends Comprobante {
    private String ruc;
    private String razonSocial;

    public Factura(String serie, String numero, DetalleComprobante detalle, Moneda moneda, Cliente cliente) {
        this.serie = serie;
        this.numero = numero;
        this.detalle = detalle;
        this.moneda = moneda;


        this.ruc = cliente.getDni();
        this.razonSocial = cliente.getNombre();
    }

    public String getDatosFacturacion() {
        return "Razón Social: " + razonSocial + " - RUC: " + ruc;
    }
}