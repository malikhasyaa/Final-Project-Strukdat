public class CourseNode {
    // Atribut mata kuliah (dari dataset)
    String kode;
    String nama;
    String kategori;
    String level;
    int sks;
    int estimasiJam;
    int semester;

    // Pointer ke linked list adjacency (daftar MK yang butuh ini sebagai prasyarat)
    AdjNode adjList;

    // Constructor
    public CourseNode(String kode, String nama, String kategori,
                      String level, int sks, int estimasiJam, int semester) {
        this.kode       = kode;
        this.nama       = nama;
        this.kategori   = kategori;
        this.level      = level;
        this.sks        = sks;
        this.estimasiJam= estimasiJam;
        this.semester   = semester;
        this.adjList    = null; // belum punya tetangga
    }
}