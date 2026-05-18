package Clases;

import Estructuras.ListaEnlazada;
import Estructuras.ListaEnlazadaCircular;

public class Curso {
    private String id;
    private String nombre;
    private ListaEnlazada<Alumno> inscritos;
    private ListaEnlazadaCircular<Alumno> roles;
    private int capacidadMaxima;

    public Curso(String id, String nombre, int capacidadMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.inscritos = new ListaEnlazada<>();
        this.roles = new ListaEnlazadaCircular<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaEnlazada<Alumno> getInscritos() {
        return inscritos;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public ListaEnlazadaCircular<Alumno> getRoles() {
        return roles;
    }
    
    
    
    //metodo extra para verificar que el curso no este lleno antes de añadir un nuevo alumno
    public boolean isFull() {
        return inscritos.size() >= capacidadMaxima;
    }
}
