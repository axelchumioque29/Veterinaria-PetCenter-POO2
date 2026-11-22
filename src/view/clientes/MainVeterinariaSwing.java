package view.clientes;

import controller.clientes.ControladorCita;
import controller.clientes.ControladorCliente;
import controller.clientes.ControladorMascota;
import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.clientes.Mascota;
import model.clientes.Recepcionista;
import model.clientes.RepositorioGenerico;

import javax.swing.SwingUtilities;

public class MainVeterinariaSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RepositorioGenerico<Cliente> repoClientes = new RepositorioGenerico<>();
            RepositorioGenerico<Mascota> repoMascotas = new RepositorioGenerico<>();
            RepositorioGenerico<Cita> repoCitas = new RepositorioGenerico<>();

            ControladorCliente controladorCliente = new ControladorCliente(repoClientes);
            ControladorMascota controladorMascota = new ControladorMascota(repoMascotas);
            ControladorCita controladorCita = new ControladorCita(repoCitas);

            VeterinariaFacade facade = new VeterinariaFacade(
                    controladorCliente,
                    controladorMascota,
                    controladorCita
            );

            Recepcionista recepcionistaTurno = new Recepcionista(
                    "74125896",
                    "Valeria Rojas",
                    "987654321",
                    "REC-01"
            );

            VentanaRecepcionSwing ventana = new VentanaRecepcionSwing(facade, recepcionistaTurno);
            ventana.setVisible(true);
        });
    }
}