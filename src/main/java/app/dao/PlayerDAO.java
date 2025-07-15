package app.dao;

import app.DatabaseConnection;
import app.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public void insertar(Player p) throws SQLException {
        String sql = "INSERT INTO player(nombre) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.executeUpdate();
        }
    }

    public List<Player> obtenerTodos() throws SQLException {
        List<Player> lista = new ArrayList<>();
        String sql = "SELECT * FROM player";

        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Player p = new Player();
                p.setIdJugador(rs.getInt("id_jugador"));
                p.setNombre(rs.getString("nombre"));
                lista.add(p);
            }
        }
        return lista;
    }
}