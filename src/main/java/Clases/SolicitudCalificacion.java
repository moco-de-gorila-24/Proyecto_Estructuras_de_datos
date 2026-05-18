/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


public class SolicitudCalificacion {
    private Alumno alumno;
    private int indice;
    private double valorNuevo;

    /**
     * Constructor para agregar una calificacion nueva
     * @param alumno
     * @param valorNuevo
     */
    public SolicitudCalificacion(Alumno alumno, double valorNuevo) {
        this.alumno = alumno;
        this.indice = -1;
        this.valorNuevo = valorNuevo;
    }

    /**
     * Constructor para modificar una calificacion existente
     * @param alumno
     * @param indice
     * @param valorNuevo
     */
    public SolicitudCalificacion(Alumno alumno, int indice, double valorNuevo) {
        this.alumno = alumno;
        this.indice = indice;
        this.valorNuevo = valorNuevo;
    }
    
    public Alumno getAlumno() {
        return alumno; 
    }
    public int getIndice() {
        return indice; 
    }
    public double getValorNuevo() {
        return valorNuevo;
    }
    public boolean esNueva() {
        return indice == -1; 
    }
}
