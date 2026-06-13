import java.io.BufferedReader;
import java.io.FileReader;

public class Fitur_DataLoader {
    
    // Asumsi class Trie sudah dibuat oleh anggota lain (Tree Dev)
    public static void load(String fileNode, String fileEdge, Trie trie, CourseGraph graph) {
        // 1. Membaca file Node (Mata Kuliah)
        try (BufferedReader br = new BufferedReader(new FileReader(fileNode))) {
            String line;
            br.readLine(); // Melewati baris judul kolom di CSV
            while ((line = br.readLine()) != null) {
                // Asumsi format CSV: KODE,NAMA,KATEGORI,LEVEL,SKS,EST_JAM,SEMESTER
                String[] data = line.split(",");
                String kode = data[0].trim();
                String nama = data[1].trim();
                String kategori = data[2].trim();
                String level = data[3].trim();
                int sks = Integer.parseInt(data[4].trim());
                int estJam = Integer.parseInt(data[5].trim());
                int semester = Integer.parseInt(data[6].trim());

                // Masukkan ke Graph Viko
                graph.addCourse(kode, nama, kategori, level, sks, estJam, semester);
                
                // Masukkan nama ke Trie beserta index-nya di array Viko
                int indexDiGraph = graph.findIndex(kode);
                if(trie != null) {
                    trie.insert(nama.toLowerCase(), indexDiGraph);
                }
            }
            System.out.println("[OK] Dataset Mata Kuliah dimuat ke Graph & Trie.");
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal load node: " + e.getMessage());
        }

        // 2. Membaca file Edge (Relasi Prasyarat)
        try (BufferedReader br = new BufferedReader(new FileReader(fileEdge))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String dariKode = data[0].trim(); // Prasyarat
                String keKode = data[1].trim();   // Lanjutan
                
                graph.addEdge(dariKode, keKode);
            }
            System.out.println("[OK] Dataset Relasi Prasyarat dimuat.");
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal load edge: " + e.getMessage());
        }
    }
}