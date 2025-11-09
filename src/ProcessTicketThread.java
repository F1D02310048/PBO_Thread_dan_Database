import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class ProcessTicketThread extends Thread {
    private static final int MAX_PROCESS = 10; 
    private int processedCount = 0;

    public ProcessTicketThread() {
        setName("PROCESSOR");
    }

    @Override
    public void run() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                return;
            }

            System.out.println("\n[PROCESSOR] Mulai memproses antrian... (Target: 10 Tiket)");

            while (processedCount < MAX_PROCESS) {
        
                String selectSql = "SELECT ticket_id, customer_name FROM tickets WHERE status = 'Menunggu' ORDER BY ticket_id ASC LIMIT 1";
                PreparedStatement selectPs = conn.prepareStatement(selectSql);
                ResultSet rs = selectPs.executeQuery();
                
                if (rs.next()) {
                    int ticketId = rs.getInt("ticket_id");
                    String customerName = rs.getString("customer_name");

                    String updateProcessingSql = "UPDATE tickets SET status = 'Diproses' WHERE ticket_id = ?";
                    PreparedStatement updateProcessingPs = conn.prepareStatement(updateProcessingSql);
                    updateProcessingPs.setInt(1, ticketId);
                    updateProcessingPs.executeUpdate();
                    System.out.printf("[PROCESSOR] Tiket #%d (%s) sedang Diproses...%n", ticketId, customerName);
                    
                    Thread.sleep(new Random().nextInt(2000) + 2000); 

                    String updateDoneSql = "UPDATE tickets SET status = 'Selesai' WHERE ticket_id = ?";
                    PreparedStatement updateDonePs = conn.prepareStatement(updateDoneSql);
                    updateDonePs.setInt(1, ticketId);
                    updateDonePs.executeUpdate();
                    System.out.printf("[PROCESSOR] Tiket #%d (%s) Selesai.✅%n", ticketId, customerName);
                    
                    processedCount++;

                } else {
                    System.out.println("[PROCESSOR] Tidak ada antrian. Menunggu 3 detik...");
                    Thread.sleep(3000);
                }
            }
            System.out.println("\n[PROCESSOR] Selesai memproses " + processedCount + " tiket. Program dapat dihentikan.");

        } catch (Exception e) {
            System.err.println("Gagal memproses tiket: " + e.getMessage());
        }
    }
}