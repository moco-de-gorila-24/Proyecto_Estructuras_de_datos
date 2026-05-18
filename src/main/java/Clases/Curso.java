package Clases;

import Estructuras.ListaDobleEnlazadaCircular;
import Estructuras.ListaEnlazada;
import Estructuras.ListaEnlazadaCircular;

public class Curso {
    private String id;
    private String nombre;
    private ListaEnlazada<Alumno> inscritos;
    private ListaEnlazadaCircular<Alumno> roles;
    private ListaDobleEnlazadaCircular<Alumno> listaEspera;
    private int capacidadMaxima;

    public Curso(String id, String nombre, int capacidadMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.inscritos = new ListaEnlazada<>();
        this.roles = new ListaEnlazadaCircular<>();
        this.listaEspera = new ListaDobleEnlazadaCircular<>(); 
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
    
    public ListaDobleEnlazadaCircular<Alumno> getListaEspera() {
        return listaEspera;
    }
    
    public boolean isFull() {
        return inscritos.size() >= capacidadMaxima;
    }
}