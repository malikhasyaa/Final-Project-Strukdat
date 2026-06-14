import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        List<Integer> indices = new ArrayList<>(); // index di graph.courses[] yang berakhir/lewat di sini
        boolean isEnd = false;
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    // Masukkan nama MK beserta index-nya di CourseGraph
    public void insert(String nama, int index) {
        if (nama == null) return;
        Node curr = root;
        for (int i = 0; i < nama.length(); i++) {
            char c = nama.charAt(i);
            curr = curr.children.computeIfAbsent(c, k -> new Node());
        }
        curr.isEnd = true;
        curr.indices.add(index);
    }

    // Cari semua index MK yang namanya mengandung/cocok dengan prefix tertentu
    public List<Integer> autocomplete(String prefix) {
        List<Integer> hasil = new ArrayList<>();
        if (prefix == null) return hasil;

        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            Node next = curr.children.get(c);
            if (next == null) {
                return hasil; // prefix tidak ditemukan
            }
            curr = next;
        }

        kumpulkan(curr, hasil);
        return hasil;
    }

    // DFS mengumpulkan semua index pada subtree node tertentu
    private void kumpulkan(Node node, List<Integer> hasil) {
        if (node.isEnd) {
            hasil.addAll(node.indices);
        }
        for (Node child : node.children.values()) {
            kumpulkan(child, hasil);
        }
    }
}