package Estructuras;

import Estructuras.Nodos.NodoListaEnlazada;

import java.util.Objects;

public class ListaEnlazadaCircular<T> {
    private NodoListaEnlazada<T> P;
    private NodoListaEnlazada<T> last;
    private int size;

    public void add(T o) {
        NodoListaEnlazada<T> nuevo = new NodoListaEnlazada<>(o);
        if (last == null) {
            P = nuevo;
            nuevo.setDato(nuevo.getDato());
        } else {
            nuevo.setSiguiente(P);
            last.setSiguiente(nuevo);
        }
        last = nuevo;
        size++;
    }

    public void set(T o, int index) {
        if (index < 0 || index > size) {
            System.out.println("Indice fuera del rango");
            return;
        }
        NodoListaEnlazada<T> nuevo = new NodoListaEnlazada<>(o);
        if (index == 0) {
            if (P == null) {
                P = nuevo;
                P.setSiguiente(P);
                last = P;
            } else {
                nuevo.setSiguiente(P);
                P = nuevo;
                last.setSiguiente(P);
            }
        } else {
            NodoListaEnlazada<T> actual = P;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
            if (actual == last) {
                last = nuevo;
            }
        }
        size++;
    }

    public boolean remove(T o) {
        if (P == null) {
            return false;
        }
        if (Objects.equals(P.getDato(), o)) {
            if (P == last) {
                P = null;
                last = null;
            } else {
                P = P.getSiguiente();
                last.setSiguiente(P);
            }
            size--;
            return true;
        }
        NodoListaEnlazada<T> actual = P;
        while (actual.getSiguiente() != P) {
            if (Objects.equals(actual.getSiguiente().getDato(), o)) {
                if (actual.getSiguiente() == last) {
                    last = actual;
                }
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                size--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public int getIndice(T o) {
        NodoListaEnlazada<T> actual = P;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(actual.getDato(), o)) {
                return i;
            }
            actual = actual.getSiguiente();
        }
        return -1;
    }

    public void clear() {
        this.P = null;
        this.last = null;
        this.size = 0;
    }
}
