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

        public List<CandidateVoteCount> countValidVotesByCandidate() {
            String sql = "SELECT candidate.name AS candidate_name, COUNT(vote.id) AS valid_vote " +
                "FROM candidate " +
                "LEFT JOIN vote ON candidate.id = vote.candidate_id AND vote.vote_type = 'VALID' " +
                "GROUP BY candidate.id, candidate.name " +
                "ORDER BY candidate.name";

            try (Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                List<CandidateVoteCount> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new CandidateVoteCount(
                        rs.getString("candidate_name"),
                        rs.getLong("valid_vote")
                                                        ));
                                }
                        return result;
                }
            catch (SQLException e) {
                        throw new RuntimeException("❌ Q3 Erreur", e);
                                }
        }

    public VoteSummary computeVoteSummary() {
        String sql = """
        SELECT 
            COUNT(CASE WHEN vote_type='VALID' THEN 1 END) AS valid_count,
            COUNT(CASE WHEN vote_type='BLANK' THEN 1 END) AS blank_count,
            COUNT(CASE WHEN vote_type='NULL' THEN 1 END) AS null_count
        FROM vote
        """;

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new VoteSummary(
                        rs.getLong("valid_count"),
                        rs.getLong("blank_count"),
                        rs.getLong("null_count")
                );
            }
            return new VoteSummary(0, 0, 0);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Q4 Erreur", e);
        }
    }

    public double computeTurnoutRate() {
        long totalVotes = countAllVotes();
        long totalElectors = 0;

         String sql = "SELECT COUNT(id) AS total FROM voter";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalElectors = rs.getLong("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Q5 Erreur lors de la récupération du nombre d'électeurs", e);
        }

        if (totalElectors == 0) {
            return 0.0;
        }
        return ((double) totalVotes / totalElectors) * 100;
    }

    public ElectionResult findWinner() {
        String sql = "SELECT candidate.name, COUNT(vote.id) AS valid_vote_count " +
                "FROM candidate " +
                "LEFT JOIN vote ON candidate.id = vote.candidate_id AND vote.vote_type = 'VALID' " +
                "GROUP BY candidate.id, candidate.name " +
                "ORDER BY valid_vote_count DESC, candidate.name " +
                "LIMIT 1";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String name = rs.getString("name");
                long count = rs.getLong("valid_vote_count");
                return new ElectionResult(name, count);
            } else {
                 return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Q6 Erreur lors de la recherche du gagnant", e);
        }
    }
}

