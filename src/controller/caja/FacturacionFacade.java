package controller.caja;

import model.caja.*;

import model.clientes.RepositorioGenerico;

public class FacturacionFacade {


    private RepositorioGenerico<Transaccion> repositorio;

    public FacturacionFacade() {
        this.repositorio = new RepositorioGenerico<>();
    }

    public void procesarNuevaVenta(String idTransaccion, Comprobante comprobante, String tipoMetodoPago, CajaDiaria cajaAbierta) {


        MetodoPago metodo = MetodoPagoFactory.crearMetodoPago(tipoMetodoPago);

        if (metodo.procesarPago(comprobante.getTotal())) {


            Transaccion transaccion = new TransaccionBuilder()
                    .asignarId(idTransaccion)
                    .conComprobante(comprobante)
                    .pagadoCon(metodo)
                    .build();


            repositorio.agregar(transaccion);


            cajaAbierta.registrarTransaccion(transaccion);

            System.out.println("Venta " + idTransaccion + " procesada y guardada en el historial con éxito.");
            System.out.println("-> Saldo actual en caja: S/ " + cajaAbierta.getSaldoFinal());
        } else {
            System.err.println("No se pudo procesar el pago de la venta " + idTransaccion);
        }
    }
}