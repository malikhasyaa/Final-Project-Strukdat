public class AdjNode {
    int destIndex;  // index tujuan di array courses[]
    AdjNode next;   // pointer ke node berikutnya

    public AdjNode(int destIndex) {
        this.destIndex = destIndex;
        this.next      = null;
    }
} 
    
