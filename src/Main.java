import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseGraph graph = new CourseGraph();
        
        // 1. Inisialisasi Trie untuk fitur pencarian
        Trie trie = new Trie(); 
        
        // 2. DIUBAH: Menggunakan DataLoader buatanmu alih-alih graph.loadData() milik Viko
        Fitur_DataLoader.load("data/node.csv", "data/edge.csv", trie, graph);

        int pilih = -1;
        while (pilih != 0) {
            System.out.println("\n========================================");
            System.out.println("   LIBRARY KNOWLEDGE NAVIGATOR");
            System.out.println("========================================");
            System.out.println("1. Insert data (MK baru / relasi baru)");
            System.out.println("2. Search MK berdasarkan prefix");
            System.out.println("3. Update / Delete data");
            System.out.println("4. Tampilkan semua MK & Graph");
            System.out.println("5. DFS - Traversal rantai prasyarat");
            System.out.println("6. Topological Sort - Urutan belajar");
            System.out.println("7. Cycle Detection");
            System.out.println("8. Tampilkan Prasyarat Sebuah Topik");
            System.out.println("9. Tracing Proses Trie (Step-by-Step)");
            System.out.println("10. Deteksi Siklus Prasyarat");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            pilih = Integer.parseInt(sc.nextLine().trim());

            switch (pilih) {
                case 1:
                    graph.insertMenu(sc);
                    break;
                case 2:
                    // DIUBAH: Fitur Smart Path Navigator (Trie -> Graph)
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
                    // DIUBAH: Fitur DFS pencarian prasyarat mundur
                    System.out.print("Masukkan Kode MK: ");
                    String kode = sc.nextLine();
                    Fitur_DFSPrasyarat.cetak(kode, graph);
                    break;
                case 6:
                    System.out.println("(Fitur 6 belum diimplementasi)");
                    break;
                case 7:
                    System.out.println("(Fitur 7 belum diimplementasi)");
                    break;
                case 8:
                    Fitur_Prerequisites.menu(graph, sc);
                    break;
                case 9:
                    Fitur_TrieTracing.menu(graph, sc);
                    break;
                case 10:
                    Fitur_CycleDetection.menu(graph, sc);
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
