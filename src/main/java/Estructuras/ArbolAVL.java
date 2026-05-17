/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Clases.Alumno;
import Estructuras.Nodos.NodoAVL;

/**
 * Clase de arbol AVL, usa los NodoAVL
 * @author luiscarlosbeltran
 */
public class ArbolAVL {
    private NodoAVL raiz;
    
    /**
     * metodo para conseguir la altura de un nodo
     * si nodo es null regresa 0 y no una nullpointerexception
     * @param nodo
     * @return 
     */
    private int altura(NodoAVL nodo) {
    if (nodo == null) return 0;
    return nodo.getAltura();
    }
    
    /**
     * calcula el factor de balance de un nodo con la formula que vimos en la clase
     * FB= alturaDer - alturaIzq
     * @param nodo
     * @return 
     */
    private int factorBalance(NodoAVL nodo) {
        if (nodo == null) return 0;
        return altura(nodo.getHijoDer()) - altura(nodo.getHijoIzq());
    }
    
    /**
     * recalcula la altura de un nodo
     * la formula es 1 + la altura de su hijo mas alto, ya sea derecho o izquierdo
     * @param nodo 
     */
    private void actualizarAltura(NodoAVL nodo) {
        nodo.setAltura(1 + Math.max(altura(nodo.getHijoIzq()), altura(nodo.getHijoDer())));
    }

    /**
     * metodo que realiza una rotacion a la derecha
     * @param nodo
     * @return 
     */
    private NodoAVL rotarDerecha(NodoAVL nodo) {
        NodoAVL hijoIzq = nodo.getHijoIzq();
        NodoAVL hijoIzqDer = hijoIzq.getHijoDer();

        hijoIzq.setHijoDer(nodo);
        nodo.setHijoIzq(hijoIzqDer);

        actualizarAltura(nodo);
        actualizarAltura(hijoIzq);
        return hijoIzq;
    }

    /**
     * metodo que realiza una rotacion a la izquierda
     * @param nodo
     * @return 
     */
    private NodoAVL rotarIzquierda(NodoAVL nodo) {
        NodoAVL hijoDer = nodo.getHijoDer();
        NodoAVL hijoDerIzq = hijoDer.getHijoIzq();

        hijoDer.setHijoIzq(nodo);
        nodo.setHijoDer(hijoDerIzq);

        actualizarAltura(nodo);
        actualizarAltura(hijoDer);
        return hijoDer;
    }

    /**
     * metodo publico para insertar, utiliza el insertar recursivo
     * @param promedio
     * @param alumno 
     */
    public void insertar(double promedio, Alumno alumno) {
        raiz = insertarRec(raiz, promedio, alumno);
    }

    /**
     * metodo de insertar recursivo
     * es llamado por insertar y recibe el nodo raiz y los parametros de promedio y el alumno
     * @param nodo
     * @param promedio
     * @param alumno
     * @return 
     */
    private NodoAVL insertarRec(NodoAVL nodo, double promedio, Alumno alumno) {
        //si la raiz es nula lo inserta ahi
        if (nodo == null) return new NodoAVL(promedio, alumno);

        if (promedio < nodo.getPromedio())
            nodo.setHijoIzq(insertarRec(nodo.getHijoIzq(), promedio, alumno));
        else if (promedio > nodo.getPromedio())
            nodo.setHijoDer(insertarRec(nodo.getHijoDer(), promedio, alumno));
        else
            nodo.setHijoDer(insertarRec(nodo.getHijoDer(), promedio, alumno)); //en caso de un promedio repetido, se manda a la derecha

        //actualiza la altura del nodo
        actualizarAltura(nodo);
        
        //checa el FB y hace rotacion si es necesario
        int balance = factorBalance(nodo);
        
        //desbalance RR
        if (balance > 1 && promedio >= nodo.getHijoDer().getPromedio()) return rotarIzquierda(nodo);
        
        //desbalance RL
        if (balance > 1 && promedio < nodo.getHijoDer().getPromedio()) {
            nodo.setHijoDer(rotarDerecha(nodo.getHijoDer()));
            return rotarIzquierda(nodo);
        }
        
        //desbalance LL
        if (balance < -1 && promedio < nodo.getHijoIzq().getPromedio()) return rotarDerecha(nodo);
        
        //desbalance LR
        if (balance < -1 && promedio > nodo.getHijoIzq().getPromedio()) {
            nodo.setHijoIzq(rotarIzquierda(nodo.getHijoIzq()));
            return rotarDerecha(nodo);
        }
        return nodo;
    }

    /**
     * metodo publico que imprime los datos de los nodos en orden
     * si no encuentra datos avisa con un print
     */
    public void inOrderPrint() {
        if (this.raiz == null) {
            System.out.println("No se encontraron datos");
        } else {
            inOrderPrintRec(this.raiz);
        }
    }

    /**
     * metodo que es llamado para imprimir los datos de los nodos en orden
     * recibe el nodo raiz desde el metodo publico que lo llama
     * @param nodo 
     */
    private void inOrderPrintRec(NodoAVL nodo) {
        if (nodo == null) return;
        //izquierdo
        inOrderPrintRec(nodo.getHijoIzq());
        
        //procesar
        System.out.println("Promedio: " +nodo.getPromedio() + " , Nombre: "+nodo.getAlumno().getNombre());
        
        //derecho
        inOrderPrintRec(nodo.getHijoDer());
    }
    
    //nota para los metodos de imprimir: quiza haga falta cambiarlos a que 
    //devuelvan string o una lista de string para mostrar en la interfaz :P
}
