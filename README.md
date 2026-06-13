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
│   ├── CourseNode.java
│   ├── AdjNode.java
│   ├── TrieNode.java
│   ├── CourseGraph.java
│   ├── Fitur_DataLoader.java
│   ├── Fitur_SmartPath.java
│   ├── Fitur_DFSPrasyarat.java
│   ├── Fitur_Prerequisites.java
│   ├── Fitur_rekomendasibelajar.java
│   ├── Fitur_CycleDetection.java
│   ├── Fitur_IsolatedTopik.java
│   ├── Fitur_searchtopik.java
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

Dataset dibuat secara mandiri oleh kelompok dan merepresentasikan kurikulum fiktif program studi Rekayasa Teknologi Informasi. Dataset terdiri dari dua file CSV.

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

### 2. `AdjNode.java` — Node Linked List Adjacency

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

### 3. `CourseNode.java` — Simpul Utama Graph (Vertex)

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

### 4. `CourseGraph.java` — Inti Struktur Graph

`CourseGraph` adalah kelas inti yang mengelola seluruh operasi pada struktur Directed Graph, mulai dari penyimpanan data, penambahan simpul dan sisi, hingga operasi hapus dan tampilkan.

```java
public class CourseGraph {
    static final int MAX = 30;
    CourseNode[] courses;  // Array statis penampung semua CourseNode
    int size;              // Jumlah MK yang tersimpan saat ini
}
```

**Operasi Utama:**

**`findIndex(String kode)`**
Mencari indeks array dari sebuah kode MK. Fungsi ini menjadi tulang punggung hampir semua operasi lain karena semua operasi Graph bekerja berdasarkan integer index, bukan String kode.

**`addCourse(...)`**
Menambahkan mata kuliah baru ke dalam array `courses`. Sebelum menambahkan, fungsi ini memeriksa kapasitas array dan melakukan pengecekan duplikasi melalui `findIndex`. Jika kode sudah terdaftar, penambahan dibatalkan dan pesan kesalahan ditampilkan.

**`addEdge(String dariKode, String keKode)`**
Menambahkan sisi berarah dari satu MK ke MK lain dengan cara menyisipkan `AdjNode` baru di bagian depan Linked List adjacency milik `dariKode`. Penyisipan di depan (head insertion) dipilih karena kompleksitasnya O(1).

**`removeEdge(String dariKode, String keKode)`**
Menghapus satu sisi prasyarat dengan cara menelusuri Linked List adjacency milik `dariKode` dan memutus referensi ke `AdjNode` yang memiliki `destIndex` sesuai dengan `keKode`.

**`removeCourse(String kode)`**
Menghapus sebuah mata kuliah beserta seluruh relasinya secara bertahap (cascade). Prosesnya terdiri dari tiga tahap: pertama, menghapus semua sisi yang mengarah masuk ke node target dari seluruh node lain; kedua, menggeser elemen array untuk mengisi kekosongan; ketiga, memperbarui semua nilai `destIndex` pada seluruh `AdjNode` agar tetap konsisten setelah pergeseran indeks.

**`dfs(String kodeAwal)`**
Menjalankan DFS dari sebuah simpul awal dan menelusuri semua simpul yang dapat dicapai dari simpul tersebut. Menggunakan array boolean `visited` untuk mencegah kunjungan berulang pada simpul yang sama.

**`displayGraph()`**
Menampilkan seluruh mata kuliah beserta daftar relasi keluaran (adjacency list) dalam format teks yang mudah dibaca.

---

### 5. `Fitur_DataLoader.java` — Pemuat Dataset dari CSV

`Fitur_DataLoader` bertanggung jawab untuk memuat data dari dua file CSV ke dalam memori, yaitu ke dalam struktur `CourseGraph` dan `Trie` secara bersamaan.

```java
public static void load(String fileNode, String fileEdge, Trie trie, CourseGraph graph)
```

