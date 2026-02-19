public class InvoiceTotal {
    private int id;
    private String customerName;
    private InvoiceStatus status;
    private double amount;

    public InvoiceTotal(int id, String customerName, InvoiceStatus status, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public InvoiceStatus getStatus() { return status; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return id + " | " + customerName + " | " + status + " | " + String.format("%.2f", amount);
    }
}
