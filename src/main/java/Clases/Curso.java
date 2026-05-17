package Clases;

import Estructuras.ListaEnlazada;

public class Curso {
    private String id;
    private String nombre;
    private ListaEnlazada<Alumno> inscritos;

    public Curso(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.inscritos = new ListaEnlazada<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaEnlazada getInscritos() {
        return inscritos;
    }
}