Proses pemuatan dibagi menjadi dua fase. Pada fase pertama, file `node.csv` dibaca baris per baris menggunakan `BufferedReader`. Setiap baris dipecah menggunakan delimiter koma, kemudian data dimasukkan ke `graph.addCourse()` dan nama mata kuliah (dalam huruf kecil) dimasukkan ke `trie.insert()` beserta indeksnya di Graph. Fase kedua membaca file `edge.csv` dan memanggil `graph.addEdge()` untuk setiap baris relasi yang ditemukan.

Mekanisme `try-catch` pada setiap fase memastikan program tidak crash meskipun file tidak ditemukan atau terdapat kesalahan format data.

---

### 6. `Main.java` — Titik Masuk Program dan Menu Utama

`Main.java` adalah titik masuk program yang mengorkestrasi seluruh komponen sistem melalui antarmuka menu berbasis teks (CLI).

Pada saat inisialisasi, program membuat satu instance `CourseGraph` dan satu instance `Trie`, kemudian memanggil `Fitur_DataLoader.load()` untuk memuat dataset dari file CSV ke kedua struktur data tersebut secara bersamaan.

Program kemudian masuk ke dalam loop utama yang menampilkan 12 opsi menu:

| Menu | Fungsi |
|------|--------|
| 1 | Insert data (MK baru atau relasi baru) |
| 2 | Search MK berdasarkan prefix (Smart Path Navigator) |
| 3 | Update / Delete data |
| 4 | Tampilkan semua MK dan relasi Graph |
| 5 | DFS — Traversal rantai prasyarat |
| 8 | Tampilkan prasyarat sebuah topik |
| 9 | Tracing proses Trie step-by-step |
| 10 | Deteksi siklus prasyarat |
| 11 | Tracing proses Graph step-by-step |
| 12 | Tracing Edge Case Graph |
| 0 | Keluar |

Pemilihan menu menggunakan `switch-case` dan setiap kasus mendelegasikan eksekusi ke kelas fitur yang bersangkutan.

---

### 7. `Fitur_SmartPath.java` — Smart Path Navigator (Gabungan Trie + Graph)

`Fitur_SmartPath` adalah fitur andalan yang memadukan kedua struktur data secara sinergis dalam satu alur eksekusi yang terintegrasi.

```java
public static void jalankan(String keyword, Trie trie, CourseGraph graph)
```

Alur eksekusi fitur ini terdiri dari tiga langkah utama. Pertama, keyword yang dimasukkan pengguna dikonversi ke huruf kecil dan diumpankan ke metode `trie.autocomplete()`. Trie kemudian menelusuri node-node prefix dan mengumpulkan semua `destIndex` dari mata kuliah yang cocok. Kedua, daftar mata kuliah yang ditemukan ditampilkan kepada pengguna. Ketiga, indeks dari hasil teratas secara otomatis diambil dan kodenya digunakan untuk memanggil `Fitur_DFSPrasyarat.cetak()`, sehingga rantai prasyarat mata kuliah tersebut langsung ditampilkan tanpa input tambahan dari pengguna.

---

### 8. `Fitur_DFSPrasyarat.java` — Penelusuran Rantai Prasyarat Mundur

`Fitur_DFSPrasyarat` mengimplementasikan DFS yang berjalan **mundur** (backward traversal) dari sebuah mata kuliah target untuk menemukan semua mata kuliah yang harus diselesaikan terlebih dahulu.

```java
public static void cetak(String kodeTarget, CourseGraph graph)
private static void dfsMundur(int currentIdx, CourseGraph graph, boolean[] visited, int kedalaman)
```

Berbeda dengan DFS konvensional yang mengikuti arah edge, DFS mundur bekerja dengan cara memindai seluruh `adjList` milik setiap node untuk mencari node mana saja yang memiliki edge yang mengarah ke `currentIdx`. Setiap node yang ditemukan ditampilkan dengan indentasi sesuai kedalamannya dan kemudian ditelusuri lebih lanjut secara rekursif. Array boolean `visited` digunakan untuk mencegah loop tak terbatas pada graf yang kompleks.

