package model.caja;

import java.time.LocalDateTime;

public class Transaccion {
    private String idTransaccion;
    private Comprobante comprobante;
    private MetodoPago metodoPago;
    private LocalDateTime fechaHora;

    public Transaccion(String id, Comprobante comp, MetodoPago metodo) {
        this.idTransaccion = id; this.comprobante = comp; this.metodoPago = metodo;
        this.fechaHora = LocalDateTime.now();
    }
    public double getTotal() { return comprobante.getTotal(); }
    public MetodoPago getMetodoPago() { return metodoPago; }
}