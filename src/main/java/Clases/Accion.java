/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


public class Accion {

    private final String tipo;
    private final Object dato;

    public Accion(String tipo, Object dato) {
        this.tipo = tipo;
        this.dato = dato;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getDato() {
        return dato;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "tipo='" + tipo + '\'' +
                ", dato=" + dato +
                '}';
    }
}

