public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        DataRetriever retriever = new DataRetriever(db);

        long totalVote = retriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        System.out.println("Q2: " + retriever.countVotesByType());

        System.out.println("Q3: " + retriever.countValidVotesByCandidate());

        System.out.println("Q4: " + retriever.computeVoteSummary());

        double taux = retriever.computeTurnoutRate();
        String tauxFormate;
        if (taux == Math.floor(taux)) {
            tauxFormate = String.valueOf((int) taux);
        } else {
            tauxFormate = String.valueOf(taux);
        }
        System.out.println("Q5: taux de participation = " + tauxFormate + "%");

        ElectionResult winner = retriever.findWinner();
        System.out.println("Q6: candidate_name | valid_vote_count");
        if (winner != null) {
            System.out.println(winner.getCandidateName() + " | " + winner.getValidVoteCount());
        } else {
            System.out.println("Aucun gagnant");
        }
    }
}
