## **FINAL PROJECT STRUKTUR DATA DAN PEMROGRAMAN BERORIENTASI OBJEK (A) 2026**

Sistem manajemen kurikulum berbasis **Trie** dan **Directed Graph** yang dirancang untuk membantu mahasiswa menelusuri jalur prasyarat mata kuliah, mencari topik secara cerdas, serta mendapatkan rekomendasi urutan belajar yang terstruktur dan logis.

---

## Kelompok 2

| No | Nama | NRP |
|----|------|-----|
| 1 | Ganestri Naurah Sawestri | 5027251014 |
| 2 | Viko Rizky Fauzan | 5027251017 |
| 3 | Malikha Syarifa Dewi | 5027251032 |
| 4 | Mahrinza Redouane Zakariyah | 5027251074 |
| 5 | Rifqi Dwi Muslim | 5027251077 |

---

##  Daftar Isi

1. [Deskripsi Proyek](#deskripsi-proyek)
2. [Struktur Folder](#struktur-folder)
3. [Dataset](#dataset)
4. [Struktur Data dan Algoritma](#struktur-data-dan-algoritma)
5. [Analisis Kompleksitas Waktu](#analisis-kompleksitas-waktu)
6. [Design Decision Log](#design-decision-log)
7. [Skenario Pengujian](#skenario-pengujian)
8. [Penjelasan Source Code](#penjelasan-source-code)
9. [Cara Menjalankan Program](#cara-menjalankan-program)

---

## Deskripsi Proyek

Library Knowledge Navigator adalah aplikasi berbasis Java yang memodelkan kurikulum program studi sebagai sebuah graf prasyarat. Setiap mata kuliah direpresentasikan sebagai simpul (vertex), dan setiap hubungan prasyarat antarmatakultiah direpresentasikan sebagai sisi berarah (directed edge).

Sistem ini memadukan dua struktur data utama secara sinergis:

- **Trie (Prefix Tree):** Bertindak sebagai mesin indeks pencarian teks yang sangat cepat. Pengguna cukup mengetikkan beberapa karakter awal untuk menemukan mata kuliah yang relevan tanpa perlu menghafal kode secara lengkap.
- **Directed Graph (Adjacency List):** Bertindak sebagai otak logika relasional yang menyimpan dan memproses seluruh hubungan prasyarat antar mata kuliah.

Gabungan keduanya menghasilkan fitur utama bernama **Smart Path Navigator**, di mana hasil pencarian Trie langsung diumpankan ke algoritma DFS pada Graf untuk menampilkan rantai prasyarat secara instan.

---

## Struktur Folder

```
Final-Project-Strukdat/
├── src/
│   ├── Main.java
│   ├── TrieNode.java
│   ├── Trie.java
│   ├── AdjNode.java
│   ├── CourseNode.java
│   ├── CourseGraph.java
│   ├── Fitur_DataLoader.java
│   ├── Fitur_SmartPath.java
│   ├── Fitur_DFSPrasyarat.java
│   ├── Fitur_Prerequisites.java
│   ├── Fitur_rekomendasibelajar.java
│   ├── Fitur_CycleDetection.java
│   ├── Fitur_IsolatedTopik.java
│   ├── Fitur_SearchTopik.java
│   ├── Fitur_TrieTracing.java
│   ├── Fitur_GraphTracing.java
│   └── Fitur_EdgeCaseTracing.java
├── data/
│   ├── node.csv
│   └── edge.csv
└── README.md
```

---

## Dataset

Dataset dibuat secara mandiri oleh kelompok dan merepresentasikan kurikulum program studi Rekayasa Teknologi Informasi. Dataset terdiri dari dua file CSV.

### node.csv — Data Mata Kuliah (25 Node)

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| KODE | String | Kode unik mata kuliah (Primary Key) |
| NAMA | String | Nama lengkap mata kuliah |
| KATEGORI | String | Bidang ilmu (Matematika, Programming, Keamanan, dll.) |
| LEVEL | String | Tingkat kesulitan (Mudah, Sedang, Sulit, Sangat Sulit) |
| SKS | Integer | Jumlah Satuan Kredit Semester |
| EST_JAM | Integer | Estimasi jam belajar per semester |
| SEMESTER | Integer | Semester penawaran mata kuliah (1 hingga 5) |

Daftar seluruh 25 mata kuliah dalam dataset:

| Kode | Nama Mata Kuliah | Kategori | SKS | Semester |
|------|-----------------|----------|-----|----------|
| ET234101 | Statistika dan Probabilitas | Matematika | 3 | 1 |
| ET234102 | Arsitektur dan Organisasi Komputer | Sistem | 3 | 1 |
| ET234103 | Algoritma dan Teknik Pemrograman | Programming | 3 | 1 |
| EE234101 | Pengantar Teknologi Elektro dan Informatika | Pengantar | 2 | 1 |
| SM234101 | Kalkulus 1 | Matematika | 3 | 1 |
| SF234204 | Fisika Dasar | Sains | 3 | 1 |
| ET234104 | Hukum dan Etika Teknologi Informasi | Etika | 2 | 1 |
| ET234201 | Sistem Operasi | Sistem | 3 | 2 |
| SM234201 | Kalkulus 2 | Matematika | 3 | 2 |
| ET234202 | Arsitektur Enterprise | Manajemen | 3 | 2 |
| ET234203 | Struktur Data dan OOP | Programming | 4 | 2 |
| ET234204 | Sistem Basis Data | Data | 3 | 2 |
| ET234301 | Ethical Hacking dan Uji Keamanan Siber | Keamanan | 3 | 3 |
| ET234302 | Keamanan Jaringan Komputer | Keamanan | 3 | 3 |
| ET234303 | Internet of Things | Sistem | 3 | 3 |
| ET234304 | Komunikasi Profesional | Soft Skill | 2 | 3 |
| ET234305 | Pemrograman Web | Programming | 3 | 3 |
| ET234306 | Komunikasi Data dan Jaringan Komputer | Sistem | 3 | 3 |
| ET234401 | Manajemen Insiden Keamanan Siber | Keamanan | 3 | 4 |
| ET234402 | Security Operations Center | Keamanan | 3 | 4 |
| ET234403 | Teknologi Komputasi Awan | Sistem | 3 | 4 |
| ET234404 | Big Data dan Data Lakehouse | Data | 3 | 4 |
| ET234405 | Kecerdasan Artifisial dan Machine Learning | AI | 3 | 4 |
| ET234406 | Integrasi Sistem | Sistem | 3 | 4 |
| ET234501 | Kriptografi | Keamanan | 3 | 5 |

### edge.csv — Relasi Prasyarat (41 Edge)

File ini mendefinisikan hubungan prasyarat antarmatakultiah dalam format `KODE_PRASYARAT,KODE_LANJUTAN`. Sebuah edge berarti mata kuliah pada kolom kiri wajib lulus terlebih dahulu sebelum mengambil mata kuliah pada kolom kanan.

Contoh beberapa relasi prasyarat:

| Prasyarat | Mata Kuliah Lanjutan |
|-----------|----------------------|
| ET234103 (Algoritma) | ET234203 (Struktur Data dan OOP) |
| ET234103 (Algoritma) | ET234305 (Pemrograman Web) |
| ET234201 (Sistem Operasi) | ET234301 (Ethical Hacking) |
| ET234301 (Ethical Hacking) | ET234501 (Kriptografi) |
| ET234102 (Arsitektur Komputer) | ET234201 (Sistem Operasi) |

---

## Struktur Data dan Algoritma

### 1. Tree: Trie (Prefix Tree)

Trie yang diimplementasikan dimodifikasi untuk menyimpan **index array** mata kuliah pada node daun (`isEnd = true`). Setiap node pada Trie merepresentasikan satu karakter huruf, dengan ukuran array anak sebesar 26 (jumlah huruf alfabet).

**Operasi yang Didukung:**

- **Insert:** Memasukkan nama mata kuliah karakter per karakter ke dalam Trie, menyimpan indeks Graph pada node akhir.
- **Search Prefix (Autocomplete):** Menelusuri karakter prefix hingga node terakhir prefix tersebut, kemudian melakukan DFS pada subtree Trie untuk mengumpulkan semua nama mata kuliah yang memiliki awalan tersebut.

**Alasan Pemilihan Trie:**
Trie unggul dalam pencarian berbasis prefix. Berbeda dengan HashMap yang mengharuskan pengguna mengetik nama secara persis, Trie mampu memberikan saran otomatis (autocomplete) hanya dengan beberapa karakter awal. Dibandingkan AVL Tree, implementasi Trie jauh lebih sederhana karena tidak memerlukan operasi rotasi.

### 2. Graph: Directed Graph dengan Adjacency List

Graf berarah diimplementasikan menggunakan **array statis** `CourseNode[] courses` berkapasitas maksimal 30 node, dan setiap `CourseNode` memiliki **Linked List** `AdjNode` yang menyimpan daftar node tujuan (mata kuliah lanjutan).

**Alasan Pemilihan Adjacency List:**
Dataset memiliki 25 node dan 41 edge, tergolong sparse graph (graf renggang). Adjacency List jauh lebih hemat memori dibandingkan Adjacency Matrix yang akan mengalokasikan memori sebesar `25 x 25 = 625` sel meskipun sebagian besar kosong. Iterasi tetangga pada Adjacency List juga lebih efisien karena langsung menuju relasi yang ada.

### 3. Algoritma Graph Utama

**Algoritma 1: Depth-First Search (DFS)**

DFS digunakan untuk dua keperluan utama:
- **Traversal Prasyarat Mundur:** Menelusuri rantai prasyarat secara rekursif ke belakang dari sebuah mata kuliah target.
- **Cycle Detection (3-State DFS):** Mendeteksi siklus pada graf menggunakan tiga state node: `UNVISITED`, `VISITING`, dan `VISITED`. Siklus terdeteksi ketika ditemukan `back-edge`, yaitu ketika sebuah node dalam perjalanan DFS menunjuk kembali ke node yang sedang dalam state `VISITING`.

**Algoritma 2: Topological Sort (Kahn's Algorithm)**

Topological Sort digunakan untuk menghasilkan rekomendasi urutan belajar yang valid. Kahn's Algorithm bekerja dengan menghitung `in-degree` (jumlah prasyarat) setiap mata kuliah, menempatkan mata kuliah dengan in-degree nol ke dalam antrian, dan memproses antrian satu per satu sambil mengurangi in-degree mata kuliah lanjutan. Jika pada akhir eksekusi terdapat mata kuliah yang tidak terproses, ini menandakan adanya siklus.

**Operasi Tree Tambahan: Prefix Auto-Complete**

Saat pengguna mengetikkan prefix seperti "Sistem", Trie melakukan penelusuran pre-order dari node terakhir prefix tersebut untuk mengumpulkan semua nama mata kuliah yang dimulai dengan kata tersebut (misalnya: Sistem Operasi, Sistem Basis Data).

**Fitur Gabungan Tree dan Graph: Smart Path Navigator**

Fitur ini menyatukan keunggulan kedua struktur data:
1. Pengguna mengetikkan prefix nama mata kuliah melalui Trie.
2. Trie mengembalikan daftar mata kuliah yang cocok beserta indeks mereka di dalam Graph.
3. Indeks tersebut digunakan untuk menjalankan DFS Prasyarat pada Graph.
4. Sistem menampilkan seluruh rantai prasyarat mata kuliah yang ditemukan secara otomatis.

---

## Analisis Kompleksitas Waktu

| Operasi | Struktur / Algoritma | Kompleksitas | Alasan |
|---------|----------------------|--------------|--------|
| Insert Mata Kuliah ke Trie | Trie | O(L) | L adalah panjang karakter nama/kode MK. Algoritma hanya mengiterasi dan mengalokasikan node sebanyak jumlah karakter tanpa mempedulikan banyaknya data yang sudah ada. |
| Search Prefix | Trie | O(L) | Sama seperti insert. Penelusuran berjalan menelusuri cabang Trie sepanjang L karakter. Sangat optimal karena tidak terpengaruh oleh total N mata kuliah. |
| Traversal Rantai Prasyarat | DFS (Graph) | O(V + E) | V adalah jumlah Vertex (mata kuliah) dan E adalah jumlah Edge (relasi). Karena menggunakan Adjacency List dengan penanda `visited`, setiap node dan edge dikunjungi tepat satu kali. |
| Rekomendasi Urutan Belajar | Topological Sort (Kahn's) | O(V + E) | Algoritma mengevaluasi in-degree setiap node. Setiap V dimasukkan dan diekstrak dari antrian tepat satu kali, dan setiap E diperiksa tepat satu kali untuk mengurangi in-degree tetangga. |
| Deteksi Siklus | DFS 3-State dengan Rekursi | O(V + E) | Identik dengan traversal DFS biasa. Rekursi dihentikan seketika ketika back-edge ke node berstatus `VISITING` ditemukan. |

---

## Design Decision Log

| No | Keputusan | Alternatif | Alasan Memilih | Risiko / Kelemahan |
|----|-----------|------------|----------------|---------------------|
| 1 | Menggunakan Adjacency List untuk struktur Graph | Adjacency Matrix | Sangat hemat memori untuk graf sparse (25 node, 41 edge). Traversal tetangga lebih cepat karena tidak memerlukan iterasi pada sel yang kosong. | Pengecekan eksistensi satu edge spesifik (A langsung ke B) membutuhkan waktu O(V), berbeda dengan O(1) pada Matrix. |
| 2 | Menggunakan Trie sebagai struktur pencarian | HashMap atau AVL Tree | Mendukung pencarian berbasis prefix secara instan (fitur autocomplete). Pengguna tidak perlu mengetik nama mata kuliah secara lengkap. | Konsumsi memori lebih besar karena mengalokasikan pointer node untuk setiap kemungkinan karakter yang belum tentu terpakai. |
| 3 | Pemetaan Node dengan Integer Index Array (0-24) | Menyimpan String Kode MK langsung di dalam Graph | Manipulasi indeks array lebih cepat dan efisien secara arsitektur. Mengurangi beban komputasi objek String. | Membutuhkan langkah terpisah untuk menerjemahkan input String pengguna kembali ke Integer Index sebelum masuk ke fungsi Graph. |
| 4 | Implementasi manual Class Node dan Linked List | Menggunakan java.util.LinkedList siap pakai | Memenuhi aturan proyek. Memberikan kontrol penuh atas manajemen referensi antar data dengan performa low-level yang efisien. | Waktu pengembangan lebih lama karena harus men-debug pengelolaan referensi objek secara manual. |
| 5 | Topological Sort menggunakan Kahn's Algorithm | Topological Sort berbasis DFS dengan Stack | Kahn's Algorithm sangat intuitif untuk simulasi rekomendasi urutan belajar. Langsung memproses mata kuliah yang in-degree-nya sudah nol. | Membutuhkan struktur data tambahan berupa antrian (Queue) dan array pembantu in-degree, sehingga menggunakan sedikit memori ekstra. |
| 6 | Cycle Detection via DFS 3-State | Disjoint Set (Union-Find) | Sangat presisi untuk Directed Graph. Langsung mendeteksi back-edge pada rantai prasyarat dengan logika rekursif yang ringkas. | Jika grafnya sangat dalam (ratusan level prasyarat), rekursi berpotensi menyebabkan StackOverflowError, meskipun aman untuk 25 node. |
| 7 | Validasi Siklus Terpisah Saat Insert Data (Preventif) | Cek siklus hanya saat user meminta simulasi urutan belajar | Mencegah data graf rusak atau mustahil sejak awal. Graph selalu terjamin bebas siklus (DAG) sebelum operasi lain dijalankan. | Menambah beban komputasi penelusuran O(V+E) pada background setiap kali ada relasi baru yang ditambahkan. |

---

## Skenario Pengujian

### A. Pengujian Normal

| No | Skenario | Input / Tindakan | Ekspektasi Output |
|----|----------|-----------------|-------------------|
| 1 | Pencarian Topik Berdasarkan Prefix | User memasukkan keyword "Sistem" | Sistem menampilkan daftar: Sistem Operasi, Sistem Basis Data, dan Integrasi Sistem secara cepat. |
| 2 | Melihat Daftar Prasyarat Langsung (DFS) | User memilih "Pemrograman Web (ET234305)" dan meminta prasyaratnya | Sistem menampilkan: Algoritma dan Teknik Pemrograman, Struktur Data dan OOP, Sistem Basis Data. |
| 3 | Rekomendasi Urutan Belajar Lengkap (Topological Sort) | User meminta rute/urutan belajar dari awal hingga "Kriptografi (ET234501)" | Sistem mencetak urutan dari node awal tanpa in-degree, berurut secara logis tanpa bentrok hingga ke Kriptografi. |
| 4 | Penambahan Node (Insert Data) | Menambahkan mata kuliah baru "Komputasi Kuantum" dengan prasyarat "Fisika Dasar" | Node berhasil masuk ke dalam struktur Tree dan Graph. Node baru terbaca pada Topological Sort berikutnya. |
| 5 | Penghapusan Edge (Update/Delete Relasi) | Menghapus relasi prasyarat "Sistem Operasi" ke "Teknologi Komputasi Awan" | Edge terhapus. Saat dicek kembali, "Sistem Operasi" tidak lagi muncul sebagai prasyarat "Teknologi Komputasi Awan". |

### B. Pengujian Edge Case

| No | Skenario | Input / Tindakan | Ekspektasi Output (Error Handling) |
|----|----------|-----------------|-------------------------------------|
| 1 | Deteksi Siklus Prasyarat (HOTS) | Menambahkan relasi back-edge: Kriptografi (Semester 5) dijadikan prasyarat untuk Statistika (Semester 1) | Sistem menolak operasi dan menampilkan peringatan: "Terdeteksi Siklus! Mata kuliah tidak dapat menjadi prasyarat untuk mata kuliah dasarnya sendiri." |
| 2 | Pencarian Data Tidak Valid / Kosong | User memasukkan keyword acak yang tidak ada, seperti "Memasak" atau input string kosong | Program tidak crash. Sistem menampilkan pesan informatif: "Data/Topik tidak ditemukan." |
| 3 | Topik Terisolasi (Disconnected Graph) | Menambahkan mata kuliah "Bahasa Indonesia" tanpa memberikan relasi masuk maupun keluar | Sistem tetap mendeteksi node tersebut. Saat diminta prasyaratnya, sistem mencetak "Topik ini tidak memiliki prasyarat dan tidak memprasyaratkan topik lain." |
| 4 | Duplikasi Data (Insert Duplicate) | Menambahkan node dengan Kode MK yang sudah ada (misalnya memasukkan ET234201 lagi) | Sistem menolak data duplikat dan mencetak pesan: "Gagal: Kode MK sudah terdaftar dalam sistem." |
| 5 | Menghapus Node Utama (Cascade Dependency) | Mencoba menghapus "Sistem Operasi" yang memiliki banyak percabangan prasyarat | Sistem memberikan peringatan, lalu menghapus seluruh edge terkait dengan aman (cascade delete) sebelum menghapus node. |

---

## Penjelasan Source Code

### 1. `TrieNode.java` — Node Dasar Struktur Trie

`TrieNode` adalah unit terkecil dalam struktur Trie. Setiap node merepresentasikan satu karakter huruf dalam nama mata kuliah.

```java
public class TrieNode {
    TrieNode[] children;   // Array 26 pointer ke node anak (A-Z)
    boolean isEnd;         // Penanda apakah node ini adalah akhir sebuah kata
    String courseCode;     // Kode MK yang tersimpan di node akhir
    char character;        // Karakter yang direpresentasikan node ini
    static final int ALPHABET_SIZE = 26;
}
```

Setiap `TrieNode` memiliki array `children` berukuran 26, di mana indeks 0 merepresentasikan huruf 'A', indeks 1 merepresentasikan 'B', dan seterusnya hingga 'Z'. Mapping ini dilakukan dengan rumus `Character.toUpperCase(c) - 'A'`.

Atribut `isEnd` berfungsi sebagai penanda bahwa jalur dari akar (root) hingga node ini membentuk sebuah nama mata kuliah yang lengkap. Ketika `isEnd` bernilai `true`, atribut `courseCode` menyimpan kode MK yang bersangkutan, sehingga pencarian dapat langsung mengembalikan kode tersebut ke sistem Graph tanpa pencarian tambahan.

Kelas ini juga menyediakan tiga metode pembantu: `hasChild(char c)` untuk memeriksa apakah node anak untuk karakter tertentu sudah ada, `getChild(char c)` untuk mengambil node anak tersebut, dan `setChild(char c, TrieNode node)` untuk menetapkan node anak baru. Ketiga metode ini mengabstraksi operasi kalkulasi indeks array agar kode di lapisan atas lebih bersih dan mudah dibaca.

---

### 2. `Trie.java` — Kelas Utama Struktur Trie
 
`Trie.java` adalah kelas utama yang digunakan oleh seluruh sistem untuk operasi pencarian. Berbeda dengan `TrieNode` yang menggunakan array 26 elemen statis, `Trie.java` mengimplementasikan Trie menggunakan struktur inner class `Node` berbasis `HashMap<Character, Node>`, sehingga lebih fleksibel dalam menangani berbagai jenis karakter termasuk spasi.
 
```java
public class Trie {
    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        boolean isEnd = false;
    }
    private final Node root;
}
```
 
Setiap `Node` menyimpan `List<Integer> indices` yang berisi semua indeks mata kuliah di dalam `CourseGraph.courses[]` yang namanya melewati atau berakhir di node tersebut. Pendekatan ini memungkinkan satu node merepresentasikan lebih dari satu mata kuliah jika nama mereka identik.
 
**`insert(String nama, int index)`**
 
Menyisipkan nama mata kuliah karakter per karakter ke dalam Trie. Untuk setiap karakter, metode `computeIfAbsent` dari `HashMap` digunakan untuk membuat node baru hanya jika belum ada, tanpa pengecekan kondisi eksplisit. Setelah semua karakter diproses, node akhir ditandai `isEnd = true` dan indeks Graph ditambahkan ke `indices`.
 
**`autocomplete(String prefix)`**
 
Menelusuri Trie sepanjang karakter prefix. Jika pada titik tertentu karakter tidak ditemukan di `HashMap`, metode langsung mengembalikan list kosong. Jika seluruh prefix berhasil dipetakan, metode memanggil `kumpulkan()` secara rekursif pada subtree yang ditemukan.
 
**`kumpulkan(Node node, List<Integer> hasil)`**
 
Fungsi DFS internal yang menelusuri seluruh subtree dari sebuah node dan mengumpulkan semua indeks yang tersimpan di node-node yang `isEnd = true`. Hasil dikembalikan sebagai `List<Integer>` ke pemanggil.
 
---
 
### 3. `AdjNode.java` — Node Linked List Adjacency
 
`AdjNode` adalah unit pembentuk Linked List yang digunakan sebagai representasi daftar tetangga (adjacency list) pada setiap simpul Graph.
 
```java
public class AdjNode {
    int destIndex;  // Indeks tujuan di dalam array courses[]
    AdjNode next;   // Pointer ke AdjNode berikutnya
}
```
 
Alih-alih menyimpan kode MK (String) secara langsung, `AdjNode` hanya menyimpan `destIndex`, yaitu posisi integer dari mata kuliah tujuan di dalam array `CourseNode[] courses` pada `CourseGraph`. Pendekatan ini lebih efisien secara memori dan komputasi karena operasi perbandingan integer jauh lebih cepat dibandingkan perbandingan String.
 
Atribut `next` memungkinkan pembentukan Linked List berantai. Ketika sebuah mata kuliah memiliki lebih dari satu mata kuliah lanjutan, semua lanjutan tersebut tersambung dalam satu rantai Linked List yang dapat ditelusuri secara sekuensial.
 
---
 
### 4. `CourseNode.java` — Simpul Utama Graph (Vertex)
 
`CourseNode` adalah struktur data utama yang merepresentasikan satu mata kuliah sekaligus bertindak sebagai simpul (vertex) dalam Graf.
 
```java
public class CourseNode {
    String kode;        // Kode unik mata kuliah
    String nama;        // Nama lengkap mata kuliah
    String kategori;    // Bidang ilmu
    String level;       // Tingkat kesulitan
    int sks;            // Jumlah SKS
    int estimasiJam;    // Estimasi jam belajar
    int semester;       // Semester penawaran
    AdjNode adjList;    // Kepala Linked List adjacency (daftar MK lanjutan)
}
```
 
Satu `CourseNode` memuat tujuh atribut data mata kuliah sesuai ketentuan proyek (minimal 5 atribut selain ID dan nama), ditambah satu pointer `adjList` yang merupakan kepala dari Linked List adjacency milik simpul tersebut. Ketika `adjList` bernilai `null`, artinya mata kuliah ini tidak memiliki mata kuliah lanjutan yang memprasyaratkannya.
 
---
 
### 5. `CourseGraph.java` — Inti Struktur Graph
 
`CourseGraph` adalah kelas inti yang mengelola seluruh operasi pada struktur Directed Graph, mulai dari penyimpanan data, penambahan simpul dan sisi, hingga operasi hapus dan tampilkan.
 
```java
public class CourseGraph {
    static final int MAX = 30;
    CourseNode[] courses;  // Array statis penampung semua CourseNode
    int size;              // Jumlah MK yang tersimpan saat ini
}
```
 
**`findIndex(String kode)`**
Mencari indeks array dari sebuah kode MK dengan cara iterasi linear. Fungsi ini menjadi tulang punggung hampir semua operasi lain karena semua operasi Graph bekerja berdasarkan integer index, bukan String kode. Mengembalikan -1 jika tidak ditemukan.
 
**`addCourse(...)`**
Menambahkan mata kuliah baru ke dalam array `courses`. Sebelum menambahkan, fungsi ini memeriksa kapasitas array dan melakukan pengecekan duplikasi melalui `findIndex`. Jika kode sudah terdaftar, penambahan dibatalkan dan pesan kesalahan ditampilkan.
 
**`addEdge(String dariKode, String keKode)`**
Menambahkan sisi berarah dari satu MK ke MK lain dengan cara menyisipkan `AdjNode` baru di bagian depan Linked List adjacency milik `dariKode`. Penyisipan di depan (head insertion) dipilih karena kompleksitasnya O(1).
 
**`removeEdge(String dariKode, String keKode)`**
Menghapus satu sisi prasyarat dengan cara menelusuri Linked List adjacency milik `dariKode` dan memutus referensi ke `AdjNode` yang memiliki `destIndex` sesuai dengan `keKode`. Menggunakan dua pointer `curr` dan `prev` untuk melakukan penghapusan linked list yang aman.
 
**`removeCourse(String kode)`**
Menghapus sebuah mata kuliah beserta seluruh relasinya secara bertahap (cascade). Prosesnya terdiri dari tiga tahap: pertama, menghapus semua sisi yang mengarah masuk ke node target dari seluruh node lain; kedua, menggeser elemen array untuk mengisi kekosongan; ketiga, memperbarui semua nilai `destIndex` pada seluruh `AdjNode` yang nilainya lebih besar dari indeks yang dihapus agar tetap konsisten setelah pergeseran.
 
**`dfs(String kodeAwal)`**
Menjalankan DFS maju dari sebuah simpul awal dan menelusuri semua simpul yang dapat dicapai mengikuti arah edge. Menggunakan array boolean `visited` untuk mencegah kunjungan berulang.
 
**`displayGraph()`**
Menampilkan seluruh mata kuliah beserta daftar relasi keluaran (adjacency list) dalam format teks yang mudah dibaca.
 
---
 
### 6. `Fitur_DataLoader.java` — Pemuat Dataset dari CSV
 
`Fitur_DataLoader` bertanggung jawab untuk memuat data dari dua file CSV ke dalam memori, yaitu ke dalam struktur `CourseGraph` dan `Trie` secara bersamaan dalam satu pemanggilan fungsi.
 
```java
public static void load(String fileNode, String fileEdge, Trie trie, CourseGraph graph)
```
 
Proses pemuatan dibagi menjadi dua fase. Pada fase pertama, file `node.csv` dibaca baris per baris menggunakan `BufferedReader`. Baris pertama (header kolom) dilewati dengan `br.readLine()` awal. Setiap baris berikutnya dipecah dengan delimiter koma, kemudian data dimasukkan ke `graph.addCourse()`. Setelah itu, indeks simpul yang baru saja ditambahkan diambil dengan `graph.findIndex(kode)` dan nama mata kuliah dalam huruf kecil dimasukkan ke `trie.insert()` beserta indeks tersebut, sehingga kedua struktur data selalu sinkron.
 
Fase kedua membaca file `edge.csv` dengan cara yang sama dan memanggil `graph.addEdge()` untuk setiap baris relasi. Blok `try-catch` pada setiap fase memastikan program tidak crash meskipun file tidak ditemukan atau terdapat kesalahan format data, dengan mencetak pesan kesalahan yang informatif.
 
---
 
### 7. `Main.java` — Titik Masuk Program dan Menu Utama
 
`Main.java` adalah titik masuk program yang mengorkestrasi seluruh komponen sistem melalui antarmuka menu berbasis teks (CLI).
 
Pada saat inisialisasi, program membuat satu instance `CourseGraph` dan satu instance `Trie`, kemudian memanggil `Fitur_DataLoader.load()` untuk memuat dataset dari file CSV ke kedua struktur data tersebut secara bersamaan. Input yang tidak valid (bukan angka) ditangkap oleh blok `try-catch NumberFormatException` sehingga program tidak berhenti mendadak.
 
Program masuk ke dalam loop utama yang menampilkan 11 opsi menu:
 
| Menu | Fungsi | Kelas yang Dipanggil |
|------|--------|----------------------|
| 1 | Insert data (MK baru atau relasi baru) | `graph.insertMenu(sc)` |
| 2 | Search MK berdasarkan prefix (Smart Path Navigator) | `Fitur_SmartPath.jalankan(...)` |
| 3 | Update / Delete data | `graph.deleteMenu(sc)` |
| 4 | Tampilkan semua MK dan relasi Graph | `graph.displayGraph()` |
| 5 | DFS — Traversal rantai prasyarat mundur | `Fitur_DFSPrasyarat.cetak(...)` |
| 6 | Topological Sort — Rekomendasi urutan belajar | `Fitur_rekomendasibelajar.jalankan(graph)` |
| 7 | Cycle Detection — Deteksi siklus prasyarat | `Fitur_CycleDetection.jalankan(graph)` |
| 8 | Topik Terisolasi — Mata kuliah bebas prasyarat | `Fitur_IsolatedTopik.jalankan(graph)` |
| 9 | Tracing proses Trie (Simulasi Autocomplete) | `Fitur_TrieTracing.cetak()` |
| 10 | Tracing proses Graph (Simulasi DFS Mundur) | `Fitur_GraphTracing.cetak()` |
| 11 | Tracing Edge Case (Kondisi Ekstrem) | `Fitur_EdgeCaseTracing.cetak()` |
| 0 | Keluar | — |
 
---
 
### 8. `Fitur_SmartPath.java` — Smart Path Navigator (Gabungan Trie + Graph)
 
`Fitur_SmartPath` adalah fitur andalan yang memadukan kedua struktur data secara sinergis dalam satu alur eksekusi yang terintegrasi.
 
```java
public static void jalankan(String keyword, Trie trie, CourseGraph graph)
```
 
Alur eksekusi fitur ini terdiri dari tiga langkah utama. Pertama, sistem memvalidasi bahwa instance `Trie` tidak null sebelum melanjutkan. Jika valid, keyword yang dimasukkan pengguna dikonversi ke huruf kecil dan diumpankan ke `trie.autocomplete()`, yang mengembalikan `List<Integer>` berisi indeks semua mata kuliah yang cocok. Kedua, jika list kosong, sistem menampilkan pesan "Topik tidak ditemukan di database" dan berhenti. Jika ada hasil, semua mata kuliah yang cocok ditampilkan beserta kode dan namanya. Ketiga, indeks dari hasil teratas (index ke-0 pada list) secara otomatis diambil dan kodenya digunakan untuk memanggil `Fitur_DFSPrasyarat.cetak()`, sehingga rantai prasyarat mata kuliah tersebut langsung ditampilkan tanpa input tambahan dari pengguna.
 
---
 
### 9. `Fitur_DFSPrasyarat.java` — Penelusuran Rantai Prasyarat Mundur
 
`Fitur_DFSPrasyarat` mengimplementasikan DFS yang berjalan **mundur** (backward traversal) dari sebuah mata kuliah target untuk menemukan semua mata kuliah yang harus diselesaikan terlebih dahulu.
 
```java
public static void cetak(String kodeTarget, CourseGraph graph)
private static void dfsMundur(int currentIdx, CourseGraph graph, boolean[] visited, int kedalaman)
```
 
Berbeda dengan DFS konvensional yang mengikuti arah edge keluar, DFS mundur bekerja dengan cara memindai seluruh `adjList` milik setiap node untuk mencari node mana saja yang memiliki edge yang mengarah ke `currentIdx`. Setiap node yang ditemukan ditampilkan dengan indentasi (`"   ".repeat(kedalaman)`) untuk merepresentasikan kedalaman prasyarat, kemudian ditelusuri lebih lanjut secara rekursif. Array boolean `visited` digunakan untuk mencegah loop tak terbatas pada graf yang kompleks.
 
---
 
### 10. `Fitur_Prerequisites.java` — Tampilan Prasyarat Terstruktur
 
`Fitur_Prerequisites` menyediakan dua mode tampilan prasyarat yang lebih terstruktur dan informatif dengan format visual menggunakan karakter box-drawing.
 
**Mode 1: Prasyarat Langsung (1 Level)**
 
Fungsi `tampilkanPrasyarat()` menampilkan hanya mata kuliah yang secara langsung menjadi prasyarat dari target. Setiap prasyarat ditampilkan dalam satu baris terformat menggunakan `System.out.printf` dengan kolom kode, nama, semester, level, dan SKS. Di bagian akhir, sistem mencetak total jumlah prasyarat yang ditemukan, atau pesan khusus jika mata kuliah tersebut tidak memiliki prasyarat.
 
**Mode 2: Rantai Prasyarat Lengkap (Semua Level)**
 
Fungsi `tampilkanRantaiPrasyarat()` memanggil `rantaiRekursif()` secara rekursif untuk menelusuri seluruh rantai prasyarat hingga ke akar (mata kuliah yang tidak memiliki prasyarat lagi). Hasilnya ditampilkan dalam format pohon hierarkis dengan karakter `└─` dan indentasi bertingkat yang merepresentasikan kedalaman prasyarat.
 
Akses ke fitur ini tersedia melalui fungsi `menu()` yang menerima input pemilihan mode dari pengguna, kemudian memanggil fungsi yang sesuai.
 
---
 
### 11. `Fitur_rekomendasibelajar.java` — Urutan Belajar via Topological Sort
 
`Fitur_rekomendasibelajar` mengimplementasikan Kahn's Algorithm untuk menghasilkan urutan belajar mata kuliah yang valid dari awal hingga akhir.
 
```java
public static void jalankan(CourseGraph graph)
```
 
Eksekusi dimulai dengan menghitung array `inDegree[]` melalui iterasi seluruh adjacency list setiap node. Selanjutnya, semua node dengan in-degree nol dimasukkan ke dalam antrian menggunakan `java.util.LinkedList` yang diinstansiasi sebagai `Queue<Integer>`, memastikan proses enqueue dan dequeue berjalan secara efisien. Setiap node yang dikeluarkan dari antrian (`queue.poll()`) ditampilkan sebagai satu langkah urutan belajar bernomor, dan in-degree semua tetangganya dikurangi satu melalui iterasi adjacency list node tersebut. Jika in-degree sebuah tetangga mencapai nol, tetangga tersebut langsung dimasukkan ke antrian untuk diproses berikutnya.
 
---
 
### 12. `Fitur_CycleDetection.java` — Deteksi Siklus Prasyarat
 
`Fitur_CycleDetection` mengimplementasikan Cycle Detection berbasis DFS dengan tiga state node untuk mendeteksi relasi prasyarat yang mustahil secara logika (melingkar).
 
```java
public static void jalankan(CourseGraph graph)
private static boolean dfsMaju(int u, CourseGraph graph, int[] state)
```
 
Array `state[]` integer digunakan sebagai penanda kondisi setiap node: nilai `0` untuk `UNVISITED`, nilai `1` untuk `VISITING`, dan nilai `2` untuk `VISITED`. Fungsi `jalankan()` mengiterasi semua node dan memanggil `dfsMaju()` untuk setiap node yang masih `UNVISITED`, memastikan seluruh komponen graph yang mungkin tidak terhubung tetap diperiksa.
 
Di dalam `dfsMaju()`, sebelum menelusuri tetangga, state node diubah ke `VISITING`. Jika saat menelusuri ditemukan tetangga yang juga berstatus `VISITING`, artinya ada jalur yang kembali ke node yang sedang ditelusuri sehingga siklus langsung terdeteksi dan fungsi mengembalikan `true`. Setelah semua tetangga selesai diproses, state node diubah ke `VISITED` (nilai 2). Hasil akhir ditampilkan sebagai `[AMAN] Tidak ditemukan siklus` atau `[WARNING] Ditemukan siklus prasyarat`.
 
---
 
### 13. `Fitur_IsolatedTopik.java` — Deteksi Topik Terisolasi
 
`Fitur_IsolatedTopik` mengidentifikasi mata kuliah yang berdiri sendiri, yaitu mata kuliah yang tidak memiliki prasyarat (in-degree nol) sekaligus tidak memprasyaratkan mata kuliah lain (out-degree nol).
 
```java
public static void jalankan(CourseGraph graph)
```
 
Prosesnya dilakukan dalam satu iterasi tunggal yang menghitung in-degree dan out-degree secara bersamaan. Untuk setiap node `i`, sistem menelusuri `adjList`-nya: setiap `AdjNode` yang ditemukan menambah `outDegree[i]` sekaligus menambah `inDegree[destIndex]`. Setelah iterasi selesai, sistem memeriksa setiap node dan menampilkan yang memiliki kedua nilai sama dengan nol. Jika tidak ada node terisolasi yang ditemukan, sistem mencetak pesan konfirmasi. Fitur ini berguna untuk mendeteksi mata kuliah yang salah dikonfigurasi atau memang berdiri sendiri.
 
---
 
### 14. `Fitur_SearchTopik.java` — Pencarian Topik via Trie
 
`Fitur_SearchTopik` menyediakan antarmuka pencarian mandiri berbasis prefix menggunakan Trie dengan tampilan hasil dalam format tabel berstruktur.
 
```java
public static void menu(Trie trie, CourseGraph graph, java.util.Scanner sc)
```
 
Tampilan menggunakan karakter box-drawing (`╔`, `║`, `╚`) untuk membingkai output secara visual. Setelah pengguna memasukkan keyword, sistem memvalidasi bahwa instance `Trie` tidak null, kemudian memanggil `trie.autocomplete()` dengan keyword dalam huruf kecil. Jika hasil kosong, pesan "Data/Topik tidak ditemukan" ditampilkan. Jika ada hasil, sistem mencetak tabel berformat kolom No, Kode MK, Nama Mata Kuliah, Level, dan Semester menggunakan `System.out.printf`. Di akhir, jumlah total mata kuliah yang ditemukan ditampilkan sebagai ringkasan.
 
---
 
### 15. `Fitur_TrieTracing.java` — Tracing Simulasi Proses Trie
 
`Fitur_TrieTracing` adalah fitur demonstrasi yang menampilkan log simulasi step-by-step dari proses autocomplete Trie untuk keyword `"sis"` secara hardcoded dan terstruktur.
 
```java
public static void cetak()
```
 
Simulasi dibagi menjadi tiga fase yang ditampilkan secara berurutan. **Fase 1** menampilkan proses penelusuran prefix karakter per karakter: sistem menunjukkan bahwa pointer berhasil turun dari ROOT ke Node('s'), lalu ke Node('i'), lalu ke Node('s'), dengan konfirmasi bahwa setiap node ditemukan di `children` node sebelumnya.
 
**Fase 2** menampilkan proses Pre-Order Traversal pada subtree setelah prefix ditemukan: sistem menunjukkan bahwa node 's' terakhir bukan `isEndOfWord`, kemudian menemukan cabang lanjutan `'t' -> 'e' -> 'm'` yang mencapai node akhir dengan `isEndOfWord = true`, lalu mengumpulkan CourseID (Index 3 dan Index 8) ke dalam Result List.
 
**Fase 3** menampilkan proses backtracking: sistem mengonfirmasi bahwa tidak ada cabang lain yang aktif dan traversal selesai, kemudian Result List dikembalikan ke Smart Path Navigator.
 
---
 
### 16. `Fitur_GraphTracing.java` — Tracing Simulasi Proses DFS Mundur
 
`Fitur_GraphTracing` menampilkan log simulasi step-by-step dari proses DFS Mundur (backward traversal) untuk mencari prasyarat mata kuliah "Struktur Data (Index 5)" secara hardcoded.
 
```java
public static void cetak()
```
 
Simulasi dibagi menjadi tiga fase. **Fase 1** menampilkan pemanggilan awal `dfsMundur(Index 5)`: sistem mengonfirmasi bahwa `visited[5]` disetel ke `true` dan pemindaian seluruh node dimulai untuk mencari incoming edge yang menuju Index 5.
 
**Fase 2** menampilkan proses rekursi dan penelusuran adjacency list: sistem menemukan bahwa Node(Index 1) memiliki edge yang mengarah ke Node(Index 5), memeriksa `visited[1]` yang bernilai `false`, mencetak hasil "Wajib Lulus: Algoritma Pemrograman", kemudian memanggil `dfsMundur(Index 1)` secara rekursif. Rekursi kedua menetapkan `visited[1] = true` dan menemukan bahwa tidak ada incoming edge ke Index 1.
 
**Fase 3** menampilkan proses backtracking: `dfsMundur(Index 1)` selesai dan dikembalikan ke `dfsMundur(Index 5)`, yang kemudian melanjutkan pemindaian dan mengonfirmasi tidak ada edge lain yang menuju Index 5, sehingga call stack kosong dan algoritma selesai.
 
---
 
### 17. `Fitur_EdgeCaseTracing.java` — Tracing Simulasi Kondisi Input Tidak Valid
 
`Fitur_EdgeCaseTracing` menampilkan log simulasi step-by-step dari penanganan kondisi ekstrem berupa pencarian keyword yang tidak ada dalam database, menggunakan input `"zzz"` sebagai contoh kasus.
 
```java
public static void cetak()
```
 
Simulasi dibagi menjadi dua fase. **Fase 1** menampilkan eksekusi `Trie.autocomplete("zzz")`: sistem menunjukkan bahwa pointer dimulai di ROOT, kemudian saat membaca karakter pertama 'z', pengecekan `root.children['z']` mengembalikan NULL karena cabang tersebut tidak pernah dibuat selama proses insert dataset. Iterasi langsung dihentikan dan fungsi Trie mengembalikan `List<Integer>` yang kosong.
 
**Fase 2** menampilkan penanganan oleh Smart Path Navigator: sistem menunjukkan bahwa list kosong diterima dan kondisi `if (hasilPencarian.isEmpty())` terpenuhi, sehingga pemanggilan ke Graph dan DFS dibatalkan sepenuhnya. Pesan "Topik tidak ditemukan di database" dicetak ke terminal. Simulasi dikonfirmasi berhasil menghindari `NullPointerException` dan sistem tetap dalam kondisi aman.
 
---

## Cara Menjalankan Program

### Prasyarat

- Java Development Kit (JDK) versi 8 atau lebih baru
- Terminal atau Command Prompt

### Langkah Kompilasi dan Eksekusi

**1. Clone atau unduh repository ini**

```bash
git clone https://github.com/[username]/Final-Project-Strukdat.git
cd Final-Project-Strukdat
```

**2. Kompilasi seluruh file Java**

```bash
javac src/*.java -d out/
```

**3. Jalankan program**

```bash
java -cp out Main
```

Pastikan direktori `data/` yang berisi `node.csv` dan `edge.csv` berada di direktori kerja yang sama saat program dijalankan.

### Contoh Alur Penggunaan
 
```
========================================
   LIBRARY KNOWLEDGE NAVIGATOR
========================================
1.  Insert data (MK baru / relasi baru)
2.  Search MK berdasarkan prefix
3.  Update / Delete data
4.  Tampilkan semua MK & Graph
5.  DFS - Traversal rantai prasyarat
6.  Topological Sort - Urutan belajar
7.  Cycle Detection (Deteksi Siklus)
8.  Topik Terisolasi (Bebas Prasyarat)
9.  Tampilkan Prasyarat Sebuah Topik (Langsung & Rantai)
10. Tracing Proses Graph (Simulasi DFS)
11. Tracing Edge Case (Kondisi Ekstrem)
12. Tracing Proses Trie (Simulasi Autocomplete)
0.  Keluar
Pilih menu: 0

Menyimpan data sebelum keluar...
[OK] Data Mata Kuliah permanen disimpan ke: data/node.csv
[OK] Data Relasi Prasyarat permanen disimpan ke: data/edge.csv
Terima kasih telah menggunakan Library Knowledge Navigator.
```
---

## Catatan 

Proyek ini dikerjakan menggunakan bahasa pemrograman Java murni. Seluruh struktur data inti (Trie, Linked List, Graph) diimplementasikan secara manual tanpa menggunakan library graph siap pakai, sesuai ketentuan proyek. Penggunaan `java.util` dibatasi hanya pada beberapa komponen pendukung seperti `Scanner` untuk input, `ArrayList` pada fitur tracing, dan `Queue` pada implementasi Kahn's Algorithm di fitur GraphTracing.
