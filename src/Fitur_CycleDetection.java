public class Fitur_CycleDetection {

    static final int UNVISITED = 0;
    static final int VISITING  = 1;
    static final int VISITED   = 2;

    private static String siklus_dari = "";
    private static String siklus_ke   = "";

    private static boolean dfsDetect(CourseGraph graph, int idx, int[] state) {
        state[idx] = VISITING;

        AdjNode curr = graph.courses[idx].adjList;
        while (curr != null) {
            int neighbor = curr.destIndex;

            if (state[neighbor] == VISITING) {
                siklus_dari = graph.courses[idx].kode;
                siklus_ke   = graph.courses[neighbor].kode;
                return true;
            }

            if (state[neighbor] == UNVISITED) {
                if (dfsDetect(graph, neighbor, state)) return true;
            }

            curr = curr.next;
        }

        state[idx] = VISITED;
        return false;
    }

    public static boolean deteksiSiklus(CourseGraph graph) {
        int[] state = new int[graph.size];
        siklus_dari = "";
        siklus_ke   = "";

        for (int i = 0; i < graph.size; i++) {
            if (state[i] == UNVISITED) {
                if (dfsDetect(graph, i, state)) return true;
            }
        }
        return false;
    }

    public static void jalankanDeteksi(CourseGraph graph) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║        FITUR 10 — CYCLE DETECTION (DFS 3-STATE)     ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Metode : DFS dengan state Unvisited / Visiting / Visited");
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("\n  [*] Memulai DFS scan pada seluruh " + graph.size + " node...\n");

        boolean adaSiklus = deteksiSiklusDenganLog(graph);

        System.out.println("\n──────────────────────────────────────────────────────");
        if (!adaSiklus) {
            System.out.println("  ✓  HASIL: TIDAK ADA SIKLUS");
            System.out.println("     Graph adalah DAG yang valid. Topological Sort aman dijalankan.");
        } else {
            System.out.println("  ✗  HASIL: SIKLUS TERDETEKSI!");
            System.out.println("\n  >> Back-edge ditemukan:");
            System.out.println("     [" + siklus_dari + "] → [" + siklus_ke + "]");

            int idxDari = graph.findIndex(siklus_dari);
            int idxKe   = graph.findIndex(siklus_ke);
            if (idxDari != -1 && idxKe != -1) {
                System.out.println("     " + graph.courses[idxDari].nama
                                   + "  →  " + graph.courses[idxKe].nama);
            }
            System.out.println("\n  SOLUSI : Hapus edge [" + siklus_dari + " → " + siklus_ke + "]");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }

    private static boolean deteksiSiklusDenganLog(CourseGraph graph) {
        int[] state = new int[graph.size];
        String[] label = {"UNVISITED", "VISITING ", "VISITED  "};
        siklus_dari = "";
        siklus_ke   = "";

        System.out.println("  No | Kode MK    | Transisi State       | Keterangan");
        System.out.println("  ---|------------|----------------------|---------------------------");

        for (int i = 0; i < graph.size; i++) {
            if (state[i] == UNVISITED) {
                System.out.printf("  %2d | %-10s | %-9s → %-9s| Mulai DFS%n",
                    i + 1, graph.courses[i].kode, label[UNVISITED], label[VISITING]);
                if (dfsDetectLog(graph, i, state, label)) return true;
            }
        }
        return false;
    }

    private static boolean dfsDetectLog(CourseGraph graph, int idx,
                                         int[] state, String[] label) {
        state[idx] = VISITING;

        AdjNode curr = graph.courses[idx].adjList;
        while (curr != null) {
            int nb = curr.destIndex;

            if (state[nb] == VISITING) {
                System.out.printf("  !! | %-10s | BACK-EDGE DETECTED   | SIKLUS ke [%s]%n",
                    graph.courses[idx].kode, graph.courses[nb].kode);
                siklus_dari = graph.courses[idx].kode;
                siklus_ke   = graph.courses[nb].kode;
                return true;
            }

            if (state[nb] == UNVISITED) {
                System.out.printf("     | %-10s | %-9s → %-9s| Dari [%s]%n",
                    graph.courses[nb].kode, label[UNVISITED], label[VISITING],
                    graph.courses[idx].kode);
                if (dfsDetectLog(graph, nb, state, label)) return true;
            }

            curr = curr.next;
        }

        System.out.printf("     | %-10s | %-9s → %-9s| Selesai diproses%n",
            graph.courses[idx].kode, label[VISITING], label[VISITED]);
        state[idx] = VISITED;
        return false;
    }

    public static void menu(CourseGraph graph, java.util.Scanner sc) {
        System.out.println("\n=== FITUR 10 — CYCLE DETECTION ===");
        System.out.println("  1. Deteksi siklus pada graph saat ini");
        System.out.println("  2. Simulasi: tambah edge lalu cek siklus");
        System.out.print("  Pilih: ");

        int pilih;
        try {
            pilih = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Input tidak valid.");
            return;
        }

        if (pilih == 1) {
            jalankanDeteksi(graph);

        } else if (pilih == 2) {
            System.out.print("  Kode MK prasyarat (dari) : ");
            String dari = sc.nextLine().trim().toUpperCase();
            System.out.print("  Kode MK tujuan    (ke)   : ");
            String ke   = sc.nextLine().trim().toUpperCase();

            if (graph.findIndex(dari) == -1 || graph.findIndex(ke) == -1) {
                System.out.println("  [!] Salah satu kode MK tidak ditemukan.");
                return;
            }

            graph.addEdge(dari, ke);
            System.out.println("\n  [+] Edge sementara [" + dari + " → " + ke + "] ditambahkan.");

            boolean ada = deteksiSiklus(graph);
            if (ada) {
                System.out.println("  ✗  SIKLUS TERDETEKSI! Edge ini menyebabkan loop.");
                System.out.println("     Back-edge: [" + siklus_dari + "] → [" + siklus_ke + "]");
                System.out.println("  [~] Edge dibatalkan dan dihapus.");
                graph.removeEdge(dari, ke);
            } else {
                System.out.println("  ✓  Aman! Tidak ada siklus. Edge berhasil ditambahkan.");
            }

        } else {
            System.out.println("  [!] Pilihan tidak valid.");
        }
    }
}
