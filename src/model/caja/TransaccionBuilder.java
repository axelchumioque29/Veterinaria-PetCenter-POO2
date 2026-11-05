package model.caja;

public class TransaccionBuilder {
    private String idTransaccion;
    private Comprobante comprobante;
    private MetodoPago metodoPago;

    public TransaccionBuilder asignarId(String idTransaccion) {
        this.idTransaccion = idTransaccion;
        return this;
    }

    public TransaccionBuilder conComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
        return this;
    }

    public TransaccionBuilder pagadoCon(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public Transaccion build() {
        if (idTransaccion == null || comprobante == null || metodoPago == null) {
            throw new IllegalStateException("Faltan datos obligatorios para construir la transacción.");
        }

        // Retorna la nueva transacción usando los datos acumulados
        return new Transaccion(idTransaccion, comprobante, metodoPago);
    }
}