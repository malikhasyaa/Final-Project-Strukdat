public class Fitur_IsolatedTopik {

    public static void menu(CourseGraph graph, java.util.Scanner sc) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     FITUR 11 — TOPIK TIDAK TERHUBUNG (ISOLATED)     ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("──────────────────────────────────────────────────────");

        // Hitung in-degree semua node
        int[] inDegree = new int[graph.size];
        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) {
                inDegree[curr.destIndex]++;
                curr = curr.next;
            }
        }

        boolean adaIsolated = false;
        int nomor = 1;

        for (int i = 0; i < graph.size; i++) {
            // Hitung out-degree
            int outDegree = 0;
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) { outDegree++; curr = curr.next; }

            if (inDegree[i] == 0 && outDegree == 0) {
                if (!adaIsolated) {
                    // Header baru ditampilkan saat ada isolated pertama
                    System.out.printf("  %-4s | %-12s | %-42s | %-10s | Smt%n",
                        "No", "Kode MK", "Nama Mata Kuliah", "Kategori");
                    System.out.println("  -----|--------------|------------------------------------------|------------|----");
                    adaIsolated = true;
                }
                System.out.printf("  %-4d | %-12s | %-42s | %-10s | %d%n",
                    nomor++,
                    graph.courses[i].kode,
                    graph.courses[i].nama,
                    graph.courses[i].kategori,
                    graph.courses[i].semester);
            }
        }

        System.out.println("──────────────────────────────────────────────────────");
        if (!adaIsolated) {
            System.out.println("  [✓] Tidak ada topik terisolasi dalam dataset ini.");
            System.out.println("      Semua " + graph.size + " MK memiliki minimal satu relasi prasyarat.");
        } else {
            System.out.println("  >> " + (nomor - 1) + " topik terisolasi ditemukan.");
            System.out.println("  [i] MK ini bisa diambil kapan saja tanpa syarat apapun");
            System.out.println("      dan tidak menjadi syarat MK lain.");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }
}