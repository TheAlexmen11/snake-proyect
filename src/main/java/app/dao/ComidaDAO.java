package app.dao;

import app.DatabaseConnection;
import app.model.Comida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComidaDAO {

    public void insertar(Comida c) throws SQLException {
        String sql = "INSERT INTO comida(id_partida, pos_x, pos_y, activa) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getIdPartida());
            stmt.setInt(2, c.getPosX());
            stmt.setInt(3, c.getPosY());
            stmt.setBoolean(4, c.isActiva());
            stmt.executeUpdate();
        }
    }
}
