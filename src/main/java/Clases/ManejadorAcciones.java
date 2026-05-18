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
    
    /**
     * deshace la ultima accion y regresa mensaje para mostrar en interfaz
     */
    public String deshacer() {
        if (historial.isEmpty()) return "No hay acciones para deshacer";
        
        Accion accion = historial.pop();
        
        switch (accion.getTipo()) {
            
            case "REGISTRAR_ALUMNO":
                Alumno alumno = (Alumno) accion.getDato();
                bst.eliminar(alumno.getMatricula());
                return "Deshecho: registro de " + alumno.getNombre();
                
            case "INSCRIBIR":
                ContextoInscripcion ci = (ContextoInscripcion) accion.getDato();
                ci.curso.getInscritos().eliminar(ci.alumno);
                return "Deshecho: inscripcion de " + ci.alumno.getNombre()
                     + " en " + ci.curso.getNombre();
                
            case "DAR_BAJA":
                ContextoInscripcion cb = (ContextoInscripcion) accion.getDato();
                cb.curso.getInscritos().add(cb.alumno);
                return "Deshecho: baja de " + cb.alumno.getNombre()
                     + " en " + cb.curso.getNombre();
                
            case "AGREGAR_CALIFICACION":
                ContextoCalificacion cc = (ContextoCalificacion) accion.getDato();
                if (cc.valorAnterior == -1) {
                    cc.alumno.getCalificaciones().eliminarUltimo();
                    return "Deshecho: calificacion nueva de " + cc.alumno.getNombre() + " eliminada";
                } else {
                    cc.alumno.getCalificaciones().set(cc.indice, cc.valorAnterior);
                    return "Deshecho: calificacion de " + cc.alumno.getNombre()
                         + " restaurada a " + cc.valorAnterior;
                }
                
            default:
                return "Accion desconocida: " + accion.getTipo();
        }
    }

    /**
     * regresa el historial como string para mostrar en interfaz
     */
    public String getHistorial() {
        if (historial.isEmpty()) return "El historial esta vacio";
        
        Pila<Accion> auxiliar = new Pila<>();
        StringBuilder sb = new StringBuilder("-----Historial de acciones-----\n");
        int contador = 1;
        
        while (!historial.isEmpty()) {
            Accion accion = historial.pop();
            sb.append(contador++).append(". ").append(accion.toString()).append("\n");
            auxiliar.push(accion);
        }
        
        while (!auxiliar.isEmpty()) {
            historial.push(auxiliar.pop());
        }
        
        return sb.toString();
    }
    
    public boolean hayAcciones() {
        return !historial.isEmpty();
    }
}