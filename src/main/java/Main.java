public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        DataRetriever retriever = new DataRetriever(db);

        long totalVote = retriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        System.out.println("Q2: " + retriever.countVotesByType());

        System.out.println("Q3: " + retriever.countValidVotesByCandidate());

        System.out.println("Q4: " + retriever.computeVoteSummary());

    }
}
