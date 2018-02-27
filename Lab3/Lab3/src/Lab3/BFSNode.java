package Lab3;

public class BFSNode {
    public String word;
    public BFSNode previousNode;

    public BFSNode(){
        word = null;
        previousNode = null;
    }

    public BFSNode(String word, BFSNode previousNode){
        this.word = word;
        this.previousNode = previousNode;
    }
}
