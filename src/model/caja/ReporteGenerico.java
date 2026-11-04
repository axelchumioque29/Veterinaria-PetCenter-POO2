package model.caja;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class ReporteGenerico<T> {
    private FiltroStream<T> filtro = new FiltroStream<>();
    private AgregadorFinanciero<T> agregador = new AgregadorFinanciero<>();

    public void generar(List<T> datos, Predicate<T> condicion, ToDoubleFunction<T> mapeador) {
        new Thread(() -> {
            List<T> filtrados = filtro.filtrar(datos, condicion);
            double total = agregador.sumarMapeando(filtrados, mapeador);
            System.out.println("Reporte generado. Total acumulado: " + total);
        }).start();
    }
}