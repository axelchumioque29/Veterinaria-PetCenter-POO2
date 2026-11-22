package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PanelBuscarMascotaSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final Consumer<List<Mascota>> onResultadoBusqueda;
    private final JTextField txtFiltro = new JTextField(16);

    public PanelBuscarMascotaSwing(VeterinariaFacade facade, Consumer<List<Mascota>> onResultadoBusqueda) {
        this.facade = facade;
        this.onResultadoBusqueda = onResultadoBusqueda;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));
        setBorder(BorderFactory.createTitledBorder("Buscar Mascota (Código, Nombre o Raza)"));

        add(new JLabel("Filtro:"));
        add(txtFiltro);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodos = new JButton("Mostrar Todas");

        btnBuscar.addActionListener(e -> buscarMascotas());
        btnMostrarTodos.addActionListener(e -> mostrarTodas());

        add(btnBuscar);
        add(btnMostrarTodos);
    }

    public void buscarMascotas() {
        String filtro = txtFiltro.getText().trim().toLowerCase();
        List<Mascota> todas = facade.listarMascotas();
        if (filtro.isEmpty()) {
            if (onResultadoBusqueda != null) {
                onResultadoBusqueda.accept(todas);
            }
            return;
        }

        List<Mascota> filtradas = new ArrayList<>();
        for (Mascota m : todas) {
            boolean coincideCodigo = m.getCodigo() != null && m.getCodigo().toLowerCase().contains(filtro);
            boolean coincideNombre = m.getNombre() != null && m.getNombre().toLowerCase().contains(filtro);
            boolean coincideRaza = m.getRaza() != null && m.getRaza().toLowerCase().contains(filtro);
            if (coincideCodigo || coincideNombre || coincideRaza) {
                filtradas.add(m);
            }
        }

        if (onResultadoBusqueda != null) {
            onResultadoBusqueda.accept(filtradas);
        }
    }

    public void mostrarTodas() {
        txtFiltro.setText("");
        if (onResultadoBusqueda != null) {
            onResultadoBusqueda.accept(facade.listarMascotas());
        }
    }
}