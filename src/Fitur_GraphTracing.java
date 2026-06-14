public class Fitur_GraphTracing {
    public static void cetak() {
        System.out.println("\n======================================================");
        System.out.println("  [LOG SIMULASI] TRACING PROSES GRAPH (DFS MUNDUR)");
        System.out.println("======================================================");
        System.out.println("[INFO] Target Mata Kuliah : Struktur Data (Index 5)");
        System.out.println("[INFO] Inisialisasi array visited[] dengan nilai false\n");

        System.out.println(">>> FASE 1: PEMANGGILAN DFS PERTAMA");
        System.out.println("[STEP 1] dfsMundur(Index 5) dipanggil.");
        System.out.println("         -> Set visited[5] = true");
        System.out.println("         -> Memindai seluruh node mencari incoming edge ke Index 5...");

        System.out.println(">>> FASE 2: REKURSI & PENELUSURAN ADJACENCY LIST");
        System.out.println("[STEP 2] Ditemukan Edge: Node(Index 1) menunjuk ke Node(Index 5)");
        System.out.println("         -> Cek visited[1]... Hasil: false");
        System.out.println("         -> Mencetak hasil: \"-> Wajib Lulus: Algoritma Pemrograman\"");
        
        System.out.println("[STEP 3] Memulai Rekursi -> dfsMundur(Index 1) dipanggil.");
        System.out.println("         -> Set visited[1] = true");
        System.out.println("         -> Memindai node mencari incoming edge ke Index 1...");
        System.out.println("         -> Hasil pemindaian: 0 incoming edge (Tidak ada prasyarat).");

        System.out.println(">>> FASE 3: BACKTRACKING (KEMBALI KE CALL STACK SEBELUMNYA)");
        System.out.println("[STEP 4] dfsMundur(Index 1) selesai dieksekusi (Return).");
        System.out.println("         -> Melanjutkan pencarian incoming edge untuk Index 5...");
        System.out.println("         -> Tidak ada edge lain yang menuju ke Index 5.");
        
        System.out.println("[DONE] Call stack kosong. Algoritma DFS selesai.");
        System.out.println("======================================================\n");
    }
}