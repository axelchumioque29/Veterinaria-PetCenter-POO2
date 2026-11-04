package model.caja;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FiltroStream<T> {
    public List<T> filtrar(List<T> datos, Predicate<T> condicion) {
        return datos.stream().filter(condicion).collect(Collectors.toList());
    }
}