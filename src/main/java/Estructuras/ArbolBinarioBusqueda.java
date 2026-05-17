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
        int comparacion = alumno.getMatricula().compareToIgnoreCase(alumno.getMatricula());


        if (comparacion < 0){
            raiz.setIzquierdo(insertarRec(raiz.getIzquierdo(), dato));

        }
        else if (comparacion > 0){
            raiz.setDerecho(insertarRec(raiz.getDerecho(), dato));

        }
        return raiz;
    }

    public Alumno buscarAlumno(String matricula) {
        NodoArbolBinarioBusqueda resultado = buscarRec(raiz, matricula);
        Alumno alumno = (Alumno) resultado.getDato();
        return resultado != null ? alumno : null;
    }

    private NodoArbolBinarioBusqueda buscarRec(NodoArbolBinarioBusqueda<T> raiz, String matricula) {
        if (raiz == null){
            return raiz;
        }
        Alumno alumno = (Alumno) raiz.getDato();
        int comparacion = matricula.compareToIgnoreCase(alumno.getMatricula());

        if (comparacion < 0){
            return buscarRec(raiz.getIzquierdo(), matricula);
        }
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
}
