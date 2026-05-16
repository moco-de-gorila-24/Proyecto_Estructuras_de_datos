package Estructuras.Nodos;

import Clases.Alumno;

public class NodoListaEnlazada {
    private Alumno alumno;
    private NodoListaEnlazada siguiente;

    public NodoListaEnlazada(Alumno alumno) {
        this.alumno = alumno; this.siguiente = null;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public NodoListaEnlazada getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaEnlazada siguiente) {
        this.siguiente = siguiente;
    }
}
