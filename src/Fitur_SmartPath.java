import java.util.List;

public class Fitur_SmartPath {
    
    public static void jalankan(String keyword, Trie trie, CourseGraph graph) {
        System.out.println("\n=== SMART PATH NAVIGATOR ===");
        
        if (trie == null) {
            System.out.println("Sistem Trie belum diaktifkan oleh Tree Developer.");
            return;
        }

        List<Integer> hasilPencarian = trie.autocomplete(keyword.toLowerCase());

        if (hasilPencarian == null || hasilPencarian.isEmpty()) {
            System.out.println("Topik tidak ditemukan di database.");
            return;
        }

        System.out.println("Ditemukan " + hasilPencarian.size() + " kecocokan mata kuliah:");
        for (int idx : hasilPencarian) {
            CourseNode mk = graph.courses[idx];
            System.out.println("- [" + mk.kode + "] " + mk.nama);
        }
        
        // Ambil hasil teratas dan lempar ke algoritma DFS Prasyarat
        int targetCourseId = hasilPencarian.get(0);
        String targetKode = graph.courses[targetCourseId].kode;
        
        System.out.println("\nMenampilkan pohon prasyarat untuk hasil teratas:");
        Fitur_DFSPrasyarat.cetak(targetKode, graph);
    }
}