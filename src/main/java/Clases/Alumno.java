package Clases;

import Estructuras.ArregloDinamico;

public class Alumno implements Comparable<Alumno>{
    private String matricula;
    private String nombre;
    private String telefono;
    private String email;
    
    //atributos de domicilio
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    
    ArregloDinamico<Double> calificaciones;

    public Alumno() {
    }

    public Alumno(String matricula, String nombre, String telefono, String email, String calle, String numero, String colonia, String ciudad, ArregloDinamico<Double> calificaciones) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.calificaciones = calificaciones;
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