---

### 9. `Fitur_Prerequisites.java` — Tampilan Prasyarat Terstruktur

`Fitur_Prerequisites` menyediakan dua mode tampilan prasyarat yang lebih terstruktur dan informatif.

**Mode 1: Prasyarat Langsung (1 Level)**

Fungsi `tampilkanPrasyarat()` menampilkan hanya mata kuliah yang secara langsung menjadi prasyarat dari target, beserta atribut lengkapnya (kode, nama, semester, level, SKS) dalam format tabel yang rapi.

**Mode 2: Rantai Prasyarat Lengkap (Semua Level)**

Fungsi `tampilkanRantaiPrasyarat()` menggunakan pendekatan rekursif `rantaiRekursif()` untuk menelusuri seluruh rantai prasyarat hingga ke akar (mata kuliah semester pertama yang tidak memiliki prasyarat lagi). Hasilnya ditampilkan dalam format pohon hierarkis dengan indentasi yang merepresentasikan kedalaman prasyarat.

---

### 10. `Fitur_rekomendasibelajar.java` — Urutan Belajar via Topological Sort

`Fitur_RekomendasiBelajar` mengimplementasikan Kahn's Algorithm untuk menghasilkan urutan belajar mata kuliah yang valid dari awal hingga akhir.

```java
public static void menu(CourseGraph graph, java.util.Scanner sc)
```

Eksekusi dimulai dengan menghitung `in-degree` (jumlah prasyarat masuk) dari setiap node melalui iterasi seluruh adjacency list. Selanjutnya, semua node dengan in-degree nol dimasukkan ke dalam antrian awal menggunakan array integer manual (bukan `java.util.Queue`) untuk menjaga konsistensi dengan arsitektur sistem yang menghindari penggunaan library berlebihan. Setiap node yang keluar dari antrian ditampilkan sebagai satu langkah urutan belajar, dan in-degree semua tetangganya dikurangi satu. Jika in-degree sebuah tetangga mencapai nol, tetangga tersebut masuk ke antrian. Jika pada akhir eksekusi jumlah node yang terproses kurang dari total node, sistem memberikan peringatan bahwa ada siklus yang mencegah beberapa mata kuliah untuk bisa diurutkan.

---

### 11. `Fitur_CycleDetection.java` — Deteksi Siklus Prasyarat

`Fitur_CycleDetection` mengimplementasikan Cycle Detection berbasis DFS dengan tiga state node untuk mendeteksi relasi prasyarat yang mustahil secara logika (melingkar).

```java
static final int UNVISITED = 0;
static final int VISITING  = 1;
static final int VISITED   = 2;
```

Setiap node memiliki salah satu dari tiga state. `UNVISITED` berarti node belum diproses sama sekali. `VISITING` berarti node sedang dalam proses penelusuran (berada di call stack rekursi saat ini). `VISITED` berarti node telah selesai diproses sepenuhnya.

Siklus terdeteksi ketika DFS menemukan sebuah edge yang mengarah ke node berstatus `VISITING`, yang disebut **back-edge**. Ini berarti ada jalur dari node tersebut kembali ke dirinya sendiri, membentuk sebuah siklus.

Fitur ini memiliki dua modus operasi. Modus pertama menjalankan deteksi pada seluruh graph dengan menampilkan log transisi state setiap node secara rinci (step-by-step). Modus kedua memungkinkan pengguna menambahkan sebuah edge sementara dan langsung memeriksa apakah edge tersebut menimbulkan siklus. Jika iya, edge tersebut otomatis dibatalkan dan dihapus.

---

### 12. `Fitur_IsolatedTopik.java` — Deteksi Topik Terisolasi

`Fitur_IsolatedTopik` mengidentifikasi mata kuliah yang berdiri sendiri, yaitu mata kuliah yang tidak memiliki prasyarat (in-degree nol) sekaligus tidak memprasyaratkan mata kuliah lain (out-degree nol).

