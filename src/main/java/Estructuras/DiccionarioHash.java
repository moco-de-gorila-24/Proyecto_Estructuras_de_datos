package Estructuras;

import Clases.Curso;
import Estructuras.Nodos.NodoDiccionario;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;


public class DiccionarioHash <K, V> implements Iterable<V> {
    private final ListaEnlazada<NodoDiccionario <K, V>> tablaHash[];
    private int nEntradas;
    private final int tamTablaHash;

    public DiccionarioHash(int tamTablaHash) {
        @SuppressWarnings("unchecked")
        ListaEnlazada<NodoDiccionario<K, V>>[] tablaHashTemp
                = (ListaEnlazada<NodoDiccionario<K, V>>[])
                new ListaEnlazada[tamTablaHash];
        tablaHash = tablaHashTemp;
        nEntradas = 0;
        this.tamTablaHash = tamTablaHash;
    }

    public V agregar(K llave, V valor) {
        int indiceTablaHash = getIndexHashTable(llave);
        if(tablaHash[indiceTablaHash] != null) {
            ListaEnlazada<NodoDiccionario<K, V>> balde = tablaHash[indiceTablaHash];

            for(NodoDiccionario<K, V> nodo: balde)
                if(llave.equals(nodo.getLlave())) {
                    V valorActual = nodo.getValor();
                    nodo.setValor(valor);
                    return valorActual;
                }
            balde.add(new NodoDiccionario<>(llave, valor));
            nEntradas++;
            return null;
        }
        tablaHash[indiceTablaHash] = new ListaEnlazada<NodoDiccionario<K, V>>();
        tablaHash[indiceTablaHash].add(new NodoDiccionario<K, V>(llave, valor));
        nEntradas++;
        return null;
    }

    private int getIndexHashTable(K llave) {
        return Math.abs(llave.hashCode()) % tamTablaHash;
    }

    public V eliminar(K llave) {
        int indiceTablaHash = getIndexHashTable(llave);
        if(tablaHash[indiceTablaHash] != null) {
            ListaEnlazada<NodoDiccionario<K, V>> balde
                    = tablaHash[indiceTablaHash];
            for(NodoDiccionario<K, V> entrada: balde)
                if(llave.equals(entrada.getLlave())) {
                    balde.eliminar(entrada);
                    nEntradas--;
                    return entrada.getValor();
                }
        }
        return null;
    }

    public int size() {
        return nEntradas;
    }

    public boolean empty() {
        return nEntradas == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        for (ListaEnlazada<NodoDiccionario<K, V>> balde : tablaHash) {
            if (balde != null) {
                for (NodoDiccionario<K, V> entrada : balde) {
                    sb.append("  ").append(entrada.getLlave())
                    .append(": ").append(((Curso) entrada.getValor()).getNombre())
                    .append("\n");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }


    @Override
    public Iterator iterator() {
        return new Iterator<V>() {
            int indiceBalde = 0;
            Iterator<NodoDiccionario<K, V>> iteradorBalde = null;

            @Override
            public boolean hasNext() {
                if (iteradorBalde != null && iteradorBalde.hasNext())
                    return true;

                while (indiceBalde < tamTablaHash) {
                    if (tablaHash[indiceBalde] != null) {
                        iteradorBalde = tablaHash[indiceBalde].iterator();
                        indiceBalde++;
                        if (iteradorBalde.hasNext()) return true;
                    } else {
                        indiceBalde++;
                    }
                }
                return false;
            }

            @Override
            public V next() {
                return iteradorBalde.next().getValor();
            }
        };
    }
    
    //metodo get para buscar un curso por id 
    public V get(K llave) {
        int indice = getIndexHashTable(llave);
        if (tablaHash[indice] != null) {
            for (NodoDiccionario<K, V> nodo : tablaHash[indice]) {
                if (llave.equals(nodo.getLlave())) {
                    return nodo.getValor();
                }
            }
        }
        return null;
    }
    
    //metodo para saber si ya existe una llave
    public boolean contiene(K llave) {
        return get(llave) != null;
    }
}
