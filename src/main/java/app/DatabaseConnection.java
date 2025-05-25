package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_de_la_base_de_datos";
    // Cambia el nombre de la base de datos por el de tu base de datos
    private static final String USER = "root";
    private static final String PASSWORD = "contaseña de la base de datos";
    // Cambia la contraseña por la de tu base de datos

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getInstance() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión establecida.");
            } catch (SQLException e) {
                System.out.println("❌ Error de conexión: " + e.getMessage());
            }
        }
        return connection;
    }
}
