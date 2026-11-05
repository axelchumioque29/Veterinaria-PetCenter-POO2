package controller.clientes;

import model.clientes.*;
import java.time.LocalDateTime;
import java.util.List;

public class VeterinariaFacade {
    private ControladorCliente controladorCliente;
    private ControladorMascota controladorMascota;
    private ControladorCita controladorCita;
    private FiltroCitasStream filtroCitasStream;

    public VeterinariaFacade(
            ControladorCliente controladorCliente,
            ControladorMascota controladorMascota,
            ControladorCita controladorCita) {
        this.controladorCliente = controladorCliente;
        this.controladorMascota = controladorMascota;
        this.controladorCita = controladorCita;
        this.filtroCitasStream = new FiltroCitasStream();
    }

    // --- Métodos unificados para el negocio ---

    public void registrarCliente(Cliente cliente) {
        controladorCliente.registrarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return controladorCliente.listarClientes();
    }

    public void registrarMascota(Mascota mascota) {
        controladorMascota.registrarMascota(mascota);
    }

    public List<Mascota> listarMascotas() {
        return controladorMascota.listarMascotas();
    }

    public void agendarCita(String idCita, String dniCliente, String codigoMascota, LocalDateTime fechaHora, IEstrategiaAtencion estrategia) throws Exception {
        Cliente cliente = controladorCliente.buscarClientePorDni(dniCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente con DNI " + dniCliente + " no encontrado en el sistema.");
        }

        Mascota mascota = controladorMascota.buscarMascotaPorCodigo(codigoMascota);
        if (mascota == null) {
            throw new Exception("Mascota con código " + codigoMascota + " no encontrada en el sistema.");
        }

        Cita nuevaCita = new Cita(idCita, cliente, mascota, fechaHora, estrategia);
        controladorCita.agendarCita(nuevaCita);
    }

    public List<Cita> obtenerCitasPendientes() {
        return filtroCitasStream.obtenerCitasPendientes(controladorCita.listarCitas());
    }
}