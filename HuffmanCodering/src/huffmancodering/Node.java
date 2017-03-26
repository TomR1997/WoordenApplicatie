/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

import java.util.Comparator;

/**
 *
 * @author Tomt
 */
public class Node implements Comparable<Node> {
    public Character character;
    public int frequency;
    public Node leftChild, rightChild;
    
    public Node(Character character, int frequency) 
    {
        this.character = character;
        this.frequency = frequency;
    }
    
    public Node(int frequency, Node leftChild, Node rightChild) 
    {
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.frequency, o.frequency);
    }
    
    @Override
    public String toString() {
        return character + ": " + frequency;
    }
}
