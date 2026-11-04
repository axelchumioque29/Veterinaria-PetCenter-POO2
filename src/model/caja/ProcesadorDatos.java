package model.caja;

import java.util.List;

public class ProcesadorDatos<T> {
    public void procesar(List<T> datos) {
        datos.forEach(dato -> System.out.println(dato.toString()));
    }
}