package controller.caja;

import model.caja.*;
import model.clientes.Cliente;
import model.clientes.Cita;
import model.farmacia.ProductoFarmacia;
import java.util.List;

public class ControladorFacturacion {

    public void procesarCobroCompleto(Cita cita, List<ProductoFarmacia> medicamentos, double precioConsulta, String tipoPago) {

        Cliente cliente = cita.getCliente();
        DetalleComprobante detalle = new DetalleComprobante();


        ConceptoCobro conceptoConsulta = new ConceptoCobro("SRV-" + cita.getIdCita(), "Consulta: " + cita.getMotivo(), precioConsulta);
        detalle.agregarItem(new ItemVenta(conceptoConsulta, 1));


        if (medicamentos != null) {
            for (ProductoFarmacia producto : medicamentos) {

                ConceptoCobro conceptoMed = new ConceptoCobro(producto);
                detalle.agregarItem(new ItemVenta(conceptoMed, 1));
            }
        }


        Boleta boleta = new Boleta("B001", "00004", detalle, Moneda.PEN, cliente);
        MetodoPago pago = MetodoPagoFactory.crearMetodoPago(tipoPago);

        if (pago.procesarPago(boleta.getTotal())) {
            Transaccion transaccion = new Transaccion("TX-" + System.currentTimeMillis(), boleta, pago);
            System.out.println("Venta procesada con éxito.");
            System.out.println(boleta.getDatosCliente());
            System.out.println("Total a cobrar: S/ " + transaccion.getTotal());
        }
    }
}