package Estructuras;

import Estructuras.Nodos.NodoPila;

public class Pila<T> {
    private NodoPila<T> tope;

    /**
     * mete un elemento a la pila
     * @param dato
     */
    public void push(T dato) {
        NodoPila<T> nuevo = new NodoPila<>(dato);
        nuevo.setSiguiente(tope);
        tope = nuevo;
    }

    /**
     * elimina y regresa el elemento hasta arriba de la pila
     * @return dato de arriba o null si no hay
     */
    public T pop() {
        if (tope == null) return null;
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        return dato;
    }

    /**
     * permite inspeccionar el elemento hasta arriba
     * @return dato del tope, o null si esta vacia
     */
    public T peek() {
        if (tope == null) return null;
        return tope.getDato();
    }

    /**
     * revisa si la pila esta vacia
     * @return true si no hay elementos
     */
    public boolean isEmpty() {
        return tope == null;
    }

    //pro si algo en el codigo ya usaba el otro nombre que no es push ni pop
    public void apilar(T dato) {push(dato);}
    public T desapilar() {return pop();}

    public void mostrar() {
        if (tope == null) {
            System.out.println("El historial esta vacio");
            return;
        }
        NodoPila<T> actual = tope;
        while (actual != null) {
            System.out.println("- " + actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
