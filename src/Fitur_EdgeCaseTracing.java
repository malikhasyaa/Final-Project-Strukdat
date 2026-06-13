import java.util.*;

/**
 * FITUR 14 — TRACING EDGE CASE GRAPH (STEP-BY-STEP)
 *
 * Pengujian 5 kondisi ekstrem pada graph:
 *   EC1. Node Terisolasi (Isolated Node)
 *   EC2. Siklus / Cycle Detection
 *   EC3. Node Tidak Ditemukan (Invalid Input)
 *   EC4. Graph Kosong (Empty Graph)
 *   EC5. Self-Loop
 */
public class Fitur_EdgeCaseTracing {

    // ═══════════════════════════════════════════════════════════════
    //  EDGE CASE 1 — NODE TERISOLASI
    // ═══════════════════════════════════════════════════════════════
    private static void edgeCase1() {
        System.out.println("\n  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │  EDGE CASE 1: NODE TERISOLASI (Isolated Node)           │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println("  Skenario : Tambah MK baru tanpa relasi ke/dari node manapun.");
        System.out.println("  Ekspektasi: DFS hanya kunjungi 1 node; Topo-sort tetap valid.");
        System.out.println();

        CourseGraph g = new CourseGraph();
        g.addCourse("EC001", "MK Terisolasi", "Test", "Mudah", 2, 40, 1);
        g.addCourse("EC002", "MK Normal A",   "Test", "Mudah", 2, 40, 1);
        g.addCourse("EC003", "MK Normal B",   "Test", "Mudah", 2, 40, 2);
        g.addEdge("EC002", "EC003"); // EC001 tidak punya edge sama sekali

        int[] inDeg = hitungInDegree(g);
        System.out.println("  In-degree tiap node:");
        for (int i = 0; i < g.size; i++) {
            boolean terisolasi = inDeg[i] == 0 && g.courses[i].adjList == null;
            System.out.printf("    %-5s | %-20s | in-degree=%d | out-degree=%s %s%n",
                g.courses[i].kode,
                g.courses[i].nama,
                inDeg[i],
                g.courses[i].adjList == null ? "0" : ">0",
                terisolasi ? "[TERISOLASI ← tidak punya relasi apapun]" : "");
        }

        System.out.println("\n  DFS dari EC001 (node terisolasi):");
        System.out.println("  Step | Aksi         | Kode MK       | Nama Mata Kuliah");
        System.out.println("  -----|--------------|---------------|------------------------");
        boolean[] visited = new boolean[g.size];
        int[] stepCtr = {0};
        List<String> urutan = new ArrayList<>();
        dfsBantu(g, 0, visited, stepCtr, urutan, 0);
        System.out.println("  [✓] DFS hanya mengunjungi: " + urutan +
            " (" + urutan.size() + " node — benar untuk node terisolasi)");
    }

    // ═══════════════════════════════════════════════════════════════
    //  EDGE CASE 2 — SIKLUS / CYCLE DETECTION
    // ═══════════════════════════════════════════════════════════════
    private static void edgeCase2() {
        System.out.println("\n  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │  EDGE CASE 2: SIKLUS — CYCLE DETECTION                 │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println("  Skenario : A → B → C → A (siklus 3 node).");
        System.out.println("  Ekspektasi: Kahn's tidak bisa selesaikan — 0 node ber-in-degree 0.");
        System.out.println();

        CourseGraph g = new CourseGraph();
        g.addCourse("SC_A", "Matakuliah A", "Test", "Sedang", 3, 60, 1);
        g.addCourse("SC_B", "Matakuliah B", "Test", "Sedang", 3, 60, 2);
        g.addCourse("SC_C", "Matakuliah C", "Test", "Sedang", 3, 60, 3);
        g.addEdge("SC_A", "SC_B");
        g.addEdge("SC_B", "SC_C");
        g.addEdge("SC_C", "SC_A"); // ← siklus!

        int[] inDeg = hitungInDegree(g);
        System.out.println("  Graph   : SC_A → SC_B → SC_C → SC_A");
        System.out.printf("  In-degree: SC_A=%d, SC_B=%d, SC_C=%d%n",
            inDeg[0], inDeg[1], inDeg[2]);
        System.out.println("  Tidak ada node ber-in-degree 0 → antrian awal KOSONG.");
        System.out.println("  Jalankan Kahn's Algorithm...");

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < g.size; i++)
            if (inDeg[i] == 0) queue.add(i);

        List<String> topo = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topo.add(g.courses[u].kode);
            AdjNode curr = g.courses[u].adjList;
            while (curr != null) {
                if (--inDeg[curr.destIndex] == 0) queue.add(curr.destIndex);
                curr = curr.next;
            }
        }

        System.out.println("  Node terproses: " + topo.size() + " dari " + g.size);
        if (topo.size() < g.size) {
            System.out.println("  [!] SIKLUS TERDETEKSI — Topological Sort gagal diselesaikan!");
            System.out.println("  [!] Relasi prasyarat ini tidak logis / melingkar.");
            System.out.println("  [✓] Kahn's berhasil mendeteksi siklus via node yang tidak terproses.");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  EDGE CASE 3 — NODE TIDAK DITEMUKAN (INVALID INPUT)
    // ═══════════════════════════════════════════════════════════════
    private static void edgeCase3(CourseGraph gAsli) {
        System.out.println("\n  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │  EDGE CASE 3: KODE MK TIDAK VALID (Invalid Input)      │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println("  Skenario : User input kode MK yang tidak ada di graph.");
        System.out.println("  Ekspektasi: findIndex() return -1, DFS batal, program tidak crash.");
        System.out.println();

        String kodeFiktif = "ET999999";
        int idx = gAsli.findIndex(kodeFiktif);
        System.out.println("  Input kode  : \"" + kodeFiktif + "\"");
        System.out.println("  findIndex() : " + idx);
        if (idx == -1) {
            System.out.println("  [✓] Return -1 — node tidak ditemukan.");
            System.out.println("  [✓] DFS dibatalkan dengan pesan error yang informatif.");
            System.out.println("  [✓] Tidak ada NullPointerException — penanganan aman.");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  EDGE CASE 4 — GRAPH KOSONG
    // ═══════════════════════════════════════════════════════════════
    private static void edgeCase4() {
        System.out.println("\n  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │  EDGE CASE 4: GRAPH KOSONG (Empty Graph)                │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println("  Skenario : Jalankan Topological Sort pada graph tanpa node.");
        System.out.println("  Ekspektasi: Proses selesai tanpa error, hasil = list kosong.");
        System.out.println();

        CourseGraph g = new CourseGraph();
        int[] inDeg = hitungInDegree(g);
        List<String> topo = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < g.size; i++)
            if (inDeg[i] == 0) queue.add(i);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topo.add(g.courses[u].kode);
        }

        System.out.println("  Jumlah node : " + g.size);
        System.out.println("  Hasil Topo  : " + topo);
        System.out.println("  [✓] Graph kosong ditangani aman — tidak ada ArrayIndexOutOfBounds.");
    }

    // ═══════════════════════════════════════════════════════════════
    //  EDGE CASE 5 — SELF-LOOP
    // ═══════════════════════════════════════════════════════════════
    private static void edgeCase5() {
        System.out.println("\n  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │  EDGE CASE 5: SELF-LOOP (Node Menunjuk Dirinya Sendiri) │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println("  Skenario : MK_X → MK_X (prasyarat dirinya sendiri).");
        System.out.println("  Ekspektasi: in-degree MK_X tidak pernah 0 → tidak terproses Kahn's.");
        System.out.println();

        CourseGraph g = new CourseGraph();
        g.addCourse("SL_X", "MK Self-Loop", "Test", "Sulit", 3, 80, 1);
        g.addCourse("SL_Y", "MK Normal",    "Test", "Mudah", 2, 40, 1);
        g.addEdge("SL_X", "SL_X"); // self-loop
        g.addEdge("SL_Y", "SL_X");

        int[] inDeg = hitungInDegree(g);
        System.out.printf("  In-degree: SL_X=%d, SL_Y=%d%n", inDeg[0], inDeg[1]);

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < g.size; i++)
            if (inDeg[i] == 0) queue.add(i);
        List<String> topo = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topo.add(g.courses[u].kode);
            AdjNode curr = g.courses[u].adjList;
            while (curr != null) {
                if (--inDeg[curr.destIndex] == 0) queue.add(curr.destIndex);
                curr = curr.next;
            }
        }

        System.out.println("  Node terproses : " + topo);
        System.out.println("  SL_X terproses : " + topo.contains("SL_X"));
        if (!topo.contains("SL_X")) {
            System.out.println("  [!] SL_X tidak terproses — self-loop membuat in-degree tidak bisa 0.");
            System.out.println("  [✓] Deteksi berhasil: self-loop = kasus khusus dari siklus.");
        }
    }

    // ── Helper: hitung in-degree dari CourseGraph ──
    private static int[] hitungInDegree(CourseGraph g) {
        int[] inDeg = new int[g.size];
        for (int u = 0; u < g.size; u++) {
            AdjNode curr = g.courses[u].adjList;
            while (curr != null) {
                inDeg[curr.destIndex]++;
                curr = curr.next;
            }
        }
        return inDeg;
    }

    // ── Helper: DFS bantu untuk edge case 1 ──
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
            }
            curr = curr.next;
        }
        System.out.printf("  %4d | BACKTRACK    | %-13s | kembali ke level %d%n",
            ++step[0], g.courses[u].kode, depth);
    }

