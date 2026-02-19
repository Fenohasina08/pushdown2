public class InvoiceStatusTotals {
    private double totalPaid;
    private double totalConfirmed;
    private double totalDraft;

    public InvoiceStatusTotals(double totalPaid, double totalConfirmed, double totalDraft) {
        this.totalPaid = totalPaid;
        this.totalConfirmed = totalConfirmed;
        this.totalDraft = totalDraft;
    }

    public double getTotalPaid() { return totalPaid; }
    public double getTotalConfirmed() { return totalConfirmed; }
    public double getTotalDraft() { return totalDraft; }

    @Override
    public String toString() {
        return String.format("total_paid = %.2f\ntotal_confirmed = %.2f\ntotal_draft = %.2f",
                totalPaid, totalConfirmed, totalDraft);
    }
}
