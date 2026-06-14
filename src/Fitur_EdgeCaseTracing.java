public class Fitur_EdgeCaseTracing {
    public static void cetak() {
        System.out.println("\n======================================================");
        System.out.println("  [LOG SIMULASI] TRACING EDGE CASE (INPUT INVALID)");
        System.out.println("======================================================");
        System.out.println("[INFO] Skenario : User mencari topik yang tidak ada di database.");
        System.out.println("[INFO] Input keyword : \"zzz\"\n");

        System.out.println(">>> FASE 1: EKSEKUSI TRIE.AUTOCOMPLETE(\"zzz\")");
        System.out.println("[STEP 1] Posisi awal pointer di ROOT.");
        System.out.println("         -> Membaca karakter ke-1 : 'z'");
        
        System.out.println("[STEP 2] Mengecek root.children['z']...");
        System.out.println("         -> Hasil pengecekan: NULL (Cabang tidak pernah dibuat).");
        System.out.println("         -> Iterasi langsung dihentikan secara paksa (Break).");
        
        System.out.println("[STEP 3] Fungsi Trie mengembalikan List<Integer> KOSONG (Empty List).\n");

        System.out.println(">>> FASE 2: PENANGANAN OLEH SMART PATH NAVIGATOR");
        System.out.println("[STEP 4] List hasil diterima oleh Fitur_SmartPath.");
        System.out.println("         -> Mengecek kondisi: if (hasilPencarian.isEmpty())");
        System.out.println("         -> Kondisi terpenuhi (TRUE).");
        
        System.out.println("[STEP 5] Program membatalkan pemanggilan Graph / DFS.");
        System.out.println("         -> Mencetak peringatan ramah ke layar terminal:");
        System.out.println("         -> \"Topik tidak ditemukan di database.\"");
        
        System.out.println("[DONE] Program berhasil menghindari NullPointerException. Sistem aman.");
        System.out.println("======================================================\n");
    }
}