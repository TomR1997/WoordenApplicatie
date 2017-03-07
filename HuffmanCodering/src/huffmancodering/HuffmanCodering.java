/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

import java.util.Scanner;

/**
 *
 * @author Tomt
 */
public class HuffmanCodering {

    public HuffmanCodering()
    {
        menu();
    }
    public static void main(String[] args) {
        HuffmanCodering hc=  new HuffmanCodering();
    }
    
    public void menu()
    {
       Scanner reader = new Scanner(System.in);
       System.out.println("Type text:");

        try {
            String input="";

            if (reader.hasNext()) {
                input = reader.next();
            }
  
        } catch (Exception e) {
            e.printStackTrace();
            menu();
        }
            
    }
}
