/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras.Nodos;

import Clases.Alumno;

/**
 * Clase que funciona como nodo para los arboles AVL, mantiene un valor de altura del nodo
 * @author luiscarlosbeltran
 */
public class NodoAVL {
    private double promedio;
    private Alumno alumno;
    private NodoAVL hijoIzq, hijoDer;
    private int altura;
    
    public NodoAVL(){
    }
    
    /**
     * el constructor que se usara para crear un nodo
     * @param promedio recibe el promedio del alumno
     * @param alumno recibe al alumno
     */
    public NodoAVL(double promedio, Alumno alumno) {
        this.promedio = promedio;
        this.alumno = alumno;
        this.altura = 1;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public NodoAVL getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(NodoAVL hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public NodoAVL getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(NodoAVL hijoDer) {
        this.hijoDer = hijoDer;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    
}
