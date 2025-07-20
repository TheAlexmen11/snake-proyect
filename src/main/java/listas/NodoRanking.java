package listas;

import app.model.TRanking;

public class NodoRanking {

    public TRanking dato;
    public NodoRanking siguiente;

    public NodoRanking(TRanking dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
