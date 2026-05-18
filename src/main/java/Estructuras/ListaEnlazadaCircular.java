package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoListaEnlazada;

import java.util.Objects;

public class ListaEnlazadaCircular<T> {
    private NodoListaEnlazada<T> P;
    private NodoListaEnlazada<T> last;
    private NodoListaEnlazada<T> actual;
    private int size;
    
    public String rotarRol() {
        if (P == null) return "No hay estudiantes con rol asignado";

        if (actual == null) actual = P;
        else actual = actual.getSiguiente();

        Alumno alumno = (Alumno) actual.getDato();
        return "Nuevo tutor/lider: " + alumno.getNombre() + " - Matrícula: " + alumno.getMatricula();
    }
    
    public T getActual() {
        if (actual == null) return null;
        return actual.getDato();
    }
    
    public String getContenido() {
        if (P == null) return "No hay estudiantes con rol asignado";
        StringBuilder sb = new StringBuilder("Estudiantes con rol\n");
        NodoListaEnlazada<T> nodo = P;
        int contador = 1;
        do {
            Alumno alumno = (Alumno) nodo.getDato();
            sb.append(contador++).append(". ")
            .append(alumno.getNombre())
            .append(" - Matricula: ").append(alumno.getMatricula());
            if (nodo == actual) sb.append(" <- rol actual");
            sb.append("\n");
            nodo = nodo.getSiguiente();
        } while (nodo != P);
        return sb.toString();
    }
    
    public int size() { 
        return size; 
    }
    
    public boolean estaVacia() { 
        return P == null;
    }

    public void add(T o) {
        NodoListaEnlazada<T> nuevo = new NodoListaEnlazada<>(o);
        if (last == null) {
            P = nuevo;
            nuevo.setSiguiente(nuevo);
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
