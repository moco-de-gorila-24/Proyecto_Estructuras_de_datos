package Estructuras.Nodos;

public class NodoDiccionario <K,V>{
    private final K llave;
    private V valor;

    public NodoDiccionario(K llave, V valor) {
        this.llave = llave;
        this.valor = valor;
    }
    @Override
    public String toString() {
        return llave + ": " + valor;
    }

    public K getLlave() {
        return llave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }
}
