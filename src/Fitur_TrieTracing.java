public class Fitur_TrieTracing {

    private TrieNode root;
    private int totalInserted;

    public Fitur12_TrieTracing() {
        root          = new TrieNode('\0');
        totalInserted = 0;
    }

    public void insert(String namaMK, String kodeMK, boolean withTrace) {
        String nama = namaMK.toUpperCase();

        if (withTrace) {
            System.out.println("\n╔══════════════════════════════════════════════════════╗");
            System.out.println("║     FITUR 12A — TRACING INSERT TRIE (STEP-BY-STEP)  ║");
            System.out.println("╚══════════════════════════════════════════════════════╝");
            System.out.println("  Input  : \"" + namaMK + "\"");
            System.out.println("  Kode   : " + kodeMK);
            System.out.println("  Jumlah karakter: " + nama.length());
            System.out.println("──────────────────────────────────────────────────────");
            System.out.println("  Step | Char | Status Node          | Jalur Trie");
            System.out.println("  -----|------|----------------------|-------------------");
        }

        TrieNode curr = root;
        String jalur  = "[ROOT]";

        for (int step = 0; step < nama.length(); step++) {
            char c = nama.charAt(step);
            jalur += " → '" + c + "'";

            if (!Character.isLetter(c)) {
                if (withTrace) {
                    System.out.printf("  %4d | '%c'  | SKIP (non-alfabet)   | %s%n",
                        step + 1, c, jalur);
                }
                continue;
            }

            boolean nodeAda = curr.hasChild(c);

            if (!nodeAda) {
                curr.setChild(c, new TrieNode(c));
                if (withTrace) {
                    System.out.printf("  %4d |  %c   | BUAT NODE BARU       | %s%n",
                        step + 1, c, jalur);
                }
            } else {
                if (withTrace) {
                    System.out.printf("  %4d |  %c   | NODE SUDAH ADA       | %s%n",
                        step + 1, c, jalur);
                }
            }

            curr = curr.getChild(c);
        }

        boolean sudahAda = curr.isEnd;
        curr.isEnd      = true;
        curr.courseCode = kodeMK;

        if (withTrace) {
            System.out.println("──────────────────────────────────────────────────────");
            if (sudahAda) {
                System.out.println("  [!] Node akhir sudah ada. Kode MK diperbarui.");
            } else {
                totalInserted++;
                System.out.println("  [✓] isEnd = true  → Node akhir ditandai.");
                System.out.println("  [✓] courseCode    = \"" + kodeMK + "\" tersimpan.");
                System.out.println("  [✓] Total MK dalam Trie: " + totalInserted);
            }
            System.out.println("══════════════════════════════════════════════════════\n");
        } else {
            if (!sudahAda) totalInserted++;
        }
    }

    public void searchPrefix(String prefix) {
        String prefixUpper = prefix.toUpperCase();

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║    FITUR 12B — TRACING SEARCH PREFIX TRIE           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Keyword : \"" + prefix + "\"");
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("  FASE 1: Penelusuran Karakter Prefix");
        System.out.println("  Step | Char | Hasil               | Keterangan");
        System.out.println("  -----|------|---------------------|-------------------");

        TrieNode curr = root;
        String jalur  = "[ROOT]";

        for (int step = 0; step < prefixUpper.length(); step++) {
            char c = prefixUpper.charAt(step);

            if (!Character.isLetter(c)) {
                System.out.printf("  %4d |  %c   | SKIP                | Non-alfabet%n",
                    step + 1, c);
                continue;
            }

            jalur += " → '" + c + "'";

            if (!curr.hasChild(c)) {
                System.out.printf("  %4d |  %c   | TIDAK ADA (NULL)    | Pencarian berhenti!%n",
                    step + 1, c);
                System.out.println("──────────────────────────────────────────────────────");
                System.out.println("  HASIL: Tidak ada MK dengan prefix \"" + prefix + "\"");
                System.out.println("══════════════════════════════════════════════════════\n");
                return;
            }

            System.out.printf("  %4d |  %c   | NODE ADA ✓          | %s%n",
                step + 1, c, jalur);
            curr = curr.getChild(c);
        }

        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("  >> Prefix ditemukan! Posisi: " + jalur);
        System.out.println("  FASE 2: Kumpulkan semua MK (DFS Trie)");
        System.out.println("──────────────────────────────────────────────────────");

        java.util.ArrayList<String[]> hasil = new java.util.ArrayList<>();
        kumpulkanHasil(curr, prefixUpper, hasil);

        if (hasil.isEmpty()) {
            System.out.println("  Tidak ada MK lengkap dengan prefix ini.");
        } else {
            System.out.println("  No | Kode MK    | Nama Mata Kuliah");
            System.out.println("  ---|------------|------------------------------------------");
            for (int i = 0; i < hasil.size(); i++) {
                System.out.printf("  %2d | %-10s | %s%n",
                    i + 1, hasil.get(i)[1], hasil.get(i)[0]);
            }
            System.out.println("──────────────────────────────────────────────────────");
            System.out.println("  >> " + hasil.size() + " mata kuliah ditemukan.");
        }
        System.out.println("══════════════════════════════════════════════════════\n");
    }

    private void kumpulkanHasil(TrieNode node, String builtWord,
                                 java.util.ArrayList<String[]> hasil) {
        if (node == null) return;
        if (node.isEnd) hasil.add(new String[]{ builtWord, node.courseCode });

        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            if (node.children[i] != null) {
                kumpulkanHasil(node.children[i], builtWord + (char)('A' + i), hasil);
            }
        }
    }

    public void loadDariGraph(CourseGraph graph) {
        for (int i = 0; i < graph.size; i++) {
            insert(graph.courses[i].nama, graph.courses[i].kode, false);
        }
        System.out.println("  [Trie] " + totalInserted + " mata kuliah berhasil diindeks.");
    }

    public static void menu(CourseGraph graph, java.util.Scanner sc) {
        Fitur12_TrieTracing trie = new Fitur12_TrieTracing();
        System.out.print("\n  [Trie] Memuat data... ");
        trie.loadDariGraph(graph);

        boolean balik = false;
        while (!balik) {
            System.out.println("\n=== FITUR 12 — TRACING PROSES TRIE (STEP-BY-STEP) ===");
            System.out.println("  1. Tracing INSERT nama MK ke Trie");
            System.out.println("  2. Tracing SEARCH prefix di Trie");
            System.out.println("  3. Demo gabungan (insert + search)");
            System.out.println("  0. Kembali");
            System.out.print("  Pilih: ");

            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input tidak valid.");
                continue;
            }

            switch (pilih) {
                case 1:
                    System.out.print("  Nama MK : ");
                    String nama = sc.nextLine().trim();
                    System.out.print("  Kode MK : ");
                    String kode = sc.nextLine().trim().toUpperCase();
                    trie.insert(nama, kode, true);
                    break;
                case 2:
                    System.out.print("  Prefix  : ");
                    trie.searchPrefix(sc.nextLine().trim());
                    break;
                case 3:
                    System.out.println("\n  [DEMO] Insert 'Kecerdasan Buatan' lalu search prefix 'Ke'");
                    trie.insert("Kecerdasan Buatan", "DEMO001", true);
                    trie.searchPrefix("Ke");
                    break;
                case 0:
                    balik = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }
}
