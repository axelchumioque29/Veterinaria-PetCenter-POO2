package controller.caja;

import model.caja.*;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.farmacia.ProductoFarmacia;
import java.util.List;

public class ControladorFacturacion {

    public void procesarCobroCompleto(Cita cita, List<ProductoFarmacia> medicamentos, double precioConsulta, String tipoPago) {


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


        Boleta boleta = new Boleta("B001", "00005", detalle, Moneda.PEN, cliente);
        MetodoPago pago = MetodoPagoFactory.crearMetodoPago(tipoPago);

        if (pago.procesarPago(boleta.getTotal())) {
            Transaccion transaccion = new Transaccion("TX-" + System.currentTimeMillis(), boleta, pago);
            System.out.println("Venta  con exito.");
            System.out.println(boleta.getDatosCliente());
            System.out.println("Total cobrado: S/ " + transaccion.getTotal());
        } else {
            System.err.println("El pago fue rechazado.");
        }
    }
}