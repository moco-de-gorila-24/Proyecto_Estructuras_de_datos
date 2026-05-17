/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Estructuras.ArbolBinarioBusqueda;
import Estructuras.Pila;

/**
 *
 * @author luiscarlosbeltran
 */
public class ManejadorAcciones {
    private Pila<Accion> historial;
    private ArbolBinarioBusqueda bst;

    public ManejadorAcciones(ArbolBinarioBusqueda bst) {
        this.historial = new Pila<>();
        this.bst = bst;
    }

    public void registrarAccion(String tipo, Object dato) {
        historial.push(new Accion(tipo, dato));
    }

    public void deshacer() {
        if (historial.isEmpty()) {
            System.out.println("No hay acciones para deshacer");
            return;
        }

        Accion accion = historial.pop();

        switch (accion.getTipo()) {

            case "REGISTRAR_ALUMNO":
                Alumno alumno = (Alumno) accion.getDato();
                bst.eliminar(alumno.getMatricula());
                System.out.println("Deshecho: registro de " + alumno.getNombre());
                break;

            case "INSCRIBIR":
                ContextoInscripcion ci = (ContextoInscripcion) accion.getDato();
                ci.curso.getInscritos().eliminar(ci.alumno);
                System.out.println("Deshecho: inscripcion de " + ci.alumno.getNombre()
                    + " en " + ci.curso.getNombre());
                break;

            case "DAR_BAJA":
                ContextoInscripcion cb = (ContextoInscripcion) accion.getDato();
                cb.curso.getInscritos().add(cb.alumno);
                System.out.println("Deshecho: baja de " + cb.alumno.getNombre()
                    + " en " + cb.curso.getNombre());
                break;

            case "AGREGAR_CALIFICACION":
                ContextoCalificacion cc = (ContextoCalificacion) accion.getDato();
                cc.alumno.getCalificaciones().set(cc.indice, cc.valorAnterior);
                System.out.println("Deshecho: calificacion de " + cc.alumno.getNombre()
                    + " restaurada a " + cc.valorAnterior);
                break;

            default:
                System.out.println("Accion desconocida: " + accion.getTipo());
        }
    }
}