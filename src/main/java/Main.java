public class Main {
    public static void main(String[] args) {
        DataRetriever retriever = new DataRetriever();

         System.out.println("=== Q1 ===");
        retriever.findInvoiceTotals().forEach(System.out::println);

         System.out.println("\n=== Q2 ===");
        retriever.findConfirmedAndPaidInvoiceTotals().forEach(System.out::println);

         System.out.println("\n=== Q3 ===");
        InvoiceStatusTotals statusTotals = retriever.computeStatusTotals();
        System.out.println(statusTotals);

         System.out.println("\n=== Q4 ===");
        double weighted = retriever.computeWeightedTurnover();
        System.out.println("Chiffre d'affaires pondéré : " + weighted);

        System.out.println("\n=== Q5-A ===");
        retriever.findInvoiceTaxSummaries().forEach(System.out::println);

        System.out.println("\n=== Q5-B ===");
        double weightedTtc = retriever.computeWeightedTurnoverTtc();
        System.out.println("Chiffre d'affaires TTC pondéré : " + weightedTtc);

    }
}
