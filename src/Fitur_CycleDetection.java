import java.util.ArrayList;
import java.util.List;

public class Fitur_CycleDetection {
    public static void jalankan(CourseGraph graph) {
        System.out.println("\n=== DETEKSI SIKLUS (CYCLE DETECTION) ===");
        int[] state = new int[graph.size]; // 0: Unvisited, 1: Visiting, 2: Visited
        boolean hasCycle = false;
        
        // List untuk merekam jejak DFS dan menyimpan siklus yang ditemukan
        List<Integer> path = new ArrayList<>();
        List<Integer> cycleNodes = new ArrayList<>();

        for (int i = 0; i < graph.size; i++) {
            if (state[i] == 0) {
                if (dfsMaju(i, graph, state, path, cycleNodes)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        if (hasCycle) {
            System.out.println("[WARNING] Ditemukan siklus prasyarat! Ada error pada kurikulum.");
            System.out.println("\nRantai Mata Kuliah yang terjebak dalam siklus (Looping):");
            
            // Mencetak rute mata kuliah yang bermasalah
            for (int i = 0; i < cycleNodes.size(); i++) {
                int mkIndex = cycleNodes.get(i);
                System.out.print("[" + graph.courses[mkIndex].kode + "] " + graph.courses[mkIndex].nama);
                
                // Tambahkan tanda panah jika bukan elemen terakhir
                if (i < cycleNodes.size() - 1) {
                    System.out.print(" -> \n");
                }
            }
            System.out.println();
        } else {
            System.out.println("[AMAN] Tidak ditemukan siklus. Kurikulum valid.");
        }
    }

    private static boolean dfsMaju(int u, CourseGraph graph, int[] state, List<Integer> path, List<Integer> cycleNodes) {
        state[u] = 1; // Tandai sedang dikunjungi
        path.add(u);  // Simpan jejak saat ini

        AdjNode curr = graph.courses[u].adjList;

        while (curr != null) {
            int v = curr.destIndex;
            
            if (state[v] == 1) { // Siklus terdeteksi!
                // Ambil node-node yang membentuk siklus dari jejak (path)
                int startIndex = path.indexOf(v);
                for (int i = startIndex; i < path.size(); i++) {
                    cycleNodes.add(path.get(i));
                }
                cycleNodes.add(v); // Menutup siklus ke node awal untuk visualisasi
                return true;
            }
            
            if (state[v] == 0 && dfsMaju(v, graph, state, path, cycleNodes)) {
                return true;
            }
            curr = curr.next;
        }
        
        state[u] = 2; // Tandai selesai
        path.remove(path.size() - 1); // Backtrack: hapus jejak saat mundur dari rekursi
        return false;
    }
}