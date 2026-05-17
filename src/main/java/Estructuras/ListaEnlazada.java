package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoListaEnlazada;

import java.util.Iterator;


public class ListaEnlazada<T> implements Iterable<T> {
    private NodoListaEnlazada<T> head;
    private int size;

    public void add(T dato) {
        NodoListaEnlazada<T> nuevo = new NodoListaEnlazada<>(dato);
        if (head == null) {
            head = nuevo;
        } else {
            NodoListaEnlazada<T> actual = head;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        size++;
    }

    public void eliminar(T dato) {
        if (head == null){
            return;
        }

        if (head.getDato().equals(dato)) {
            head = head.getSiguiente();
            size--;
            return;
        }

        NodoListaEnlazada<T> actual = head;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                size--;
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    public void mostrar() {
        if (head == null) {
            System.out.println("No hay estudiantes inscritos.");
            return;
        }
        NodoListaEnlazada<T> actual = head;
        while (actual != null) {
            Alumno alumno = (Alumno) actual.getDato();

            System.out.println("Matrícula: " + alumno.getMatricula() + " - Nombre: " + alumno.getNombre());
            actual = actual.getSiguiente();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            NodoListaEnlazada<T> actual = head;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }
    
    /**
     * regresa el tamaño de la lista
     * @return 
     */
    public int size() { // ← método nuevo
        return size;
    }

}
