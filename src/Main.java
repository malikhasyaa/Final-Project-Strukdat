import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseGraph graph = new CourseGraph();
        
        // Inisialisasi Trie
        Trie trie = new Trie();

        // Memuat data Node dan Edge dari CSV
        Fitur_DataLoader.load("data/node.csv", "data/edge.csv", trie, graph);

        int pilih = -1;
        while (pilih != 0) {
            System.out.println("\n========================================");
            System.out.println("   LIBRARY KNOWLEDGE NAVIGATOR");
            System.out.println("========================================");
            System.out.println("1.  Insert data (MK baru / relasi baru)");
            System.out.println("2.  Search MK berdasarkan prefix");
            System.out.println("3.  Update / Delete data");
            System.out.println("4.  Tampilkan semua MK & Graph");
            System.out.println("5.  DFS - Traversal rantai prasyarat");
            System.out.println("6.  Topological Sort - Urutan belajar");
            System.out.println("7.  Cycle Detection (Deteksi Siklus)");
            System.out.println("8.  Topik Terisolasi (Bebas Prasyarat)");
            System.out.println("9.  Tracing Proses Trie (Simulasi Autocomplete)");
            System.out.println("10. Tracing Proses Graph (Simulasi DFS)");
            System.out.println("11. Tracing Edge Case (Kondisi Ekstrem)");
            System.out.println("0.  Keluar");
            System.out.print("Pilih menu: ");

            try {
                pilih = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                continue;
            }

            switch (pilih) {
                case 1:
                    graph.insertMenu(sc);
                    break;
                case 2:
                    System.out.print("Masukkan awalan topik (contoh: 'Sistem'): ");
                    String keyword = sc.nextLine();
                    Fitur_SmartPath.jalankan(keyword, trie, graph);
                    break;
                case 3:
                    graph.deleteMenu(sc);
                    break;
                case 4:
                    graph.displayGraph();
                    break;
                case 5:
                    System.out.print("Masukkan Kode MK (contoh: IF102): ");
                    String kode = sc.nextLine().toUpperCase();
                    Fitur_DFSPrasyarat.cetak(kode, graph);
                    break;
                case 6:
                    Fitur_rekomendasibelajar.jalankan(graph);
                    break;
                case 7:
                    Fitur_CycleDetection.jalankan(graph);
                    break;
                case 8:
                    Fitur_IsolatedTopik.jalankan(graph);
                    break;
                case 9:
                    Fitur_TrieTracing.cetak();
                    break;
                case 10:
                    Fitur_GraphTracing.cetak();
                    break;
                case 11:
                    Fitur_EdgeCaseTracing.cetak();
                    break;
                case 0:
                    System.out.println("Keluar. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        sc.close();
    }
}