Prosesnya dimulai dengan menghitung in-degree semua node melalui iterasi adjacency list. Kemudian, untuk setiap node, out-degree dihitung dengan menghitung panjang linked list adjacency-nya. Node yang memiliki keduanya bernilai nol dikategorikan sebagai terisolasi dan ditampilkan dalam format tabel. Fitur ini penting untuk mendeteksi mata kuliah yang salah dikonfigurasi atau memang berdiri sendiri seperti mata kuliah pilihan bebas.

---

### 13. `Fitur_searchtopik.java` — Pencarian Topik via Trie

`Fitur_SearchTopik` menyediakan antarmuka pencarian berbasis prefix menggunakan Trie dan menampilkan hasilnya dalam format tabel yang informatif.

```java
public static void menu(Trie trie, CourseGraph graph, java.util.Scanner sc)
```

Setelah pengguna memasukkan keyword, fungsi `trie.autocomplete()` dipanggil dengan keyword dalam huruf kecil. Hasilnya berupa daftar indeks integer yang kemudian digunakan untuk mengambil data lengkap dari `graph.courses[]` dan menampilkannya dalam tabel berkolom kode MK, nama, level, dan semester. Jika tidak ada hasil yang ditemukan, sistem menampilkan pesan informatif tanpa menyebabkan program berhenti.

---

### 14. `Fitur_TrieTracing.java` — Tracing Proses Trie Step-by-Step

`Fitur_TrieTracing` adalah fitur edukasi yang menampilkan proses internal operasi Trie secara visual dan bertahap, memudahkan pemahaman cara kerja struktur data ini.

Kelas ini memiliki implementasi Trie mandiri dengan `TrieNode` sebagai akar dan mendukung dua sub-fitur utama.

**Sub-Fitur Tracing Insert:**

Fungsi `insert(String namaMK, String kodeMK, boolean withTrace)` dengan parameter `withTrace = true` akan menampilkan setiap langkah proses insert secara terperinci. Untuk setiap karakter dalam nama mata kuliah, sistem menampilkan nomor step, karakter yang diproses, status node (BUAT NODE BARU atau NODE SUDAH ADA), dan jalur penelusuran dari root hingga posisi saat ini. Di akhir proses, sistem mengonfirmasi penandaan `isEnd = true` dan penyimpanan kode MK.

**Sub-Fitur Tracing Search Prefix:**

Fungsi `searchPrefix(String prefix)` membagi proses pencarian menjadi dua fase yang ditampilkan secara eksplisit. Fase pertama menampilkan penelusuran karakter prefix satu per satu, dengan status apakah node untuk karakter tersebut ditemukan atau tidak. Jika pada titik tertentu node tidak ditemukan, pencarian berhenti dan pesan "tidak ditemukan" ditampilkan. Fase kedua, jika prefix berhasil ditemukan, menampilkan proses DFS pada subtree Trie untuk mengumpulkan semua nama mata kuliah lengkap yang berawalan prefix tersebut.

---

### 15. `Fitur_GraphTracing.java` — Tracing Proses Graph Step-by-Step

`Fitur_GraphTracing` menampilkan proses eksekusi dua algoritma graph utama secara visual dan bertahap, berguna untuk keperluan demonstrasi dan pemahaman algoritma.

**Sub-Fitur 13A: Tracing DFS**

Fungsi `tracingDFS(CourseGraph g, String startKode)` menampilkan setiap langkah DFS dari sebuah simpul awal. Untuk setiap tindakan yang terjadi (VISIT, PUSH/EXPLORE, SUDAH VISIT, BACKTRACK), sistem mencetak nomor step, tindakan, kode MK, dan nama MK dengan indentasi yang merepresentasikan kedalaman rekursi. Di akhir, urutan kunjungan ditampilkan secara lengkap.

