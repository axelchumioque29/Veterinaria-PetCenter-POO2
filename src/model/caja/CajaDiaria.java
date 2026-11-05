package model.caja;

import java.util.ArrayList;
import java.util.List;


public class CajaDiaria {
    private Cajero cajero;
    private double saldoInicial;
    private double saldoFinal;
    private EstadoCaja estado;
    private List<Transaccion> transacciones;

    public CajaDiaria(Cajero cajero, double saldoInicial) {
        this.cajero = cajero;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoInicial;
        this.estado = EstadoCaja.ABIERTA;
        this.transacciones = new ArrayList<>();
    }

    public void registrarTransaccion(Transaccion t) {
        transacciones.add(t);
        saldoFinal += t.getTotal();
    }

    public void cerrarCaja() { this.estado = EstadoCaja.CERRADA; }
    public double getSaldoInicial() { return saldoInicial; }
    public double getSaldoFinal() { return saldoFinal; }
    public EstadoCaja getEstado() { return estado; }
}