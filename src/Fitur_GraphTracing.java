import java.util.*;

/**
 * FITUR 13A & 13B — TRACING PROSES GRAPH (STEP-BY-STEP)
 *
 * Sub-fitur:
 *   13A. Tracing DFS rekursif (rantai prasyarat)
 *   13B. Tracing Topological Sort — Kahn's Algorithm
 */
public class Fitur_GraphTracing {

    // ═══════════════════════════════════════════════════════════════
    //  FITUR 13A — TRACING DFS (DEPTH-FIRST SEARCH)
    // ═══════════════════════════════════════════════════════════════
    public static void tracingDFS(CourseGraph g, String startKode) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       FITUR 13A — TRACING DEPTH-FIRST SEARCH (DFS)          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        int startIdx = g.findIndex(startKode);
        if (startIdx == -1) {
            System.out.println("  [!] Kode MK \"" + startKode + "\" tidak ditemukan dalam graph.");
            System.out.println("══════════════════════════════════════════════════════════════\n");
            return;
        }

        System.out.println("  Start Node : " + startKode + " — " + g.courses[startIdx].nama);
        System.out.println("  Jenis Graph: Directed Graph (Adjacency List via AdjNode)");
        System.out.println("  Algoritma  : DFS Rekursif (penelusuran rantai MK lanjutan)");
        System.out.println("──────────────────────────────────────────────────────────────");
        System.out.println("  Step | Aksi         | Kode MK       | Nama Mata Kuliah");
        System.out.println("  -----|--------------|---------------|------------------------");

        boolean[] visited  = new boolean[g.size];
        int[]     stepCtr  = {0};
        List<String> urutan = new ArrayList<>();

        dfsBantu(g, startIdx, visited, stepCtr, urutan, 0);

