package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Cliente;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class PanelTablaClientesSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaClientes;

    public PanelTablaClientesSwing(VeterinariaFacade facade) {
        this.facade = facade;
        this.modeloTabla = new DefaultTableModel(new Object[]{"DNI", "Nombre", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablaClientes = new JTable(modeloTabla);
        inicializarComponentes();
        cargarClientes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Clientes Registrados"));

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarClientes());
        add(btnActualizar, BorderLayout.SOUTH);
    }

    public void cargarClientes() {
        mostrarListaClientes(facade.listarClientes());
    }

    public void mostrarListaClientes(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);
        if (clientes == null) {
            return;
        }
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{c.getDni(), c.getNombre(), c.getTelefono()});
        }
    }
}