package app.model;
public class TRanking {
    private String nombre;
    private int puntos;

    public TRanking() {
    }
    
    public TRanking(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + ", Puntos: " + puntos;
    }
}

