import java.util.List;

public class Fitur_SearchTopik {


    public static void menu(Trie trie, CourseGraph graph, java.util.Scanner sc) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     FITUR 7 — SEARCH TOPIK                          ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.print("  Masukkan kata kunci / prefix (contoh: 'Sistem'): ");
        String keyword = sc.nextLine().trim();

        System.out.println("  Keyword : \"" + keyword + "\"");
        System.out.println("──────────────────────────────────────────────────────");

        if (trie == null) {
            System.out.println("  [!] Sistem Trie belum diaktifkan.");
            return;
        }

        List<Integer> hasil = trie.autocomplete(keyword.toLowerCase());

        if (hasil == null || hasil.isEmpty()) {
            System.out.println("  [!] Data/Topik tidak ditemukan untuk keyword: \"" + keyword + "\"");
        } else {
            System.out.printf("  %-4s | %-12s | %-42s | %-10s | Smt%n",
                "No", "Kode MK", "Nama Mata Kuliah", "Level");
            System.out.println("  -----|--------------|------------------------------------------|------------|----");
            for (int i = 0; i < hasil.size(); i++) {
                int idx = hasil.get(i);
                CourseNode mk = graph.courses[idx];
                System.out.printf("  %-4d | %-12s | %-42s | %-10s | %d%n",
                    i + 1, mk.kode, mk.nama, mk.level, mk.semester);
            }
            System.out.println("──────────────────────────────────────────────────────");
            System.out.println("  >> " + hasil.size() + " mata kuliah ditemukan.");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }
}