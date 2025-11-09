# PBO-Thread-dan-Database: Sistem Antrian Tiket Digital
Nama: [Fadila Rahmania]

NIM: [F1D02310048]

Mata Kuliah: Pemrograman Berorientasi Objek (PBO)
## Deskripsi Proyek
Program ini merupakan implementasi konsep Multithreading dan koneksi MySQL Database menggunakan bahasa pemrograman Java (JDBC).
Program mensimulasikan sistem back-end antrian tiket layanan pelanggan, di mana proses pembuatan tiket dan pemrosesan tiket berjalan secara paralel.

Fitur Utama
- Koneksi Database: Menggunakan JDBC untuk terhubung ke MySQL.
- Multithreading Aktif: Terdapat thread **GENERATOR** (operasi tulis/INSERT) dan thread **PROCESSOR** (operasi baca/SELECT dan tulis/UPDATE) yang berjalan serentak (concurrent).
- Simulasi Antrian: Menunjukkan bagaimana data yang baru dimasukkan oleh satu thread dapat segera diambil dan diproses oleh thread lain.

## Struktur Folder

<img width="453" height="505" alt="gambar" src="https://github.com/user-attachments/assets/4aa0b2dc-7d5d-4537-b447-7092e1ea6f91" />

```
├── lib/

│   └── mysql-connector-j-9.5.0.jar  <-- Driver koneksi 

│

└── src/
    ├── App.java                   --> Kelas Utama (Main)
    
    ├── DBConnection.java          --> Konfigurasi Koneksi Database
    
    ├── GenerateTicketThread.java  --> Thread 1: Menambahkan tiket baru ke database (INSERT)
    
    └── ProcessTicketThread.java   --> Thread 2: Memproses tiket berstatus 'Menunggu' (SELECT & UPDATE)
```
    
  ## Konfigurasi Database (MySQL)

  Menyiapkan database dan tabel sebelum menjalankan program.

  1. Nyalakan Serve MySQL XAMPP.
     
  2. Jalankan Skrip SQL: Buka tool manajemen database dan jalankan skrip berikut:
```sql
CREATE DATABASE IF NOT EXISTS queue_db;
USE queue_db;

CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Menunggu' -- Status: Menunggu, Diproses, Selesai
);
```
  3. Konfigurasi Koneksi: Pastikan detail koneksi di **DBConnection.java** sesuai dengan pengaturan MySQL lokal (URL, USER, PASS).

## Struktur Database

<img width="926" height="181" alt="Screenshot 2025-11-09 191559" src="https://github.com/user-attachments/assets/aac2f107-7cac-4922-a39f-2fcce71d0856" />


## Cara Menjalankan Program (Terminal/CMD)

Jalankan perintah ini dari *root directory* proyek (`PBO-THREAD-AND-DATABASE-QUEUE`):
```bash
javac -cp "lib/mysql-connector-j-9.5.0.jar" src/*.java
```
 
## Penjelasan Kerja Program
1. **GenerateTicketThread**

    - Thread ini membuat 3 entri tiket baru (Risa, Budi, Santi) ke dalam tabel **tickets**.

    - Setiap tiket otomatis berstatus **Menunggu**.

    - Operasi yang dilakukan adalah **INSERT**.

2. **ProcessTicketThread**

    - Thread ini berjalan dalam loop untuk memproses hingga 10 tiket.

    - **SELECT**: Mencari tiket yang berstatus **Menunggu**.

    - **UPDATE 1**: Jika ditemukan, status tiket diubah menjadi **Diproses** dan ada jeda waktu (simulasi pemrosesan).

   -  **UPDATE 2**: Setelah jeda, status diubah menjadi **Selesai**.

    - Jika **tidak ada tiket Menunggu** yang ditemukan, **thread akan jeda (sleep)** selama 3 detik sebelum mencari lagi.
   
## Hasil Screenshot Program
Output ini menunjukkan eksekusi empat thread yang berjalan secara bersamaan (paralel): tiga thread **GENERATOR** dan satu thread **PROCESSOR**. Awalnya, thread PROCESSOR mencari tugas dan melaporkan **Tidak ada antrian** karena data tiket belum sepenuhnya masuk. Setelah thread **GENERATOR** menyelesaikan tugasnya (melakukan operasi **INSERT**) dan memasukkan tiga data pelanggan (*Risa, Santi, Budi*) ke database, thread **PROCESSOR** langsung mengambil tiket-tiket tersebut (operasi **SELECT**) dan mengubah statusnya secara berurutan. Setelah menyelesaikan tiga tiket yang baru masuk, thread **PROCESSOR** kembali melaporkan **Tidak ada antrian** dan masuk ke mode sleep (jeda 3 detik) untuk menghemat sumber daya sambil terus memantau database untuk tiket baru yang mungkin masuk, menunjukkan logika sistem back-end yang dinamis dan efisien.

<img width="898" height="795" alt="Screenshot 2025-11-09 184605" src="https://github.com/user-attachments/assets/5115b78e-d703-493d-bd8b-6665e3724b18" />

   
## Hasil Akhir

Output program menunjukkan bagaimana thread **GENERATOR dan PROCESSOR** bekerja secara asinkron. **Tiket dibuat oleh GENERATOR** dan langsung diambil serta **diselesaikan oleh PROCESSOR** secara berurutan, menggambarkan sistem back-end yang dinamis.

