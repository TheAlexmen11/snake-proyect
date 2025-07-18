package app.dao;

import app.DatabaseConnection;
import app.model.PuntosSerpiente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PuntosSerpienteDAO {

    public void insertar(PuntosSerpiente ps) throws SQLException {
        String sql = "INSERT INTO puntos_serpiente(id_serpiente, pos_x, pos_y) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ps.getIdSerpiente());
            stmt.setInt(2, ps.getPosX());
            stmt.setInt(3, ps.getPosY());
            stmt.executeUpdate();
        }
    }
}
