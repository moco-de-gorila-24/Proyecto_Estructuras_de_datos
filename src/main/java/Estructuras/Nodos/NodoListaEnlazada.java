package Estructuras.Nodos;

import Clases.Alumno;

public class NodoListaEnlazada<T> {
    private T dato;
    private NodoListaEnlazada<T> siguiente;

    public NodoListaEnlazada(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoListaEnlazada<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaEnlazada<T> siguiente) {
        this.siguiente = siguiente;
    }
}