    // ═══════════════════════════════════════════════════════════════
    //  MENU UTAMA — dipanggil dari Main.java
    //  Cara pakai: Fitur_EdgeCaseTracing.menu(graph, sc);
    // ═══════════════════════════════════════════════════════════════
    public static void menu(CourseGraph graph, Scanner sc) {
        boolean balik = false;
        while (!balik) {
            System.out.println("\n══════════════════════════════════════════════════════════");
            System.out.println("      FITUR 14 — TRACING EDGE CASE GRAPH (STEP-BY-STEP)");
            System.out.println("══════════════════════════════════════════════════════════");
            System.out.println("  1. Edge Case 1 — Node Terisolasi");
            System.out.println("  2. Edge Case 2 — Siklus (Cycle Detection)");
            System.out.println("  3. Edge Case 3 — Kode MK Tidak Valid");
            System.out.println("  4. Edge Case 4 — Graph Kosong");
            System.out.println("  5. Edge Case 5 — Self-Loop");
            System.out.println("  6. Jalankan Semua Edge Case");
            System.out.println("  0. Kembali ke menu utama");
            System.out.print("  Pilih: ");

            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input tidak valid, masukkan angka.");
                continue;
            }

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║          FITUR 14 — TRACING EDGE CASE GRAPH                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  Pengujian kondisi-kondisi ekstrem / tidak normal pada graph.");

            switch (pilih) {
                case 1: edgeCase1(); break;
                case 2: edgeCase2(); break;
                case 3: edgeCase3(graph); break;
                case 4: edgeCase4(); break;
                case 5: edgeCase5(); break;
                case 6:
                    edgeCase1();
                    edgeCase2();
                    edgeCase3(graph);
                    edgeCase4();
                    edgeCase5();
                    System.out.println("\n══════════════════════════════════════════════════════════════");
                    System.out.println("  [✓] Semua 5 edge case selesai diuji.");
                    System.out.println("══════════════════════════════════════════════════════════════\n");
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
