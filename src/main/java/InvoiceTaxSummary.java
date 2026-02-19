public class InvoiceTaxSummary {
    private int id;
    private double ht;
    private double tva;
    private double ttc;

    public InvoiceTaxSummary(int id, double ht, double tva, double ttc) {
        this.id = id;
        this.ht = ht;
        this.tva = tva;
        this.ttc = ttc;
    }

    @Override
    public String toString() {
        return id + " | HT " + String.format("%.2f", ht) +
                " | TVA " + String.format("%.2f", tva) +
                " | TTC " + String.format("%.2f", ttc);
    }
}
