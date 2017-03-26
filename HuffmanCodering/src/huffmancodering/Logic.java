/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

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

                // Stap 2
                PriorityQueue queue = sortFrequency(map);

                // Stap 3
                PriorityQueue boom = generateTree(queue);

                // Stap 4
                Node hoofdKnoop = (Node) boom.poll();
                HashMap<Character, String> tabel = new HashMap<>();
                getCodes(hoofdKnoop, "", tabel);
                System.out.println(tabel.toString());

                // Stap 5
                String gecodeerdBericht = codeMessage(tabel, input);
                System.out.println(gecodeerdBericht);

                // Stap 6
                ArrayList<Character> gecodeerdArray = new ArrayList<>();

                for (char c : gecodeerdBericht.toCharArray()) {
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

    public static Map<Character, Integer> countFrequency(String input) {
        char[] chars = input.toCharArray();

        HashMap<Character, Integer> hashMap = new HashMap<>();

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

    public static PriorityQueue sortFrequency(Map<Character, Integer> map) {
        PriorityQueue queue = new PriorityQueue();
        for (Character c : map.keySet()) {
            queue.add(new Node(c, map.get(c)));
        }

        return queue;
    }

    public static PriorityQueue generateTree(PriorityQueue queue) {
        while (queue.size() >= 2) {
            Node left = (Node) queue.poll();
            Node right = (Node) queue.poll();
            int freq = left.frequency + right.frequency;
            Node knoop = new Node(freq, left, right);
            queue.add(knoop);
        }

        return queue;
    }

    public static void getCodes(Node knoop, String code, HashMap<Character, String> tabel) {
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

    public static String codeMessage(HashMap<Character, String> table, String message) {
        char[] chars = message.toCharArray();
        String codedMessage = "";

        for (char c : chars) {
            codedMessage += table.get(c);
        }

        return codedMessage;
    }

    public static void decodeMessage(Node hoofdKnoop, Node knoop, StringBuilder bericht, ArrayList<Character> codedMessage, int count) {
        if (codedMessage.size() > count) {
            Character nummer = codedMessage.get(count);

            if (nummer == '0') {
                if (knoop.leftChild.character != null) {
                    bericht.append(knoop.leftChild.character);
                    count++;
                    decodeMessage(hoofdKnoop, hoofdKnoop, bericht, codedMessage, count);
                } else {
                    count++;
                    decodeMessage(hoofdKnoop, knoop.leftChild, bericht, codedMessage, count);
                }
            } else if (nummer == '1') {
                if (knoop.rightChild.character != null) {
                    bericht.append(knoop.rightChild.character);
                    count++;
                    decodeMessage(hoofdKnoop, hoofdKnoop, bericht, codedMessage, count);
                } else {
                    count++;
                    decodeMessage(hoofdKnoop, knoop.rightChild, bericht, codedMessage, count);
                }
            }
        }
    }
}
