package view.clientes;

import controller.clientes.VeterinariaFacade;
import model.clientes.Mascota;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class PanelTablaMascotasSwing extends JPanel {
    private final VeterinariaFacade facade;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaMascotas;

    public PanelTablaMascotasSwing(VeterinariaFacade facade) {
        this.facade = facade;
        this.modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Raza"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablaMascotas = new JTable(modeloTabla);
        inicializarComponentes();
        cargarMascotas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Mascotas Registradas"));

        add(new JScrollPane(tablaMascotas), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarMascotas());
        add(btnActualizar, BorderLayout.SOUTH);
    }

    public void cargarMascotas() {
        mostrarListaMascotas(facade.listarMascotas());
    }

    public void mostrarListaMascotas(List<Mascota> mascotas) {
        modeloTabla.setRowCount(0);
        if (mascotas == null) {
            return;
        }
        for (Mascota m : mascotas) {
            modeloTabla.addRow(new Object[]{m.getCodigo(), m.getNombre(), m.getRaza()});
        }
    }
}