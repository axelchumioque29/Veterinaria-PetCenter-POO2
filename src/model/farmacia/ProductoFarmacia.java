package model.farmacia;

public abstract class ProductoFarmacia {
    private final String codigo;
    private String nombre;
    private double precio;

    public ProductoFarmacia(String codigo, String nombre, double precio) {
        this.codigo = validarTexto(codigo);
        setNombre(nombre);
        setPrecio(precio);
    }

    public static String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Complete los campos obligatorios.");
        }
        return texto.trim();
    }

    public String getCodigo() { return codigo; }
    public synchronized String getNombre() { return nombre; }
    public synchronized void setNombre(String nombre) { this.nombre = validarTexto(nombre); }
    public synchronized double getPrecio() { return precio; }
    public synchronized void setPrecio(double precio) {
        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio debe ser un número no negativo.");
        }
        this.precio = precio;
    }
    public abstract String getTipo();
    @Override
    public String toString() { return codigo + " - " + getNombre(); }
}

