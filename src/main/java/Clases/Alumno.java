package Clases;

import Estructuras.ArregloDinamico;

public class Alumno implements Comparable<Alumno>{
    private String matricula;
    private String nombre;
    private String telefono;
    private String email;
    private String domicilio;
    ArregloDinamico<Double> calificaciones;

    public Alumno(String matricula, String nombre, String telefono, String email, String domicilio) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.domicilio = domicilio;
        this.calificaciones = new ArregloDinamico<>();
    }

    public void agregarCalificacion(double calif) {
        calificaciones.agregar(calif);
    }

    public double getPromedio() { // [cite: 48]
        if (calificaciones.size() == 0) return 0.0;
        double suma = 0;
        for (int i = 0; i < calificaciones.size(); i++) {
            suma += calificaciones.get(i);
        }
        return suma / calificaciones.size();
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public ArregloDinamico<Double> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArregloDinamico<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    @Override
    public int compareTo(Alumno o) {
        return this.matricula.compareToIgnoreCase(o.matricula);
    }
}
