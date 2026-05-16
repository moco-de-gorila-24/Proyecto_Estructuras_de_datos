package Estructuras;

import Estructuras.Nodos.NodoPila;

public class Pila {
    private NodoPila tope;

    public void apilar(String accion) {
        NodoPila nuevo = new NodoPila(accion);
        nuevo.setSiguiente(tope);
        tope = nuevo;
    }

    public String desapilar() {
        if (tope == null) return null;
        String accion = tope.getAccion();
        tope = tope.getSiguiente();
        return accion;
    }

    public void mostrar() {
        if (tope == null) {
            System.out.println("El historial está vacío.");
            return;
        }
        NodoPila actual = tope;
        while (actual != null) {
            System.out.println("- " + actual.getAccion());
            actual = actual.getSiguiente();
        }
    }
}
