public class Fitur_CycleDetection {
    public static void jalankan(CourseGraph graph) {
        System.out.println("\n=== DETEKSI SIKLUS (CYCLE DETECTION) ===");
        int[] state = new int[graph.size]; // 0: Unvisited, 1: Visiting, 2: Visited
        boolean hasCycle = false;

        for (int i = 0; i < graph.size; i++) {
            if (state[i] == 0) {
                if (dfsMaju(i, graph, state)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        if (hasCycle) {
            System.out.println("[WARNING] Ditemukan siklus prasyarat! Ada error pada kurikulum.");
        } else {
            System.out.println("[AMAN] Tidak ditemukan siklus. Kurikulum valid.");
        }
    }

    private static boolean dfsMaju(int u, CourseGraph graph, int[] state) {
        state[u] = 1; // Tandai sedang dikunjungi
        AdjNode curr = graph.courses[u].adjList;

        while (curr != null) {
            int v = curr.destIndex;
            if (state[v] == 1) return true; // Siklus terdeteksi!
            if (state[v] == 0 && dfsMaju(v, graph, state)) return true;
            curr = curr.next;
        }
        state[u] = 2; // Tandai selesai
        return false;
    }
}