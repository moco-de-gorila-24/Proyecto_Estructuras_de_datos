/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;
import Estructuras.Nodos.NodoCola;

public class Cola<T> {

    private NodoCola<T> frente;
    private NodoCola<T> fin;
    private int size;

    public Cola() {
        frente = null;
        fin = null;
        size = 0;
    }

    /**
     * Inserta un elemento al final de la cola.
     */
    public void enqueue(T elemento) {

        NodoCola<T> nuevo =
                new NodoCola<>(elemento);

        if (isEmpty()) {

            frente = nuevo;
            fin = nuevo;

        } else {

            fin.setSiguiente(nuevo);
            fin = nuevo;
        }

        size++;
    }

    /**
     * Elimina y retorna el elemento al frente.
     */
    public T dequeue() {

        if (isEmpty()) {
            return null;
        }

        T dato = frente.getDato();

        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        size--;

        return dato;
    }

    /**
     * Obtiene el elemento al frente sin eliminarlo.

     */
    public T peek() {

        if (isEmpty()) {
            return null;
        }

        return frente.getDato();
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean isEmpty() {
        return frente == null;
    }

    /**
     * Devuelve cantidad de elementos.
     */
    public int size() {
        return size;
    }
}
