package model.clientes;

public interface IMascotaBuilder {
    IMascotaBuilder setCodigo(String codigo);
    IMascotaBuilder setNombre(String nombre);
    IMascotaBuilder setEdad(int edad);
    IMascotaBuilder setRaza(String raza);
    IMascotaBuilder setPeso(double peso);
    Mascota buildPerro(String nivelEnergia);
    Mascota buildGato(boolean esArisco);
}