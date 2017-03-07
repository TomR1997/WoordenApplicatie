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

    private Facade facade;
    public HuffmanCodering()
    {
        facade = new Facade();
        facade.showMenu();
    }
    public static void main(String[] args) {
        HuffmanCodering hc=  new HuffmanCodering();
    }
}
