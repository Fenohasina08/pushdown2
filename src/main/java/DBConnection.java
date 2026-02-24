import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {

    public Connection getConnection() {
        try {
            String jdbcURl = System.getenv("JDBC_URl"); //
            String user = System.getenv("postgres");
            String password = System.getenv("fenohasina3123");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ceni", "postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
