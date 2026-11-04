package model.caja;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class AgregadorFinanciero<T> {
    public double sumarMapeando(List<T> datos, ToDoubleFunction<T> mapeador) {
        return datos.stream().mapToDouble(mapeador).sum();
    }
}