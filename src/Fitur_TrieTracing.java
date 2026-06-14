public class Fitur_TrieTracing {
    public static void cetak() {
        System.out.println("\n======================================================");
        System.out.println("  [LOG SIMULASI] TRACING PROSES TRIE (AUTOCOMPLETE)");
        System.out.println("======================================================");
        System.out.println("[INFO] Kata kunci (prefix) yang dicari : \"sis\"");
        System.out.println("[INFO] Posisi awal pointer : ROOT NODE\n");

        System.out.println(">>> FASE 1: PENCARIAN PREFIX PADA TREE (O(L))");
        System.out.println("[STEP 1] Membaca karakter ke-1 : 's'");
        System.out.println("         -> Mengecek root.children['s'] ... [DITEMUKAN]");
        System.out.println("         -> Pointer turun ke Node('s')");

        System.out.println("[STEP 2] Membaca karakter ke-2 : 'i'");
        System.out.println("         -> Mengecek Node('s').children['i'] ... [DITEMUKAN]");
        System.out.println("         -> Pointer turun ke Node('i')");

        System.out.println("[STEP 3] Membaca karakter ke-3 : 's'");
        System.out.println("         -> Mengecek Node('i').children['s'] ... [DITEMUKAN]");
        System.out.println("         -> Pointer turun ke Node('s')");
        System.out.println("         -> Seluruh karakter prefix berhasil dipetakan!\n");

        System.out.println(">>> FASE 2: PRE-ORDER TRAVERSAL PADA SUB-TREE");
        System.out.println("[STEP 4] Memulai penelusuran dari Node('s') terakhir...");
        System.out.println("         -> Cek isEndOfWord pada Node('s'): false");
        System.out.println("         -> Mencari child node yang tidak null...");

        System.out.println("[STEP 5] Ditemukan cabang lanjutan: 't' -> 'e' -> 'm'");
        System.out.println("         -> Pointer mencapai Node('m') (Kata: \"sistem\")");
        System.out.println("         -> Cek isEndOfWord pada Node('m'): true [VALID]");

        System.out.println("[STEP 6] Mengumpulkan CourseID dari Node('m')...");
        System.out.println("         -> CourseID ditemukan: [Index 3, Index 8]");
        System.out.println("         -> Menyimpan ID ke dalam List hasil (Result).\n");

        System.out.println(">>> FASE 3: BACKTRACKING & FINISHING");
        System.out.println("[STEP 7] Backtrack pointer ke atas untuk mencari cabang lain...");
        System.out.println("         -> Tidak ada cabang lain yang aktif di bawah Node('s').");
        System.out.println("[DONE] Traversal selesai. Mengembalikan Result List ke SmartPath.");
        System.out.println("======================================================\n");
    }
}