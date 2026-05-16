package Estructuras.Nodos;

import Clases.Alumno;

public class NodoArbolBinarioBusqueda {
    private Alumno alumno;
    private NodoArbolBinarioBusqueda izquierdo, derecho;

    public NodoArbolBinarioBusqueda(Alumno alumno) {
        this.alumno = alumno;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public NodoArbolBinarioBusqueda getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbolBinarioBusqueda izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbolBinarioBusqueda getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbolBinarioBusqueda derecho) {
        this.derecho = derecho;
    }
}
