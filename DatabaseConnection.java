import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TrainStation";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Add it to your library.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
