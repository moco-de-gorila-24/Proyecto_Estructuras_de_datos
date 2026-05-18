package Estructuras.Nodos;

import Clases.Alumno;

public class NodoArbolBinarioBusqueda<T> {
    private T dato;
    private NodoArbolBinarioBusqueda izquierdo, derecho;

    public NodoArbolBinarioBusqueda(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoArbolBinarioBusqueda<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbolBinarioBusqueda<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbolBinarioBusqueda<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbolBinarioBusqueda<T> derecho) {
        this.derecho = derecho;
    }
}
