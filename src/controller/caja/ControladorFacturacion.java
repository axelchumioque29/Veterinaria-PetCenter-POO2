package controller.caja;

import model.caja.*;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.farmacia.ProductoFarmacia;
import java.util.List;

public class ControladorFacturacion {

    private ManejadorComprobantes manejador = new ManejadorComprobantes();

    // NOTA: Se agregó el parámetro 'CajaDiaria cajaAbierta' al final
    public void procesarCobroCompleto(Cita cita, List<ProductoFarmacia> medicamentos, double precioConsulta, String tipoPago, CajaDiaria cajaAbierta) {

        Cliente cliente = cita.getCliente();
        DetalleComprobante detalle = new DetalleComprobante();

        ConceptoCobro conceptoConsulta = new ConceptoCobro("SRV-" + cita.getIdCita(), "Consulta: " + cita.getMotivo(), precioConsulta);
        detalle.agregarItem(new ItemVenta(conceptoConsulta, 1));

        if (medicamentos != null && !medicamentos.isEmpty()) {
            for (ProductoFarmacia producto : medicamentos) {
                ConceptoCobro conceptoMed = new ConceptoCobro(producto);
                detalle.agregarItem(new ItemVenta(conceptoMed, 1));
            }
        }

        String serie = "B001";
        String numeroCorrelativo = manejador.generarNumeroBoleta();

        Boleta boleta = new Boleta(serie, numeroCorrelativo, detalle, Moneda.PEN, cliente);
        MetodoPago pago = MetodoPagoFactory.crearMetodoPago(tipoPago);

        if (pago.procesarPago(boleta.getTotal())) {
            Transaccion transaccion = new Transaccion("TX-" + System.currentTimeMillis(), boleta, pago);

            // EL PASO CLAVE FALTANTE: Inyectar el dinero a la caja diaria actual
            cajaAbierta.registrarTransaccion(transaccion);

            System.out.println("Venta exitosa. Comprobante generado: " + serie + "-" + numeroCorrelativo);
            System.out.println(boleta.getDatosCliente());
            System.out.println("Total cobrado: S/ " + transaccion.getTotal());
            System.out.println("-> Saldo actual en caja: S/ " + cajaAbierta.getSaldoFinal());
        } else {
            System.err.println("El pago fue rechazado.");
        }
    }
}