package Lab3;
/* WORD LADDER BFSNode.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Git URL: https://github.com/ZachPope531/422C_Spring2018
 * Spring 2018
 */

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
