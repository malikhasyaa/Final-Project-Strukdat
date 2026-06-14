import java.util.LinkedList;
import java.util.Queue;

public class Fitur_rekomendasibelajar {
    public static void jalankan(CourseGraph graph) {
        System.out.println("\n=== REKOMENDASI URUTAN BELAJAR (TOPOLOGICAL SORT) ===");
        int[] inDegree = new int[graph.size];
        
        // Hitung in-degree (jumlah prasyarat) untuk tiap mata kuliah
        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) {
                inDegree[curr.destIndex]++;
                curr = curr.next;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        // Masukkan semua MK yang tidak punya prasyarat (in-degree 0) ke queue
        for (int i = 0; i < graph.size; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        int urutan = 1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.println(urutan++ + ". [" + graph.courses[u].kode + "] " + graph.courses[u].nama);

            AdjNode curr = graph.courses[u].adjList;
            while (curr != null) {
                int v = curr.destIndex;
                inDegree[v]--; // Kurangi in-degree karena prasyarat sudah "lulus"
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
                curr = curr.next;
            }
        }
    }
}