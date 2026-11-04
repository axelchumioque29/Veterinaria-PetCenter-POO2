package view.farmacia;

import controller.farmacia.ClinicaFacade;
import model.farmacia.*;
import javax.swing.*;
import java.awt.*;

public class DetalleMedicamentoView extends JPanel {
    public DetalleMedicamentoView(ClinicaFacade clinica) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel();
        JTextField codigo = NotificacionFarmaciaView.agregarCampo(formulario, "Código del medicamento");
        JTextArea detalle = new JTextArea();
        detalle.setEditable(false);
        formulario.add(NotificacionFarmaciaView.crearBoton("Consultar detalle", () -> {
            Medicamento medicamento = clinica.getInventario().buscar(codigo.getText().trim());
            StringBuilder texto = new StringBuilder(medicamento + "\nTipo: " + medicamento.getTipo()
                    + "\nPrincipio activo: " + medicamento.getPrincipioActivo()
                    + "\nPrecio: " + medicamento.getPrecio() + "\nStock vigente: " + medicamento.getStockDisponible() + "\n\n");
            for (LoteMedicamento lote : medicamento.getLotes()) {
                texto.append(lote.getCodigo()).append(" | ").append(lote.getFechaVencimiento())
                        .append(" | unidades: ").append(lote.getCantidad()).append(" | ").append(lote.getEstado()).append("\n");
            }
            detalle.setText(texto.toString());
        }));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(detalle), BorderLayout.CENTER);
    }
}

