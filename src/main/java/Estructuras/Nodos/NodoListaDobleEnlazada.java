package Estructuras.Nodos;

public class NodoListaDobleEnlazada<T> {
    private T dato;
    private NodoListaDobleEnlazada<T> siguiente;
    private NodoListaDobleEnlazada<T> anterior;

    public NodoListaDobleEnlazada() {
        this.dato = null;
        this.siguiente = null;
        this.anterior = null;
    }

    public NodoListaDobleEnlazada(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoListaDobleEnlazada<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaDobleEnlazada<T> siguiente) {
        this.siguiente = siguiente;
    }

    public NodoListaDobleEnlazada<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoListaDobleEnlazada<T> anterior) {
        this.anterior = anterior;
    }
}