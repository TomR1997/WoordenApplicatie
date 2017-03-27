/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author Tomt
 */
public class Logic {

    public void menu() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Type text:");

        try {
            String input = "";

            if (reader.hasNext()) {
                input = reader.next();
            }
            if (input != "") {
                Map<Character, Integer> map = countFrequency(input);
                System.out.println(map.toString());

                PriorityQueue queue = sortFrequency(map);
                System.out.println(queue.toString());
                PriorityQueue tree = generateTree(queue);
                exportToFile(tree);

                Node hoofdKnoop = (Node) importTree().poll();

                Map<Character, String> table = new HashMap<>();
                getCodes(hoofdKnoop, "", table);
                System.out.println(table.toString());

                String gecodeerdBericht = codeMessage(table, input);
                exportToFile(gecodeerdBericht);
                System.out.println(importEncoded());

                ArrayList<Character> gecodeerdArray = new ArrayList<>();

                for (char c : importEncoded().toCharArray()) {
                    gecodeerdArray.add(c);
                }

                StringBuilder ontvangenBericht = new StringBuilder("");
                decodeMessage(hoofdKnoop, hoofdKnoop, ontvangenBericht, gecodeerdArray, 0);
                System.out.println(ontvangenBericht.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
            menu();
        }
    }

    public Map<Character, Integer> countFrequency(String input) {
        //HashMap get: O(1), put: O(1)
        char[] chars = input.toCharArray();

        Map<Character, Integer> hashMap = new HashMap<>();

        for (char c : chars) {
            Integer i = hashMap.get(c);

            if (i != null) {
                i++;
                hashMap.put(c, i);
            } else {
                hashMap.put(c, 1);
            }
        }
        return hashMap;
    }

    public PriorityQueue sortFrequency(Map<Character, Integer> map) {
        // PriorityQueue add: O(log n)
        // HashMap get: O(1)
        PriorityQueue queue = new PriorityQueue();
        for (Character c : map.keySet()) {
            queue.add(new Node(c, map.get(c)));
        }

        return queue;
    }

    public PriorityQueue generateTree(PriorityQueue queue) {
        // PriorityQueue poll: O(log n), add: O(log n), size: O(1)        
        while (queue.size() >= 2) {
            Node left = (Node) queue.poll();
            Node right = (Node) queue.poll();
            int freq = left.frequency + right.frequency;
            Node knoop = new Node(freq, left, right);
            queue.add(knoop);
        }

        return queue;
    }

    public void getCodes(Node knoop, String code, Map<Character, String> tabel) {
        // HashMap put: O(1)
        if (knoop.leftChild != null) {
            code += "0";

            if (knoop.leftChild.character == null) {
                getCodes(knoop.leftChild, code, tabel);
            } else {
                tabel.put(knoop.leftChild.character, code);
            }

            code = code.substring(0, code.length() - 1);
        }

        if (knoop.rightChild != null) {
            code += "1";

            if (knoop.rightChild.character == null) {
                getCodes(knoop.rightChild, code, tabel);
            } else {
                tabel.put(knoop.rightChild.character, code);
            }

            code = code.substring(0, code.length() - 1);
        }
    }

    public String codeMessage(Map<Character, String> table, String message) {
        // HashMap get: O(1)
        char[] chars = message.toCharArray();
        String codedMessage = "";

        for (char c : chars) {
            codedMessage += table.get(c);
        }

        return codedMessage;
    }

    public void decodeMessage(Node hoofdKnoop, Node node, StringBuilder message, ArrayList<Character> codedMessage, int count) {
        // ArrayList get: O(1), size: O(1)
        if (codedMessage.size() > count) {
            Character number = codedMessage.get(count);

            if (number == '0') {
                if (node.leftChild.character != null) {
                    message.append(node.leftChild.character);
                    count++;
                    decodeMessage(hoofdKnoop, hoofdKnoop, message, codedMessage, count);
                } else {
                    count++;
                    decodeMessage(hoofdKnoop, node.leftChild, message, codedMessage, count);
                }
            } else if (number == '1') {
                if (node.rightChild.character != null) {
                    message.append(node.rightChild.character);
                    count++;
                    decodeMessage(hoofdKnoop, hoofdKnoop, message, codedMessage, count);
                } else {
                    count++;
                    decodeMessage(hoofdKnoop, node.rightChild, message, codedMessage, count);
                }
            }
        }
    }

    public void exportToFile(PriorityQueue rootNode) {
        try {
            FileOutputStream fos = new FileOutputStream("boom.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(rootNode);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToFile(String encoded) {
        BitSet bitSet = createBitset(encoded);
        try {
            FileOutputStream fos = new FileOutputStream("encoded.bin");
            fos.write(bitSet.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BitSet createBitset(String s) {
        BitSet bitSet = new BitSet(s.length() + 1);
        int bitcounter = 0;
        for (Character c : s.toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }
        bitSet.set(bitcounter + 1);
        return bitSet;
    }

    public PriorityQueue importTree() {
        try {
            FileInputStream fis = new FileInputStream("boom.bin");
            ObjectInputStream oos = new ObjectInputStream(fis);
            return (PriorityQueue) oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String importEncoded() {
        try {
            FileInputStream fis = new FileInputStream("encoded.bin");
            DataInputStream dis = new DataInputStream(fis);
            File file = new File("encoded.bin");
            byte[] result = new byte[(int) file.length()];
            dis.readFully(result);
            BitSet bs = BitSet.valueOf(result);
            dis.close();
            return bitSetToString(bs);
        } catch (FileNotFoundException e) {
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String bitSetToString(BitSet set) {
        String result = "";
        for (int i = 0; i < (set.length() - 2); i++) {
            if (set.get(i)) {
                result += "1";
            } else {
                result += "0";
            }
        }
        return result;
    }
}
