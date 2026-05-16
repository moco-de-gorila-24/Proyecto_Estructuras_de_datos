package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoListaEnlazada;

public class ListaEnlazada {
    private NodoListaEnlazada head;

    public void add(Alumno alumno) {
        NodoListaEnlazada nuevo = new NodoListaEnlazada(alumno);
        if (head == null) {
            head = nuevo;
        } else {
            NodoListaEnlazada actual = head;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    public void mostrar() {
        if (head == null) {
            System.out.println("No hay estudiantes inscritos.");
            return;
        }
        NodoListaEnlazada actual = head;
        while (actual != null) {
            System.out.println("Matrícula: " + actual.getAlumno().getMatricula() + " - Nombre: " + actual.getAlumno().getNombre());
            actual = actual.getSiguiente();
        }
    }
}
