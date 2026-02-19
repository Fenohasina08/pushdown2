import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final String url = "jdbc:postgresql://localhost:5432/pushdown";
    private final String user = "postgres";
    private final String password = "fenohasina3123";

    public List<InvoiceTotal> findInvoiceTotals() {
        List<InvoiceTotal> result = new ArrayList<>();
        String sql = """
    SELECT invoice.id, invoice.customer_name, invoice.status, SUM(quantity * unit_price) AS total 
    FROM invoice
    JOIN invoice_line ON invoice.id = invoice_line.invoice_id
    GROUP BY invoice.id, invoice.customer_name, invoice.status
    ORDER BY invoice.id
""";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InvoiceTotal total = new InvoiceTotal(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        InvoiceStatus.valueOf(rs.getString("status")),
                        rs.getDouble("total")
                );
                result.add(total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {
        List<InvoiceTotal> result = new ArrayList<>();
        String sql = """
            SELECT invoice.id, customer_name, status, SUM(quantity * unit_price) AS total 
            FROM invoice
            JOIN invoice_line ON invoice.id = invoice_line.invoice_id
            WHERE status IN ('CONFIRMED', 'PAID')
            GROUP BY invoice.id, customer_name, status
            ORDER BY invoice.id
            """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InvoiceTotal total = new InvoiceTotal(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        InvoiceStatus.valueOf(rs.getString("status")),
                        rs.getDouble("total")
                );
                result.add(total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public InvoiceStatusTotals computeStatusTotals() {
        String sql = """
        SELECT 
            SUM(CASE WHEN status = 'PAID' THEN quantity * unit_price ELSE 0 END) AS total_paid,
            SUM(CASE WHEN status = 'CONFIRMED' THEN quantity * unit_price ELSE 0 END) AS total_confirmed,
            SUM(CASE WHEN status = 'DRAFT' THEN quantity * unit_price ELSE 0 END) AS total_draft
        FROM invoice
        JOIN invoice_line ON invoice.id = invoice_line.invoice_id
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                InvoiceStatusTotals totals = new InvoiceStatusTotals(
                        rs.getDouble("total_paid"),
                        rs.getDouble("total_confirmed"),
                        rs.getDouble("total_draft")
                );
                return totals;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new InvoiceStatusTotals(0, 0, 0);
    }

    public double computeWeightedTurnover() {
        String sql = """
        SELECT SUM(quantity * unit_price * 
               CASE status 
                   WHEN 'PAID' THEN 1.0 
                   WHEN 'CONFIRMED' THEN 0.5 
                   ELSE 0.0 
               END) AS weighted_total
        FROM invoice
        JOIN invoice_line ON invoice.id = invoice_line.invoice_id
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("weighted_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<InvoiceTaxSummary> findInvoiceTaxSummaries() {
        List<InvoiceTaxSummary> result = new ArrayList<>();
        String sql = """
        SELECT invoice.id,
               SUM(quantity * unit_price) AS ht,
               SUM(quantity * unit_price) * tax_config.rate AS tva,
               SUM(quantity * unit_price) * (1 + tax_config.rate) AS ttc
        FROM invoice
        JOIN invoice_line ON invoice.id = invoice_line.invoice_id
        CROSS JOIN tax_config WHERE tax_config.label = 'TVA STANDARD'
        GROUP BY invoice.id, tax_config.rate
        ORDER BY invoice.id
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InvoiceTaxSummary summary = new InvoiceTaxSummary(
                        rs.getInt("id"),
                        rs.getDouble("ht"),
                        rs.getDouble("tva"),
                        rs.getDouble("ttc")
                );
                result.add(summary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public double computeWeightedTurnoverTtc() {
        String sql = """
        SELECT COALESCE(SUM(quantity * unit_price * (1 + tax_config.rate) *
               CASE invoice.status 
                   WHEN 'PAID' THEN 1.0 
                   WHEN 'CONFIRMED' THEN 0.5 
                   ELSE 0.0 
               END), 0) AS weighted_ttc
        FROM invoice
        JOIN invoice_line ON invoice.id = invoice_line.invoice_id
        CROSS JOIN tax_config
        WHERE tax_config.label = 'TVA STANDARD'
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("weighted_ttc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}


