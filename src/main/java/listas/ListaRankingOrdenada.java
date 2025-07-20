package listas;

import app.model.TRanking;
import java.util.ArrayList;
import java.util.List;

public class ListaRankingOrdenada {

    private NodoRanking cabeza;

    public void insertarOrdenado(TRanking nuevoDato) {
        NodoRanking nuevoNodo = new NodoRanking(nuevoDato);

        if (cabeza == null || nuevoDato.getPuntos() > cabeza.dato.getPuntos()) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
        } else {
            NodoRanking actual = cabeza;
            while (actual.siguiente != null && actual.siguiente.dato.getPuntos() >= nuevoDato.getPuntos()) {
                actual = actual.siguiente;
            }
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
    }

    public List<TRanking> aLista() {
        List<TRanking> lista = new ArrayList<>();
        NodoRanking actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }
}
