public class Fitur_IsolatedTopik {
    public static void jalankan(CourseGraph graph) {
        System.out.println("\n=== TOPIK TERISOLASI (BEBAS DIAMBIL KAPAN SAJA) ===");
        int[] inDegree = new int[graph.size];
        int[] outDegree = new int[graph.size];

        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) {
                outDegree[i]++;
                inDegree[curr.destIndex]++;
                curr = curr.next;
            }
        }

        boolean found = false;
        for (int i = 0; i < graph.size; i++) {
            if (inDegree[i] == 0 && outDegree[i] == 0) {
                System.out.println("- [" + graph.courses[i].kode + "] " + graph.courses[i].nama);
                found = true;
            }
        }
        
        if (!found) System.out.println("Tidak ada topik terisolasi.");
    }
}