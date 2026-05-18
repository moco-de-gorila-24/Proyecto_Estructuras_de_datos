package Estructuras;

/**
 *
 * @author Luis Alonso
 * */
public class ArregloDinamico<T> {
    private Object[] elementos;
    private int size;

    public ArregloDinamico() {
        elementos = new Object[10];
        size = 0;
    }

    public void agregar(T elemento) {
        if (size == elementos.length) {
            Object[] nuevoArreglo = new Object[elementos.length * 2];
            System.arraycopy(elementos, 0, nuevoArreglo, 0, elementos.length);
            elementos = nuevoArreglo;
        }
        elementos[size++] = elemento;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) elementos[index];
    }

    public void eliminar(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        for (int i = index; i < size - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[--size] = null;
    }

    public int size() {
        return size;
    }

    public void set(int index, T elemento) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        elementos[index] = elemento;
    }
    
    public void eliminarUltimo() {
        if (size == 0) return;
        elementos[size - 1] = null;
        size--;
    }
}
