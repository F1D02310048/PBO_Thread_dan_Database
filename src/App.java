public class App {
    public static void main(String[] args) {
        System.out.println("\n\t=== SIMULASI SISTEM ANTRIAN TIKET ONLINE ===");
        
        // membuat 3 thread untuk mengajukan tiket baru (INSERT)
        GenerateTicketThread g1 = new GenerateTicketThread("Risa", "Pengembalian Dana");
        GenerateTicketThread g2 = new GenerateTicketThread("Budi", "Perubahan Data Akun");
        GenerateTicketThread g3 = new GenerateTicketThread("Santi", "Laporan Bug Aplikasi");
        
        // membuat 1 thread untuk memproses tiket (SELECT dan UPDATE)
        ProcessTicketThread processor = new ProcessTicketThread();
        
        System.out.println("Memulai Thread Generator dan Processor secara bersamaan...");
        g1.start();
        g2.start();
        g3.start();
        processor.start();
    }
}