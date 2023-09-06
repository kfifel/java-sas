package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {
    private static final String DB_NAME = "library_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String SERVER_NAME = "localhost";
    private static Connection conn = null;

    private DataBase() {
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + SERVER_NAME + "/" + DB_NAME, USERNAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error in connection to the database: " + e);
            }
        }

        return conn;
    }

    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Disconnection error: " + e.getMessage());
        }

        conn = null;
    }
}