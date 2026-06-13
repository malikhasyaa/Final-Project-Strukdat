public class Fitur_RekomendasiBelajar {

    public static void menu(CourseGraph graph, java.util.Scanner sc) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║   FITUR 9 — REKOMENDASI URUTAN BELAJAR              ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Urutan dari MK tanpa prasyarat hingga MK tingkat lanjut.");
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

        // Queue manual pakai array (konsisten dengan CourseGraph — tanpa java.util)
        int[] queue = new int[graph.size];
        int front = 0, rear = 0;

        for (int i = 0; i < graph.size; i++) {
            if (inDegree[i] == 0) queue[rear++] = i;
        }

        int urutan = 1;
        int totalDiproses = 0;

        System.out.printf("  %-4s | %-12s | %-42s | %-10s | Smt%n",
            "No", "Kode MK", "Nama Mata Kuliah", "Level");
        System.out.println("  -----|--------------|------------------------------------------|------------|----");

        while (front < rear) {
            int curr = queue[front++];
            totalDiproses++;

            System.out.printf("  %-4d | %-12s | %-42s | %-10s | %d%n",
                urutan++,
                graph.courses[curr].kode,
                graph.courses[curr].nama,
                graph.courses[curr].level,
                graph.courses[curr].semester);

            AdjNode adj = graph.courses[curr].adjList;
            while (adj != null) {
                inDegree[adj.destIndex]--;
                if (inDegree[adj.destIndex] == 0) {
                    queue[rear++] = adj.destIndex;
                }
                adj = adj.next;
            }
        }

        System.out.println("──────────────────────────────────────────────────────");
        if (totalDiproses < graph.size) {
            System.out.println("  [!] PERINGATAN: " + (graph.size - totalDiproses) +
                " MK tidak bisa diurutkan karena ada SIKLUS!");
            System.out.println("      Jalankan Fitur 10 (Cycle Detection) untuk detail.");
        } else {
            System.out.println("  [✓] Semua " + totalDiproses + " mata kuliah berhasil diurutkan.");
            System.out.println("  [✓] Graph bebas siklus — urutan belajar valid.");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }
}