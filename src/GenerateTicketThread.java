import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

public class GenerateTicketThread extends Thread { 
    private final String customerName;
    private final String serviceType;

    public GenerateTicketThread(String customerName, String serviceType) {
        this.customerName = customerName;
        this.serviceType = serviceType;
        setName("GENERATOR-" + serviceType.substring(0, 3).toUpperCase()); 
    }

    @Override
    public void run() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                return;
            }

            Thread.sleep(new Random().nextInt(500) + 500); 

            String sql = "INSERT INTO tickets (customer_name, service_type) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, serviceType);
            ps.executeUpdate();

            System.out.printf("[%s] Ticket baru dibuat: %s untuk layanan %s.%n", 
                              Thread.currentThread().getName(), 
                              customerName, 
                              serviceType);
        } catch (Exception e) {
            System.err.println("Gagal membuat tiket: " + e.getMessage());
        }
    }
}