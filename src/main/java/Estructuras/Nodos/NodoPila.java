package Estructuras.Nodos;

public class NodoPila<T> {
    private T accion;
    private NodoPila<T> siguiente;

    public NodoPila(T accion) {
        this.accion = accion;
        this.siguiente = null;
    }

    public T getDato() {
        return accion;
    }

    public void setDato(T accion) {
        this.accion = accion;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila<T> siguiente) {
        this.siguiente = siguiente;
    }

    public T getAccion() {
        return accion;
    }

    public void setAccion(T accion) {
        this.accion = accion;
    }
}
