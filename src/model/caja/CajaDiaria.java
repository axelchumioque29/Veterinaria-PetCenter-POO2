package model.caja;

import java.util.ArrayList;
import java.util.List;

public class CajaDiaria {
    private Cajero cajero;
    private EstadoCaja estado = EstadoCaja.ABIERTA;
    private List<Transaccion> transacciones = new ArrayList<>();

    public CajaDiaria(Cajero cajero) { this.cajero = cajero; }
    public void registrarTransaccion(Transaccion t) { transacciones.add(t); }
    public void cerrarCaja() { this.estado = EstadoCaja.CERRADA; }
    public EstadoCaja getEstado() { return estado; }
}