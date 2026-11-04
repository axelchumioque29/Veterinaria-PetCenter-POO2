package model.clientes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RepositorioGenerico<T>{
    private List<T> elementos;

    public RepositorioGenerico() {
        this.elementos = new ArrayList<>();
    }

    public void agregar(T elemento){
        elementos.add(elemento);
    }

    public List<T> listarTodos(){
        return elementos;
    }

    public T buscar (Predicate<T> condicion){
        return elementos.stream()
        .filter(condicion)
        .findFirst()
        .orElse(null);
    }

    public boolean eliminar(T elemento){
        return elementos.remove(elemento);
    }

}
