import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fitur_SmartPath {
    
    public static void jalankan(String keyword, Trie trie, CourseGraph graph) {
        System.out.println("\n=== SMART PATH NAVIGATOR ===");
        
        if (trie == null) {
            System.out.println("Sistem Trie belum diaktifkan oleh Tree Developer.");
            return;
        }

        String searchKey = keyword.toLowerCase();
        
        // 1. Memanggil Trie untuk pencarian awalan
        List<Integer> hasilTrie = trie.autocomplete(searchKey);
        
        // 2. Membuat list final untuk menggabungkan hasil
        List<Integer> hasilFinal = new ArrayList<>();
        
        if (hasilTrie != null) {
            hasilFinal.addAll(hasilTrie);
        }

        // 3. Scan tambahan (Linear Search) untuk mencari keyword di TENGAH kalimat
        for (int i = 0; i < graph.size; i++) {
            String namaMk = graph.courses[i].nama.toLowerCase();
            
            if (namaMk.contains(searchKey) && !hasilFinal.contains(i)) {
                hasilFinal.add(i);
            }
        }

        if (hasilFinal.isEmpty()) {
            System.out.println("Topik tidak ditemukan di database.");
            return;
        }

        System.out.println("Ditemukan " + hasilFinal.size() + " kecocokan mata kuliah:");
        
        // Mencetak hasil dengan penomoran urut
        for (int i = 0; i < hasilFinal.size(); i++) {
            int idx = hasilFinal.get(i);
            CourseNode mk = graph.courses[idx];
            System.out.println((i + 1) + ". [" + mk.kode + "] " + mk.nama);
        }
        
        int pilihanIndex = 0; // Default ke elemen pertama
        
        // Jika hasilnya lebih dari 1, minta user memilih
        if (hasilFinal.size() > 1) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\nPilih nomor mata kuliah untuk melihat pohon prasyaratnya (1-" + hasilFinal.size() + "): ");
            try {
                String input = sc.nextLine().trim();
                int pilihan = Integer.parseInt(input);
                
                // Validasi pilihan agar tidak error OutOfBounds
                if (pilihan >= 1 && pilihan <= hasilFinal.size()) {
                    pilihanIndex = pilihan - 1; // Konversi ke index array (mulai dari 0)
                } else {
                    System.out.println("[!] Pilihan di luar rentang. Menampilkan opsi pertama secara default.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Input bukan angka valid. Menampilkan opsi pertama secara default.");
            }
            // Catatan: Scanner lokal ini sengaja TIDAK di-close (sc.close()) agar tidak 
            // mematikan System.in yang sedang dipakai oleh looping Menu di Main.java.
        }
        
        // Ambil data MK berdasarkan index yang dipilih
        int targetCourseId = hasilFinal.get(pilihanIndex);
        String targetKode = graph.courses[targetCourseId].kode;
        String targetNama = graph.courses[targetCourseId].nama;
        
        System.out.println("\nMenelusuri rantai prasyarat mundur untuk: " + targetNama);
        Fitur_DFSPrasyarat.cetak(targetKode, graph);
    }
}