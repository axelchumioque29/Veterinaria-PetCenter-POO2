package controller.caja;

package controller.caja;

import model.caja.*;
import model.caja.genericos.IRepository;
import model.caja.genericos.TransaccionRepository;

public class FacturacionFacade {
    private IRepository<Transaccion> repositorio;

    public FacturacionFacade() {
        this.repositorio = new TransaccionRepository();
    }

    // Le agregamos 'CajaDiaria cajaAbierta' para que el dinero entre a la caja del turno
    public void procesarNuevaVenta(String idTransaccion, Comprobante comprobante, String tipoMetodoPago, CajaDiaria cajaAbierta) {

        // 1. Factory crea el método de pago
        MetodoPago metodo = MetodoPagoFactory.crearMetodoPago(tipoMetodoPago);

        // 2. Se procesa el cobro (Patrón Strategy interno)
        if (metodo.procesarPago(comprobante.getTotal())) {

            // 3. Builder construye el objeto complejo
            Transaccion transaccion = new TransaccionBuilder()
                    .asignarId(idTransaccion)
                    .conComprobante(comprobante)
                    .pagadoCon(metodo)
                    .build();

            // 4. Repository lo guarda en el historial global
            repositorio.guardar(transaccion);

            // 5. ACTUALIZAMOS EL DINERO EN LA CAJA (Paso crítico)
            cajaAbierta.registrarTransaccion(transaccion);

            System.out.println("Venta " + idTransaccion + " procesada y guardada con éxito.");
            System.out.println("-> Saldo actual en caja: S/ " + cajaAbierta.getSaldoFinal());
        } else {
            System.err.println("No se pudo procesar el pago de la venta " + idTransaccion);
        }
    }
}