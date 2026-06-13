import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // =========================================================================
    // FITUR 2: DATA LOADER (Membaca file CSV dan memasukkannya ke Trie & Graph)
    // =========================================================================
    public static void loadDataset(String filePathNode, String filePathEdge, Trie trie, Graph graph, Map<Integer, String> courseMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePathNode))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int indexId = Integer.parseInt(data[0]); 
                String namaMk = data[2]; 

                courseMap.put(indexId, namaMk);
                trie.insert(namaMk.toLowerCase(), indexId);
            }
            System.out.println("[OK] Berhasil memuat 25 Course ke dalam Trie.");
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca file node: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePathEdge))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int prasyaratId = Integer.parseInt(data[0]);
                int lanjutanId = Integer.parseInt(data[1]);
                
                graph.addEdge(prasyaratId, lanjutanId);
            }
            System.out.println("[OK] Berhasil memuat 41 relasi edge ke dalam Graph.");
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca file edge: " + e.getMessage());
        }
    }


    // =========================================================================
    // FITUR 4: DFS PRASYARAT (Menampilkan urutan ke belakang dari mata kuliah)
    // =========================================================================
    public static void cetakChainPrasyarat(int courseIndex, Graph graph, Map<Integer, String> courseMap) {
        boolean[] visited = new boolean[graph.getVertexCount()];
        System.out.println("\nMenelusuri rantai prasyarat untuk: " + courseMap.get(courseIndex));
        dfsPrasyarat(courseIndex, graph, visited, courseMap, 1);
    }

    private static void dfsPrasyarat(int currentId, Graph graph, boolean[] visited, Map<Integer, String> courseMap, int kedalaman) {
        visited[currentId] = true;
        
        for (int prasyaratId : graph.getIncomingEdges(currentId)) {
            if (!visited[prasyaratId]) {
                System.out.println("   ".repeat(kedalaman) + "-> Wajib Lulus: " + courseMap.get(prasyaratId));
                dfsPrasyarat(prasyaratId, graph, visited, courseMap, kedalaman + 1);
            }
        }
    }


    // =========================================================================
    // FITUR 6: SMART PATH NAVIGATOR (Gabungan Trie dan DFS Graph)
    // =========================================================================
    public static void smartPathNavigator(String keyword, Trie trie, Graph graph, Map<Integer, String> courseMap) {
        System.out.println("\n=== SMART PATH NAVIGATOR ===");
        
        List<Integer> hasilPencarian = trie.autocomplete(keyword.toLowerCase());

        if (hasilPencarian.isEmpty()) {
            System.out.println("Topik tidak ditemukan di database.");
            return;
        }

        int targetCourseId = hasilPencarian.get(0);
        System.out.println("Ditemukan kecocokan: " + courseMap.get(targetCourseId));
        
        cetakChainPrasyarat(targetCourseId, graph, courseMap);
    }


    // =========================================================================
    // FUNGSI UTAMA (Menu Interaktif Terminal / CLI)
    // =========================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Trie trie = new Trie(); 
        Graph graph = new Graph(25); 
        Map<Integer, String> courseMap = new HashMap<>();

        // 1. Load Data Otomatis Saat Program Berjalan
        loadDataset("data/node.csv", "data/edge.csv", trie, graph, courseMap);

        int menu = 0;
        do {
            System.out.println("\n--- MENU LIBRARY KNOWLEDGE NAVIGATOR ---");
            System.out.println("1. Cari Topik & Lihat Prasyarat (Smart Path)");
            System.out.println("2. Keluar");
            System.out.print("Pilih menu: ");
            menu = scanner.nextInt();
            scanner.nextLine(); 

            switch (menu) {
                case 1:
                    System.out.print("Masukkan awalan atau nama topik (contoh: 'Sistem'): ");
                    String keyword = scanner.nextLine();
                    smartPathNavigator(keyword, trie, graph, courseMap);
                    break;
                case 2:
                    System.out.println("Keluar dari program...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (menu != 2);
        
        scanner.close();
    }
}