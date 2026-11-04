package view.farmacia;

import controller.farmacia.ControladorFarmacia;
import javax.swing.*;
import java.awt.*;

public class ControlStockView extends JPanel {
    public ControlStockView(ControladorFarmacia controlador) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField codigo = NotificacionFarmaciaView.agregarCampo(formulario, "Código del medicamento");
        JTextField lote = NotificacionFarmaciaView.agregarCampo(formulario, "Código del lote");
        JTextField fecha = NotificacionFarmaciaView.agregarCampo(formulario, "Vencimiento (AAAA-MM-DD)");
        JTextField cantidad = NotificacionFarmaciaView.agregarCampo(formulario, "Unidades");
        formulario.add(NotificacionFarmaciaView.crearBoton("Registrar lote", () -> {
            controlador.registrarLote(codigo.getText().trim(), lote.getText().trim(), fecha.getText().trim(), cantidad.getText().trim());
            NotificacionFarmaciaView.mostrarExito(this, "Lote registrado.");
        }));
        formulario.add(NotificacionFarmaciaView.crearBoton("Agregar stock al lote", () -> {
            controlador.agregarStock(codigo.getText().trim(), lote.getText().trim(), cantidad.getText().trim());
            NotificacionFarmaciaView.mostrarExito(this, "Stock agregado.");
        }));
        formulario.add(NotificacionFarmaciaView.crearBoton("Descontar stock vigente", () -> {
            controlador.descontarStock(codigo.getText().trim(), cantidad.getText().trim());
            NotificacionFarmaciaView.mostrarExito(this, "Salida registrada.");
        }));
        add(formulario, BorderLayout.NORTH);
        add(new JLabel("La salida elige los lotes vigentes por vencimiento. El lote solo se indica para ingresos."),
                BorderLayout.SOUTH);
    }
}

