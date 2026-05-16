package Estructuras.Nodos;

public class NodoPila {
    private String accion;
    private NodoPila siguiente;

    public NodoPila(String accion) {
        this.accion = accion; this.siguiente = null;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila siguiente) {
        this.siguiente = siguiente;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
