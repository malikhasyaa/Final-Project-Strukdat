import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CourseGraph {
    static final int MAX = 30;
    CourseNode[] courses;
    int size;

    public CourseGraph() {
        courses = new CourseNode[MAX];
        size    = 0;
    }

    // Cari index dari kode MK tertentu, return -1 kalau tidak ada
    public int findIndex(String kode) {
        for (int i = 0; i < size; i++) {
            if (courses[i].kode.equals(kode)) return i;
        }
        return -1;
    }

    // Tambah satu MK ke dalam array
    public void addCourse(String kode, String nama, String kategori,
                          String level, int sks, int estimasiJam, int semester) {
        if (size >= MAX) {
            System.out.println("Graph penuh!");
            return;
        }
        if (findIndex(kode) != -1) {
            System.out.println("Gagal: Kode MK sudah terdaftar.");
            return;
        }
        courses[size] = new CourseNode(kode, nama, kategori, level, sks, estimasiJam, semester);
        size++;
    }

    // Tambah edge
    public void addEdge(String dariKode, String keKode) {
        int dari = findIndex(dariKode);
        int ke   = findIndex(keKode);
        if (dari == -1 || ke == -1) {
            System.out.println("Gagal addEdge: kode tidak ditemukan.");
            return;
        }
        AdjNode baru = new AdjNode(ke);
        baru.next = courses[dari].adjList;
        courses[dari].adjList = baru;
    }

    // Load dataset hardcoded (opsional jika sudah pakai Fitur_DataLoader)
    public void loadData() {
        // === NODE ===
        addCourse("ET234101","Statistika dan Probabilitas","Matematika","Mudah",3,60,1);
        addCourse("ET234102","Arsitektur dan Organisasi Komputer","Sistem","Sedang",3,80,1);
        addCourse("ET234103","Algoritma dan Teknik Pemrograman","Programming","Sedang",3,80,1);
        addCourse("EE234101","Pengantar Teknologi Elektro & Informatika","Pengantar","Mudah",2,40,1);
        addCourse("SM234101","Kalkulus 1","Matematika","Sedang",3,80,1);
        addCourse("SF234204","Fisika Dasar","Sains","Sedang",3,60,1);
        addCourse("ET234104","Hukum dan Etika Teknologi Informasi","Etika","Mudah",2,40,1);
        addCourse("ET234201","Sistem Operasi","Sistem","Sulit",3,80,2);
        addCourse("SM234201","Kalkulus 2","Matematika","Sulit",3,80,2);
        addCourse("ET234202","Arsitektur Enterprise","Manajemen","Sedang",3,60,2);
        addCourse("ET234203","Struktur Data dan OOP","Programming","Sulit",4,100,2);
        addCourse("ET234204","Sistem Basis Data","Data","Sedang",3,80,2);
        addCourse("ET234301","Ethical Hacking dan Uji Keamanan Siber","Keamanan","Sulit",3,80,3);
        addCourse("ET234302","Keamanan Jaringan Komputer","Keamanan","Sedang",3,80,3);
        addCourse("ET234303","Internet of Things","Sistem","Sedang",3,80,3);
        addCourse("ET234304","Komunikasi Profesional","Soft Skill","Mudah",2,40,3);
        addCourse("ET234305","Pemrograman Web","Programming","Sedang",3,80,3);
        addCourse("ET234306","Komunikasi Data dan Jaringan Komputer","Sistem","Sedang",3,80,3);
        addCourse("ET234401","Manajemen Insiden Keamanan Siber","Keamanan","Sulit",3,80,4);
        addCourse("ET234402","Security Operations Center","Keamanan","Sulit",3,80,4);
        addCourse("ET234403","Teknologi Komputasi Awan","Sistem","Sedang",3,80,4);
        addCourse("ET234404","Big Data dan Data Lakehouse","Data","Sulit",3,80,4);
        addCourse("ET234405","Kecerdasan Artifisial dan Machine Learning","AI","Sulit",3,80,4);
        addCourse("ET234406","Integrasi Sistem","Sistem","Sulit",3,80,4);
        addCourse("ET234501","Kriptografi","Keamanan","Sangat Sulit",3,80,5);

        // === EDGE ===
        addEdge("ET234103","ET234203"); addEdge("ET234103","ET234305");
        addEdge("ET234103","ET234204"); addEdge("ET234103","ET234303");
        addEdge("ET234102","ET234201"); addEdge("ET234102","ET234303");
        addEdge("SM234101","SM234201"); addEdge("SF234204","ET234303");
        addEdge("ET234101","ET234404"); addEdge("ET234101","ET234405");
        addEdge("ET234101","ET234501");
        addEdge("EE234101","ET234202"); addEdge("EE234101","ET234306");
        addEdge("EE234101","ET234301");
        addEdge("ET234201","ET234301"); addEdge("ET234201","ET234302");
        addEdge("ET234201","ET234403");
        addEdge("ET234306","ET234302"); addEdge("ET234306","ET234303");
        addEdge("ET234306","ET234403");
        addEdge("ET234203","ET234405"); addEdge("ET234203","ET234404");
        addEdge("ET234203","ET234305");
        addEdge("ET234204","ET234305"); addEdge("ET234204","ET234404");
        addEdge("ET234204","ET234406"); addEdge("ET234204","ET234403");
        addEdge("ET234202","ET234406");
        addEdge("ET234104","ET234304"); addEdge("ET234104","ET234301");
        addEdge("ET234301","ET234401"); addEdge("ET234301","ET234402");
        addEdge("ET234301","ET234501");
        addEdge("ET234302","ET234401"); addEdge("ET234302","ET234402");
        addEdge("ET234302","ET234501");
        addEdge("SM234201","ET234405");
        addEdge("ET234305","ET234406");
        addEdge("ET234304","ET234401");
        addEdge("ET234403","ET234404");
        addEdge("ET234303","ET234406");
    }

    // DFS rekursif (internal)
    private void dfsRekursif(int index, boolean[] visited, String indent) {
        visited[index] = true;
        System.out.println(indent + "-> " + courses[index].kode + " | " + courses[index].nama);

        AdjNode curr = courses[index].adjList;
        while (curr != null) {
            if (!visited[curr.destIndex]) {
                dfsRekursif(curr.destIndex, visited, indent + "   ");
            }
            curr = curr.next;
        }
    }

    // DFS publik (Fitur 5)
    public void dfs(String kodeAwal) {
        int start = findIndex(kodeAwal);
        if (start == -1) {
            System.out.println("Kode MK tidak ditemukan: " + kodeAwal);
            return;
        }
        boolean[] visited = new boolean[size];
        System.out.println("\n=== DFS: Rantai MK dari " + courses[start].nama + " ===");
        dfsRekursif(start, visited, "");
    }

    // Hapus edge (Fitur 3)
    public void removeEdge(String dariKode, String keKode) {
        int dari = findIndex(dariKode);
        int ke   = findIndex(keKode);

        if (dari == -1 || ke == -1) {
            System.out.println("Gagal: Kode MK tidak ditemukan.");
            return;
        }

        AdjNode curr = courses[dari].adjList;
        AdjNode prev = null;

        while (curr != null) {
            if (curr.destIndex == ke) {
                if (prev == null) {
                    courses[dari].adjList = curr.next;
                } else {
                    prev.next = curr.next;
                }
                System.out.println("Berhasil: Relasi " + dariKode + " -> " + keKode + " dihapus.");
                return;
            }
            prev = curr;
            curr = curr.next;
        }

        System.out.println("Gagal: Relasi " + dariKode + " -> " + keKode + " tidak ditemukan.");
    }

    // Hapus MK cascade (Fitur 3)
    public void removeCourse(String kode) {
        int target = findIndex(kode);
        if (target == -1) {
            System.out.println("Gagal: Kode MK '" + kode + "' tidak ditemukan.");
            return;
        }

        String nama = courses[target].nama;

        // Hapus semua edge masuk ke target
        for (int i = 0; i < size; i++) {
            if (i == target) continue;
            AdjNode curr = courses[i].adjList;
            AdjNode prev = null;
            while (curr != null) {
                if (curr.destIndex == target) {
                    if (prev == null) {
                        courses[i].adjList = curr.next;
                    } else {
                        prev.next = curr.next;
                    }
                    curr = (prev == null) ? courses[i].adjList : prev.next;
                } else {
                    prev = curr;
                    curr = curr.next;
                }
            }
        }

        // Geser array
        for (int i = target; i < size - 1; i++) {
            courses[i] = courses[i + 1];
        }
        courses[size - 1] = null;
        size--;

        // Update destIndex yang bergeser
        for (int i = 0; i < size; i++) {
            AdjNode curr = courses[i].adjList;
            while (curr != null) {
                if (curr.destIndex > target) {
                    curr.destIndex--;
                }
                curr = curr.next;
            }
        }

        System.out.println("Berhasil: MK '" + nama + "' dan semua relasinya dihapus.");
    }

    // Helper untuk Rebuild / Sinkronisasi Trie
    public void rebuildTrie(Trie myTrie) {
        if (myTrie != null) {
            myTrie.clear(); 
            for (int i = 0; i < size; i++) {
                myTrie.insert(courses[i].nama.toLowerCase(), i);
            }
        }
    }

    // Menu Insert (Fitur 1)
    public void insertMenu(Scanner sc, Trie myTrie) {
        System.out.println("\n=== INSERT DATA ===");
        System.out.println("1. Tambah Mata Kuliah baru");
        System.out.println("2. Tambah Relasi Prasyarat baru");
        System.out.print("Pilih: ");
        int pilih = Integer.parseInt(sc.nextLine().trim());

        if (pilih == 1) {
            System.out.print("Kode MK     : "); String kode     = sc.nextLine();
            System.out.print("Nama MK     : "); String nama     = sc.nextLine();
            System.out.print("Kategori    : "); String kategori = sc.nextLine();
            System.out.print("Level       : "); String level    = sc.nextLine();
            System.out.print("SKS         : "); int sks         = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Est. Jam    : "); int jam         = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Semester    : "); int smt         = Integer.parseInt(sc.nextLine().trim());
            
            addCourse(kode, nama, kategori, level, sks, jam, smt);
            
            if (myTrie != null) {
                int indexBaru = findIndex(kode); 
                myTrie.insert(nama.toLowerCase(), indexBaru);
            }
            System.out.println("Berhasil: MK '" + nama + "' ditambahkan.");

        } else if (pilih == 2) {
            System.out.print("Kode MK Prasyarat (dari) : "); String dari = sc.nextLine();
            System.out.print("Kode MK Tujuan    (ke)   : "); String ke   = sc.nextLine();
            int idxDari = findIndex(dari);
            int idxKe   = findIndex(ke);
            if (idxDari == -1) {
                System.out.println("Gagal: Kode '" + dari + "' tidak ditemukan.");
            } else if (idxKe == -1) {
                System.out.println("Gagal: Kode '" + ke + "' tidak ditemukan.");
            } else {
                addEdge(dari, ke);
                System.out.println("Berhasil: Relasi " + dari + " -> " + ke + " ditambahkan.");
            }
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    // Menu Update dan Delete digabung (Fitur 3)
    public void updateDeleteMenu(Scanner sc, Trie myTrie) {
        System.out.println("\n=== UPDATE / DELETE DATA ===");
        System.out.println("1. Update Data Mata Kuliah");
        System.out.println("2. Hapus Relasi Prasyarat (removeEdge)");
        System.out.println("3. Hapus Mata Kuliah beserta semua relasinya (removeCourse)");
        System.out.print("Pilih: ");
        int pilih = Integer.parseInt(sc.nextLine().trim());

        if (pilih == 1) { 
            System.out.print("Masukkan Kode MK yang ingin di-update: "); 
            String kode = sc.nextLine();
            int idx = findIndex(kode);
            
            if (idx == -1) {
                System.out.println("Gagal: Kode MK tidak ditemukan.");
                return;
            }

            System.out.println("\nMasukkan data baru (Tekan ENTER jika tidak ingin mengubah data):");
            
            System.out.print("Nama MK [" + courses[idx].nama + "]: ");
            String newNama = sc.nextLine();
            if (!newNama.trim().isEmpty()) courses[idx].nama = newNama;

            System.out.print("Kategori [" + courses[idx].kategori + "]: ");
            String newKat = sc.nextLine();
            if (!newKat.trim().isEmpty()) courses[idx].kategori = newKat;

            System.out.print("Level [" + courses[idx].level + "]: ");
            String newLevel = sc.nextLine();
            if (!newLevel.trim().isEmpty()) courses[idx].level = newLevel;

            System.out.print("SKS [" + courses[idx].sks + "]: ");
            String newSks = sc.nextLine();
            if (!newSks.trim().isEmpty()) courses[idx].sks = Integer.parseInt(newSks.trim());

            System.out.print("Est. Jam [" + courses[idx].estimasiJam + "]: ");
            String newJam = sc.nextLine();
            if (!newJam.trim().isEmpty()) courses[idx].estimasiJam = Integer.parseInt(newJam.trim());

            System.out.print("Semester [" + courses[idx].semester + "]: ");
            String newSmt = sc.nextLine();
            if (!newSmt.trim().isEmpty()) courses[idx].semester = Integer.parseInt(newSmt.trim());

            rebuildTrie(myTrie);
            System.out.println("Berhasil: Data MK '" + courses[idx].kode + "' telah diperbarui.");

        } else if (pilih == 2) {
            System.out.print("Kode MK Prasyarat (dari) : "); String dari = sc.nextLine();
            System.out.print("Kode MK Tujuan    (ke)   : "); String ke   = sc.nextLine();
            removeEdge(dari, ke);

        } else if (pilih == 3) {
            System.out.print("Kode MK yang akan dihapus: "); String kode = sc.nextLine();
            int idx = findIndex(kode);
            if (idx == -1) {
                System.out.println("Gagal: Kode MK tidak ditemukan.");
                return;
            }
            System.out.println("PERINGATAN: Menghapus '" + courses[idx].nama +
                               "' akan memutus semua relasi yang terhubung.");
            System.out.print("Lanjutkan? (y/n): ");
            String konfirmasi = sc.nextLine();
            if (konfirmasi.equalsIgnoreCase("y")) {
                removeCourse(kode);
                rebuildTrie(myTrie);
                System.out.println("Data Trie otomatis disinkronisasi ulang.");
            } else {
                System.out.println("Dibatalkan.");
            }
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    // Tampilkan Graph (Fitur 4)
    public void displayGraph() {
        System.out.println("\n=== DAFTAR MATA KULIAH & RELASI ===");
        for (int i = 0; i < size; i++) {
            System.out.print("[" + courses[i].kode + "] " + courses[i].nama + " -> ");
            AdjNode curr = courses[i].adjList;
            if (curr == null) System.out.print("(tidak ada relasi keluar)");
            while (curr != null) {
                System.out.print(courses[curr.destIndex].kode);
                if (curr.next != null) System.out.print(", ");
                curr = curr.next;
            }
            System.out.println();
        }
    }

    // FITUR BARU: Menyimpan data array dan edge kembali ke file CSV
    public void saveDataToFile(String fileNode, String fileEdge) {
        // 1. Simpan (Timpa) file Node
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileNode))) {
            // Tulis Header agar sesuai dengan pembacaan DataLoader
            pw.println("KODE,NAMA,KATEGORI,LEVEL,SKS,EST_JAM,SEMESTER");
            for (int i = 0; i < size; i++) {
                CourseNode c = courses[i];
                pw.println(c.kode + "," + c.nama + "," + c.kategori + "," + 
                           c.level + "," + c.sks + "," + c.estimasiJam + "," + c.semester);
            }
            System.out.println("[OK] Data Mata Kuliah permanen disimpan ke: " + fileNode);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan file node: " + e.getMessage());
        }

        // 2. Simpan (Timpa) file Edge
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileEdge))) {
            // Tulis Header Edge
            pw.println("KODE_PRASYARAT,KODE_LANJUTAN");
            for (int i = 0; i < size; i++) {
                AdjNode curr = courses[i].adjList;
                while (curr != null) {
                    pw.println(courses[i].kode + "," + courses[curr.destIndex].kode);
                    curr = curr.next;
                }
            }
            System.out.println("[OK] Data Relasi Prasyarat permanen disimpan ke: " + fileEdge);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan file edge: " + e.getMessage());
        }
    }
}