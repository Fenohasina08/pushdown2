import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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

    public List<VoteTypeCount> countVotesByType() {
        String sql = "SELECT vote_type, COUNT(id) AS count FROM vote GROUP BY vote_type ORDER BY vote_type";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<VoteTypeCount> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new VoteTypeCount(
                        rs.getString("vote_type"),
                        rs.getLong("count")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Q2 Erreur", e);
        }
    }
}
