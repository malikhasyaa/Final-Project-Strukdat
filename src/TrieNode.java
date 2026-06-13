public class TrieNode {
    TrieNode[] children;   
    boolean isEnd;         
    String courseCode;    
    char character;        

    static final int ALPHABET_SIZE = 26;

    public TrieNode(char c) {
        this.character  = c;
        this.children   = new TrieNode[ALPHABET_SIZE];
        this.isEnd      = false;
        this.courseCode = null;
    }

    // Cek apakah ada child untuk karakter tertentu
    public boolean hasChild(char c) {
        int idx = Character.toUpperCase(c) - 'A';
        if (idx < 0 || idx >= ALPHABET_SIZE) return false;
        return children[idx] != null;
    }

    // Ambil child untuk karakter tertentu
    public TrieNode getChild(char c) {
        int idx = Character.toUpperCase(c) - 'A';
        if (idx < 0 || idx >= ALPHABET_SIZE) return null;
        return children[idx];
    }

    // Set child untuk karakter tertentu
    public void setChild(char c, TrieNode node) {
        int idx = Character.toUpperCase(c) - 'A';
        if (idx >= 0 && idx < ALPHABET_SIZE) {
            children[idx] = node;
        }
    }
}