**Sub-Fitur 13B: Tracing Topological Sort (Kahn's Algorithm)**

Fungsi `tracingTopologicalSort(CourseGraph g)` membagi proses Kahn's Algorithm menjadi dua fase yang ditampilkan secara terperinci. Fase pertama menampilkan in-degree awal setiap node beserta keterangannya (masuk antrian awal atau menunggu sejumlah prasyarat). Fase kedua menampilkan setiap langkah proses BFS: node mana yang di-dequeue, tetangga mana saja yang in-degree-nya berkurang, dan node mana yang baru masuk ke antrian. Hasil akhir berupa urutan belajar yang valid ditampilkan dalam format tabel.

---

### 16. `Fitur_EdgeCaseTracing.java` — Tracing Lima Kondisi Ekstrem Graph

`Fitur_EdgeCaseTracing` adalah fitur pengujian komprehensif yang mensimulasikan lima kondisi ekstrem pada graph secara live untuk membuktikan ketahanan sistem (fault tolerance).

**Edge Case 1: Node Terisolasi**

Membuat graph mini dengan satu node terisolasi (tanpa edge) dan dua node normal yang saling terhubung. Sistem menampilkan in-degree dan out-degree setiap node, lalu menjalankan DFS dari node terisolasi untuk membuktikan bahwa DFS hanya mengunjungi satu node tanpa error.

**Edge Case 2: Siklus (Cycle Detection)**

Membuat graph dengan siklus A → B → C → A. Sistem menampilkan bahwa semua node memiliki in-degree lebih dari nol, sehingga antrian awal Kahn's Algorithm kosong. Hasilnya, tidak ada satupun node yang dapat diproses, dan sistem mengonfirmasi bahwa jumlah node terproses (0) lebih sedikit dari total node (3), menandakan keberadaan siklus.

**Edge Case 3: Kode MK Tidak Valid**

Mensimulasikan input kode MK fiktif "ET999999" ke dalam `findIndex()`. Sistem menampilkan hasil pencarian yang bernilai -1 dan mengonfirmasi bahwa DFS dibatalkan dengan aman tanpa memunculkan `NullPointerException`.

**Edge Case 4: Graph Kosong**

Menjalankan Topological Sort pada sebuah instance `CourseGraph` yang baru dibuat tanpa node apapun. Sistem membuktikan bahwa operasi berjalan selesai tanpa error, dengan hasil berupa list kosong, dan tidak ada `ArrayIndexOutOfBoundsException` yang terjadi.

**Edge Case 5: Self-Loop**

Membuat node yang memiliki edge ke dirinya sendiri (self-loop). Sistem menampilkan bahwa in-degree node tersebut tidak akan pernah bisa mencapai nol, sehingga node tersebut tidak akan pernah masuk ke antrian Kahn's Algorithm dan tidak akan pernah terproses, yang merupakan perilaku yang benar dan diharapkan.

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
...
Pilih menu: 2
Masukkan awalan topik (contoh: 'Sistem'): Sistem

=== SMART PATH NAVIGATOR ===
Ditemukan 3 kecocokan mata kuliah:
- [ET234201] Sistem Operasi
- [ET234204] Sistem Basis Data
- [ET234406] Integrasi Sistem

Menampilkan pohon prasyarat untuk hasil teratas:
Menelusuri rantai prasyarat mundur untuk: Sistem Operasi
   -> Wajib Lulus: [ET234102] Arsitektur dan Organisasi Komputer
```

---

## Catatan 

Proyek ini dikerjakan menggunakan bahasa pemrograman Java murni. Seluruh struktur data inti (Trie, Linked List, Graph) diimplementasikan secara manual tanpa menggunakan library graph siap pakai, sesuai ketentuan proyek. Penggunaan `java.util` dibatasi hanya pada beberapa komponen pendukung seperti `Scanner` untuk input, `ArrayList` pada fitur tracing, dan `Queue` pada implementasi Kahn's Algorithm di fitur GraphTracing.
