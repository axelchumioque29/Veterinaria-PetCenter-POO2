package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cliente;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PanelBuscarClienteSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final Consumer<List<Cliente>> onResultadoBusqueda;
    private final JTextField txtFiltro = new JTextField(16);

    public PanelBuscarClienteSwing(VeterinariaFacade facade, Consumer<List<Cliente>> onResultadoBusqueda) {
        this.facade = facade;
        this.onResultadoBusqueda = onResultadoBusqueda;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));
        setBorder(BorderFactory.createTitledBorder("Buscar Cliente (DNI o Nombre)"));

        add(new JLabel("Filtro:"));
        add(txtFiltro);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Mostrar Todos");

        btnBuscar.addActionListener(e -> buscarClientes());
        btnLimpiar.addActionListener(e -> mostrarTodos());

        add(btnBuscar);
        add(btnLimpiar);
    }

    public void buscarClientes() {
        String filtro = txtFiltro.getText().trim().toLowerCase();
        List<Cliente> todos = facade.listarClientes();
        if (filtro.isEmpty()) {
            if (onResultadoBusqueda != null) {
                onResultadoBusqueda.accept(todos);
            }
            return;
        }

        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : todos) {
            boolean coincideDni = c.getDni() != null && c.getDni().toLowerCase().contains(filtro);
            boolean coincideNombre = c.getNombre() != null && c.getNombre().toLowerCase().contains(filtro);
            if (coincideDni || coincideNombre) {
                filtrados.add(c);
            }
        }

        if (onResultadoBusqueda != null) {
            onResultadoBusqueda.accept(filtrados);
        }
    }

    public void mostrarTodos() {
        txtFiltro.setText("");
        if (onResultadoBusqueda != null) {
            onResultadoBusqueda.accept(facade.listarClientes());
        }
    }
}