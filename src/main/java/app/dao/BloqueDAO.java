package app.dao;

import app.DatabaseConnection;
import app.model.Bloque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BloqueDAO {

    public void insertar(Bloque b) throws SQLException {
        String sql = "INSERT INTO bloque(id_partida, pos_x, pos_y) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, b.getIdPartida());
            stmt.setInt(2, b.getPosX());
            stmt.setInt(3, b.getPosY());
            stmt.executeUpdate();
        }
    }
}
