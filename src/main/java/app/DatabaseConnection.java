package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/snake_BD";
    // Cambia el nombre de la base de datos por el de tu base de datos
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🔄 Conexión abierta/reabierta.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener la conexión: " + e.getMessage());
        }
        return connection;
    }
}