        System.out.println("──────────────────────────────────────────────────────────────");
        System.out.println("  URUTAN KUNJUNGAN DFS:");
        System.out.print("  ");
        for (int i = 0; i < urutan.size(); i++) {
            System.out.print(urutan.get(i));
            if (i < urutan.size() - 1) System.out.print(" → ");
        }
        System.out.println();
        System.out.println("  Total node dikunjungi: " + urutan.size());
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }

    private static void dfsBantu(CourseGraph g, int u, boolean[] visited,
                                  int[] step, List<String> urutan, int depth) {
        visited[u] = true;
        step[0]++;
        urutan.add(g.courses[u].kode);

        String indent = "  ".repeat(depth);
        System.out.printf("  %4d | VISIT        | %-13s | %s%s%n",
            step[0], g.courses[u].kode, indent, g.courses[u].nama);

        AdjNode curr = g.courses[u].adjList;
        while (curr != null) {
            int v = curr.destIndex;
            if (!visited[v]) {
                step[0]++;
                System.out.printf("  %4d | PUSH/EXPLORE | %-13s | %s→ %s%n",
                    step[0], g.courses[v].kode, indent, g.courses[v].nama);
                dfsBantu(g, v, visited, step, urutan, depth + 1);
            } else {
                step[0]++;
                System.out.printf("  %4d | SUDAH VISIT  | %-13s | (skip — sudah dikunjungi)%n",
                    step[0], g.courses[v].kode);
            }
            curr = curr.next;
        }

        System.out.printf("  %4d | BACKTRACK    | %-13s | kembali ke level %d%n",
            ++step[0], g.courses[u].kode, depth);
    }

    // ═══════════════════════════════════════════════════════════════
    //  FITUR 13B — TRACING TOPOLOGICAL SORT (KAHN'S ALGORITHM)
    // ═══════════════════════════════════════════════════════════════
    public static void tracingTopologicalSort(CourseGraph g) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   FITUR 13B — TRACING TOPOLOGICAL SORT (KAHN'S ALGORITHM)   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  Jenis Graph: Directed Graph (Adjacency List via AdjNode)");
        System.out.println("  Algoritma  : Kahn's (BFS berbasis in-degree)");
        System.out.println("  Tujuan     : Urutan belajar yang valid (semua prasyarat terpenuhi)");
        System.out.println("──────────────────────────────────────────────────────────────");

        // Hitung in-degree setiap node
        int[] inDeg = new int[g.size];
        for (int u = 0; u < g.size; u++) {
            AdjNode curr = g.courses[u].adjList;
            while (curr != null) {
                inDeg[curr.destIndex]++;
                curr = curr.next;
            }
        }

        // Tampilkan in-degree awal
        System.out.println("  FASE 1: Hitung In-Degree Setiap Node");
        System.out.println("  No | Kode MK       | Nama MK                                    | In-Deg | Ket");
        System.out.println("  ---|---------------|--------------------------------------------|----|----------------------------");
        for (int i = 0; i < g.size; i++) {
            String ket = inDeg[i] == 0
                ? "← masuk antrian awal"
                : "tunggu " + inDeg[i] + " prasyarat";
            System.out.printf("  %2d | %-13s | %-42s | %6d | %s%n",
                i + 1, g.courses[i].kode, g.courses[i].nama, inDeg[i], ket);
        }

        // Inisialisasi antrian dengan node ber-in-degree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < g.size; i++)
            if (inDeg[i] == 0) queue.add(i);

        System.out.println("──────────────────────────────────────────────────────────────");
        System.out.println("  FASE 2: Proses BFS — Kahn's Algorithm");
        System.out.println("  Step | Dequeue       | Tetangga yang In-Degree-nya Berkurang");
        System.out.println("  -----|---------------|--------------------------------------");

        List<Integer> topoOrder = new ArrayList<>();
        int step = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            step++;
            topoOrder.add(u);

            System.out.printf("  %4d | DEQUEUE %-6s| ", step, g.courses[u].kode);

            AdjNode curr = g.courses[u].adjList;
            boolean adaTetangga = false;
            while (curr != null) {
                int v = curr.destIndex;
                inDeg[v]--;
                adaTetangga = true;
                System.out.printf("%s(in-deg→%d) ", g.courses[v].kode, inDeg[v]);
                if (inDeg[v] == 0) {
                    queue.add(v);
                    System.out.print("[→QUEUE] ");
                }
                curr = curr.next;
            }
            if (!adaTetangga) System.out.print("(tidak ada tetangga keluar)");
            System.out.println();
        }

        System.out.println("──────────────────────────────────────────────────────────────");
        System.out.println("  HASIL — Urutan Belajar yang Valid:");
        System.out.println("  No | Kode MK       | Nama Mata Kuliah                            | Smt");
        System.out.println("  ---|---------------|---------------------------------------------|----");
        for (int i = 0; i < topoOrder.size(); i++) {
            int idx = topoOrder.get(i);
            System.out.printf("  %2d | %-13s | %-43s | %d%n",
                i + 1,
                g.courses[idx].kode,
                g.courses[idx].nama,
                g.courses[idx].semester);
        }

        System.out.println("──────────────────────────────────────────────────────────────");
        if (topoOrder.size() != g.size) {
            System.out.println("  [!] PERINGATAN: Hanya " + topoOrder.size() +
                " dari " + g.size + " node terproses!");
            System.out.println("  [!] Kemungkinan ada SIKLUS (cycle) dalam graph.");
        } else {
            System.out.println("  [✓] Semua " + g.size + " mata kuliah berhasil diurutkan.");
            System.out.println("  [✓] Graph bebas siklus — urutan belajar valid.");
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }

    // ═══════════════════════════════════════════════════════════════
    //  MENU — dipanggil dari Main.java
    //  Cara pakai: Fitur_GraphTracing.menu(graph, sc);
    // ═══════════════════════════════════════════════════════════════
    public static void menu(CourseGraph graph, Scanner sc) {
        boolean balik = false;
        while (!balik) {
            System.out.println("\n══════════════════════════════════════════════════════════");
            System.out.println("      FITUR 13 — TRACING PROSES GRAPH (STEP-BY-STEP)");
            System.out.println("══════════════════════════════════════════════════════════");
            System.out.println("  1. Tracing DFS dari suatu Mata Kuliah");
            System.out.println("  2. Tracing Topological Sort (Kahn's Algorithm)");
            System.out.println("  3. Demo Gabungan (DFS + Topological Sort)");
            System.out.println("  0. Kembali ke menu utama");
            System.out.print("  Pilih: ");

            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input tidak valid, masukkan angka.");
                continue;
            }

            switch (pilih) {
                case 1:
                    System.out.print("  Masukkan Kode MK (contoh: ET234103): ");
                    String kodeInput = sc.nextLine().trim().toUpperCase();
                    tracingDFS(graph, kodeInput);
                    break;
                case 2:
                    tracingTopologicalSort(graph);
                    break;
                case 3:
                    System.out.println("\n  [DEMO] DFS dari ET234103 (Algoritma dan Teknik Pemrograman)");
                    tracingDFS(graph, "ET234103");
                    System.out.println("\n  [DEMO] Topological Sort seluruh kurikulum");
                    tracingTopologicalSort(graph);
                    break;
                case 0:
                    balik = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }
}
