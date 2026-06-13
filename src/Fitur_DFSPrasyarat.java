public class Fitur_DFSPrasyarat {
    
    public static void cetak(String kodeTarget, CourseGraph graph) {
        int targetIdx = graph.findIndex(kodeTarget);
        if (targetIdx == -1) {
            System.out.println("Gagal: Kode MK tidak ditemukan.");
            return;
        }

        boolean[] visited = new boolean[graph.size];
        System.out.println("\nMenelusuri rantai prasyarat mundur untuk: " + graph.courses[targetIdx].nama);
        dfsMundur(targetIdx, graph, visited, 1);
    }

    private static void dfsMundur(int currentIdx, CourseGraph graph, boolean[] visited, int kedalaman) {
        visited[currentIdx] = true;
        
        // Cek semua mata kuliah, cari mana yang menunjuk ke currentIdx
        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            
            // Loop linked list adjacency milik node i
            while (curr != null) {
                // Jika node i menunjuk ke target kita (currentIdx)
                if (curr.destIndex == currentIdx && !visited[i]) {
                    System.out.println("   ".repeat(kedalaman) + "-> Wajib Lulus: [" + graph.courses[i].kode + "] " + graph.courses[i].nama);
                    
                    // Terus telusuri ke belakang
                    dfsMundur(i, graph, visited, kedalaman + 1);
                }
                curr = curr.next;
            }
        }
    }
}