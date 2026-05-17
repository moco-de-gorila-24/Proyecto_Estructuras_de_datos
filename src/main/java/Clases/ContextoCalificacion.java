/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author luiscarlosbeltran
 */
public class ContextoCalificacion {
    public Alumno alumno;
    public int indice;
    public double valorAnterior;

    public ContextoCalificacion(Alumno alumno, int indice, double valorAnterior) {
        this.alumno = alumno;
        this.indice = indice;
        this.valorAnterior = valorAnterior;
    }
}