import java.sql.*;

public class DataRetriever {
    private DBConnection dbConnection;

    public DataRetriever(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long countAllVotes() {
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(id) AS total_votes FROM vote")) {
            if (rs.next()) {
                return rs.getLong("total_votes");
            }
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Q1 Erreur", e);
        }
    }


}
