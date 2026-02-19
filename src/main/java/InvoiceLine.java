import java.math.BigDecimal;

public class InvoiceLine {
    private int id;
    private int invoiceId;
    private String label;
    private int quantity;
    private BigDecimal unitPrice;

    public InvoiceLine() {}

    public InvoiceLine(int id, int invoiceId, String label, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.label = label;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
