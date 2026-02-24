public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        DataRetriever retriever = new DataRetriever(db);

        long totalVote = retriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        System.out.println("Q2: " + retriever.countVotesByType());

    }
}
