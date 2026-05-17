package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoArbolBinarioBusqueda;
import Estructuras.Nodos.NodoListaEnlazada;

public class ArbolBinarioBusqueda<T> {
    private NodoArbolBinarioBusqueda<T> raiz;

    public void insertar(T dato) {
        raiz = insertarRec(raiz, dato);
    }

    private NodoArbolBinarioBusqueda insertarRec(NodoArbolBinarioBusqueda<T> raiz, T dato) {
        if (raiz == null) {
            raiz = new NodoArbolBinarioBusqueda(dato);
            return raiz;
        }

        Alumno alumno = (Alumno) dato;
        Alumno alumnoRaiz = (Alumno) raiz.getDato();
        int comparacion = alumno.getMatricula().compareToIgnoreCase(alumnoRaiz.getMatricula());


        if (comparacion < 0){
            raiz.setIzquierdo(insertarRec(raiz.getIzquierdo(), dato));
        }
        else if (comparacion > 0){
            raiz.setDerecho(insertarRec(raiz.getDerecho(), dato));

        }
        return raiz;
    }

    public Alumno buscarAlumno(String matricula) {
        NodoArbolBinarioBusqueda<T> resultado = buscarRec(raiz, matricula);
        return resultado != null ? (Alumno) resultado.getDato() : null;
    }

    private NodoArbolBinarioBusqueda buscarRec(NodoArbolBinarioBusqueda<T> raiz, String matricula) {
        if (raiz == null){
            return raiz;
        }
        Alumno alumno = (Alumno) raiz.getDato();
        int comparacion = matricula.compareToIgnoreCase(alumno.getMatricula());

        if (comparacion == 0) return raiz;
        if (comparacion < 0) return buscarRec(raiz.getIzquierdo(), matricula);
        return buscarRec(raiz.getDerecho(), matricula);
    }

    public void inOrder() {
        inOrderRec(raiz);
    }

    private void inOrderRec(NodoArbolBinarioBusqueda raiz) {
        if (raiz != null) {
            inOrderRec(raiz.getIzquierdo());
            Alumno alumno = (Alumno) raiz.getDato();
            System.out.println("Matrícula: " + alumno.getMatricula() + " - Nombre: " + alumno.getNombre());
            inOrderRec(raiz.getDerecho());
        }
    }

    public void obtenerTodos(ArregloDinamico<Alumno> lista) {
        obtenerTodosRec(raiz, lista);
    }

    private void obtenerTodosRec(NodoArbolBinarioBusqueda raiz, ArregloDinamico<Alumno> lista) {
        if (raiz != null) {
            obtenerTodosRec(raiz.getIzquierdo(), lista);
            Alumno alumno = (Alumno) raiz.getDato();
            lista.agregar(alumno);
            obtenerTodosRec(raiz.getDerecho(), lista);
        }
    }
    
    public void eliminar(String matricula) {
        raiz = eliminarRec(raiz, matricula);
    }

    private NodoArbolBinarioBusqueda<T> eliminarRec(NodoArbolBinarioBusqueda<T> nodo, String matricula) {
        if (nodo == null) {
            System.out.println("Estudiante no encontrado");
            return null;
        }

        Alumno alumno = (Alumno) nodo.getDato();
        int comparacion = matricula.compareToIgnoreCase(alumno.getMatricula());

        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRec(nodo.getIzquierdo(), matricula));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), matricula));
        } else {
            
            //caso1: el nodo es hoja
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) return null;

            //caso2: tiene un hijo
            if (nodo.getIzquierdo() == null) return nodo.getDerecho();
            if (nodo.getDerecho() == null) return nodo.getIzquierdo();
            
            //caso3: tiene 2 hijos
            NodoArbolBinarioBusqueda<T> sucesor = minimoNodo(nodo.getDerecho());
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminarRec(nodo.getDerecho(),((Alumno) sucesor.getDato()).getMatricula()));
        }
        return nodo;
    }

    private NodoArbolBinarioBusqueda<T> minimoNodo(NodoArbolBinarioBusqueda<T> nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }
    
    
}
