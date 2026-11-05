package model.clientes;

public class MascotaBuilder implements IMascotaBuilder {
    private String codigo;
    private String nombre;
    private int edad;
    private String raza;
    private double peso;

    @Override
    public IMascotaBuilder setCodigo(String codigo) { this.codigo = codigo; return this; }
    @Override
    public IMascotaBuilder setNombre(String nombre) { this.nombre = nombre; return this; }
    @Override
    public IMascotaBuilder setEdad(int edad) { this.edad = edad; return this; }
    @Override
    public IMascotaBuilder setRaza(String raza) { this.raza = raza; return this; }
    @Override
    public IMascotaBuilder setPeso(double peso) { this.peso = peso; return this; }

    @Override
    public Mascota buildPerro(String nivelEnergia) {
        return new Perro(codigo, nombre, edad, raza, peso, nivelEnergia);
    }

    @Override
    public Mascota buildGato(boolean esArisco) {
        return new Gato(codigo, nombre, edad, raza, peso, esArisco);
    }
}