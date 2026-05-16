package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoArbolBinarioBusqueda;
import Estructuras.Nodos.NodoListaEnlazada;

public class ArbolBinarioBusqueda {
    private NodoArbolBinarioBusqueda raiz;

    public void insertar(Alumno alumno) {
        raiz = insertarRec(raiz, alumno);
    }

    private NodoArbolBinarioBusqueda insertarRec(NodoArbolBinarioBusqueda raiz, Alumno alumno) {
        if (raiz == null) {
            raiz = new NodoArbolBinarioBusqueda(alumno);
            return raiz;
        }
        int comparacion = alumno.getMatricula().compareToIgnoreCase(raiz.getAlumno().getMatricula());

        if (comparacion < 0){
            raiz.setIzquierdo(insertarRec(raiz.getIzquierdo(), alumno));

        }
        else if (comparacion > 0){
            raiz.setDerecho(insertarRec(raiz.getDerecho(), alumno));

        }
        return raiz;
    }

    public Alumno buscarAlumno(String matricula) {
        NodoArbolBinarioBusqueda resultado = buscarRec(raiz, matricula);
        return resultado != null ? resultado.getAlumno() : null;
    }

    private NodoArbolBinarioBusqueda buscarRec(NodoArbolBinarioBusqueda raiz, String matricula) {
        if (raiz == null || raiz.getAlumno().getMatricula() == matricula){
            return raiz;
        }
        int comparacion = matricula.compareToIgnoreCase(raiz.getAlumno().getMatricula());

        if (comparacion > 0){
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
            System.out.println("Matrícula: " + raiz.getAlumno().getMatricula() + " - Nombre: " + raiz.getAlumno().getNombre());
            inOrderRec(raiz.getDerecho());
        }
    }

    public void obtenerTodos(ArregloDinamico<Alumno> lista) {
        obtenerTodosRec(raiz, lista);
    }

    private void obtenerTodosRec(NodoArbolBinarioBusqueda raiz, ArregloDinamico<Alumno> lista) {
        if (raiz != null) {
            obtenerTodosRec(raiz.getIzquierdo(), lista);
            lista.agregar(raiz.getAlumno());
            obtenerTodosRec(raiz.getDerecho(), lista);
        }
    }
}
