package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoListaDobleEnlazada;

import java.util.ArrayList;
import java.util.Objects;

public class ListaDobleEnlazadaCircular<T> {
    private NodoListaDobleEnlazada<T> P;
    private NodoListaDobleEnlazada<T> last;
    private int size;


    public void add(T o) {
        NodoListaDobleEnlazada<T> nuevo = new NodoListaDobleEnlazada<>(o);
        if (last == null) {
            P = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            nuevo.setSiguiente(P);
            nuevo.setAnterior(last);
            last.setSiguiente(nuevo);
            P.setAnterior(nuevo);

        }
        last = nuevo;
        size++;
    }


    public void set(T o, int index) {
        if (index < 0 || index > size) {
            System.out.println("fuera de rango");
            return;
        }

        NodoListaDobleEnlazada<T> nuevo = new NodoListaDobleEnlazada<>(o);

        if (index == 0) {
            if (P == null) {
                P = nuevo;
                P.setSiguiente(P);
                P.setAnterior(P);
                last = P;
            } else {
                nuevo.setSiguiente(P);
                nuevo.setAnterior(last);
                P.setAnterior(nuevo);
                last.setSiguiente(nuevo);
                P = nuevo;
            }
        } else {
            NodoListaDobleEnlazada<T> actual = P;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            nuevo.setAnterior(actual);
            actual.getSiguiente().setAnterior(nuevo);
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
                P.getSiguiente().setAnterior(last);
                last.setSiguiente(P.getSiguiente());
                P = P.getSiguiente();
            }
            size--;
            return true;
        }

        NodoListaDobleEnlazada<T> actual = P;
        while (actual.getSiguiente() != P) {
            if (Objects.equals(actual.getSiguiente().getDato(), o)) {
                if (actual.getSiguiente() == last) {
                    last = actual;
                }
                NodoListaDobleEnlazada<T> toEliminate = actual.getSiguiente();
                actual.setSiguiente(toEliminate.getSiguiente());
                toEliminate.getSiguiente().setAnterior(actual);
                size--;
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }


    public int indexOf(T o) {
        NodoListaDobleEnlazada<T> actual = P;
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

    public ArregloDinamico<Alumno> getList(){
        ArregloDinamico<Alumno> alumnos = new ArregloDinamico<>();
        NodoListaDobleEnlazada<T> actual = P;
        for (int i = 0; i < size; i++) {
            Alumno alumno = (Alumno) actual.getDato();
            alumnos.agregar(alumno);
            actual = actual.getSiguiente();
        }
        return alumnos;
    }
    
    public String getNPrimeros(int n) {
        if (P == null) return "La lista de espera esta vacia";

        int mostrar = Math.min(n, size);//esto es por si se pide mas de los que hay
        StringBuilder sb = new StringBuilder();
        sb.append("Primeros ").append(mostrar).append(" en lista de espera\n");

        NodoListaDobleEnlazada<T> actual = P;
        for (int i = 0; i < mostrar; i++) {
            Alumno alumno = (Alumno) actual.getDato();
            sb.append(i + 1).append(". ")
            .append(alumno.getNombre())
            .append(" - Matricula: ").append(alumno.getMatricula())
            .append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
    
    public String recorrerAdelante() {
        if (P == null) return "La lista de espera esta vacia";

        StringBuilder sb = new StringBuilder("Lista de espera (adelante)\n");
        NodoListaDobleEnlazada<T> actual = P;
        int contador = 1;
        do {
            Alumno alumno = (Alumno) actual.getDato();
            sb.append(contador++).append(". ")
            .append(alumno.getNombre())
            .append(" - Matricula: ").append(alumno.getMatricula())
            .append("\n");
            actual = actual.getSiguiente();
        } while (actual != P);
        return sb.toString();
    }
    
    public String recorrerAtras() {
        if (last == null) return "La lista de espera esta vacia.";
        
        StringBuilder sb = new StringBuilder("Lista de espera (atras)\n");
        NodoListaDobleEnlazada<T> actual = last;
        int contador = 1;
        do {
            Alumno alumno = (Alumno) actual.getDato();
            sb.append(contador++).append(". ")
            .append(alumno.getNombre())
            .append(" - Matricula: ").append(alumno.getMatricula())
            .append("\n");
            actual = actual.getAnterior();
        } while (actual != last);
        return sb.toString();
    }
    
    public int size() {
        return size;
    }

    public boolean estaVacia() {
        return P == null;
    }

}
