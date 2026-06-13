import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseGraph graph = new CourseGraph();
        graph.loadData();

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
                    System.out.println("(Fitur 2 belum diimplementasi)");
                    break;
                case 3:
                    graph.deleteMenu(sc);
                    break;
                case 4:
                    graph.displayGraph();
                    break;
                case 5:
                    System.out.print("Masukkan Kode MK: ");
                    String kode = sc.nextLine();
                    graph.dfs(kode);
                    break;
                case 6:
                    System.out.println("(Fitur 6 belum diimplementasi)");
                    break;
                case 7:
                    System.out.println("(Fitur 7 belum diimplementasi)");
                    break;
                case 8:
                    Fitur8_Prerequisites.menu(graph, sc);
                    break;
                case 9:
                    Fitur12_TrieTracing.menu(graph, sc);
                    break;
                case 10:
                    Fitur10_CycleDetection.menu(graph, sc);
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
