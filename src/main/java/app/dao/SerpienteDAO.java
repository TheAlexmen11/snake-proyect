package app.dao;

import app.DatabaseConnection;
import app.model.Serpiente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SerpienteDAO {

    public void insertar(Serpiente s) throws SQLException {
        String sql = "INSERT INTO serpiente(id_partida, color) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getIdPartida());
            stmt.setString(2, s.getColor());
            stmt.executeUpdate();
        }
    }
}