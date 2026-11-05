package view.clientes;

import controller.clientes.ControladorCita;
import controller.clientes.ControladorCliente;
import controller.clientes.ControladorMascota;
import controller.clientes.VeterinariaFacade;
import model.clientes.Cita;
import model.clientes.Cliente;
import model.clientes.Mascota;
import model.clientes.RepositorioGenerico;

public class MainVeterinaria {
    public static void main(String[] args) {
        // Inicializar repositorios genéricos
        RepositorioGenerico<Cliente> repoClientes = new RepositorioGenerico<>();
        RepositorioGenerico<Mascota> repoMascotas = new RepositorioGenerico<>();
        RepositorioGenerico<Cita> repoCitas = new RepositorioGenerico<>();

        // Inicializar controladores base
        ControladorCliente ctrlCliente = new ControladorCliente(repoClientes);
        ControladorMascota ctrlMascota = new ControladorMascota(repoMascotas);
        ControladorCita ctrlCita = new ControladorCita(repoCitas);

        // Ensamblar la Fachada (Patrón Facade)
        VeterinariaFacade facade = new VeterinariaFacade(ctrlCliente, ctrlMascota, ctrlCita);

        // Lanzar el menú principal de la vista por consola
        MenuPrincipalView menuPrincipal = new MenuPrincipalView(facade);
        menuPrincipal.mostrar();
    }
}