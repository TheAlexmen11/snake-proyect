package app.dao;

import app.DatabaseConnection;
import app.model.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public void insertar(Partida p) throws SQLException {
        String sql = "INSERT INTO partida(id_jugador, puntos, en_progreso) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdJugador());
            stmt.setInt(2, p.getPuntos());
            stmt.setBoolean(3, p.isEnProgreso());
            stmt.executeUpdate();
        }
    }

    public List<Partida> obtenerTodas() throws SQLException {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT * FROM partida";

        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Partida p = new Partida();
                p.setIdPartida(rs.getInt("id_partida"));
                p.setIdJugador(rs.getInt("id_jugador"));
                p.setPuntos(rs.getInt("puntos"));
                p.setEnProgreso(rs.getBoolean("en_progreso"));
                p.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
                Timestamp fin = rs.getTimestamp("fecha_fin");
                p.setFechaFin(fin != null ? fin.toLocalDateTime() : null);
                lista.add(p);
            }
        }
        return lista;
    }
}