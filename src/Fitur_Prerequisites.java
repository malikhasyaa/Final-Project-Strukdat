public class Fitur_Prerequisites {

    public static void tampilkanPrasyarat(CourseGraph graph, String kodeTujuan) {
        int targetIdx = graph.findIndex(kodeTujuan);

        if (targetIdx == -1) {
            System.out.println("  [!] Kode MK '" + kodeTujuan + "' tidak ditemukan.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║             DAFTAR PRASYARAT MATA KULIAH             ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Target MK  : [" + graph.courses[targetIdx].kode + "] "
                           + graph.courses[targetIdx].nama);
        System.out.println("  Semester   : " + graph.courses[targetIdx].semester);
        System.out.println("  Level      : " + graph.courses[targetIdx].level);
        System.out.println("──────────────────────────────────────────────────────");

        int jumlah = 0;
        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) {
                if (curr.destIndex == targetIdx) {
                    jumlah++;
                    System.out.printf("  %2d. [%-10s] %-40s | Smt %d | %-10s | %d SKS%n",
                        jumlah,
                        graph.courses[i].kode,
                        graph.courses[i].nama,
                        graph.courses[i].semester,
                        graph.courses[i].level,
                        graph.courses[i].sks
                    );
                }
                curr = curr.next;
            }
        }

        System.out.println("──────────────────────────────────────────────────────");
        if (jumlah == 0) {
            System.out.println("  >> Mata kuliah ini TIDAK memiliki prasyarat.");
            System.out.println("     Dapat langsung diambil dari semester pertama.");
        } else {
            System.out.println("  >> Total prasyarat langsung: " + jumlah + " mata kuliah.");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }

    public static void tampilkanRantaiPrasyarat(CourseGraph graph, String kodeTujuan) {
        int targetIdx = graph.findIndex(kodeTujuan);
        if (targetIdx == -1) {
            System.out.println("  [!] Kode MK tidak ditemukan.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║               RANTAI PRASYARAT LENGKAP               ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Target : [" + kodeTujuan + "] " + graph.courses[targetIdx].nama);
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("  Semua MK yang harus diselesaikan terlebih dahulu:\n");

        boolean[] visited = new boolean[graph.size];
        rantaiRekursif(graph, targetIdx, visited, 1);
    }

    private static void rantaiRekursif(CourseGraph graph, int targetIdx,
                                        boolean[] visited, int level) {
        for (int i = 0; i < graph.size; i++) {
            AdjNode curr = graph.courses[i].adjList;
            while (curr != null) {
                if (curr.destIndex == targetIdx && !visited[i]) {
                    visited[i] = true;
                    String indent = "  ";
                    for (int d = 0; d < level; d++) indent += "  ";
                    System.out.printf("%s└─ [%-10s] %s (Smt %d)%n",
                        indent,
                        graph.courses[i].kode,
                        graph.courses[i].nama,
                        graph.courses[i].semester
                    );
                    rantaiRekursif(graph, i, visited, level + 1);
                }
                curr = curr.next;
            }
        }
    }

    public static void menu(CourseGraph graph, java.util.Scanner sc) {
        System.out.println("\n=== TAMPILKAN PRASYARAT SEBUAH TOPIK ===");
        System.out.println("  1. Prasyarat langsung (1 level)");
        System.out.println("  2. Rantai prasyarat lengkap (semua level)");
        System.out.print("  Pilih mode: ");

        int mode;
        try {
            mode = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Input tidak valid.");
            return;
        }

        // PERBAIKAN: Cek validitas mode di sini. Tolak jika bukan 1 atau 2.
        if (mode != 1 && mode != 2) {
            System.out.println("  [!] Pilihan tidak valid. Silakan pilih 1 atau 2.");
            return;
        }

        // Jika mode valid (1 atau 2), baru program akan meminta Kode MK target
        System.out.print("  Masukkan Kode MK target: ");
        String kode = sc.nextLine().trim().toUpperCase();

        if (mode == 1) {
            tampilkanPrasyarat(graph, kode);
        } else if (mode == 2) {
            tampilkanRantaiPrasyarat(graph, kode);
        }
    }
}