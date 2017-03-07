/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

/**
 *
 * @author Tomt
 */
public class Facade {
    private Logic logic;

    public Facade() {
        logic = new Logic();
    }
    
    public void showMenu(){
        logic.menu();
    }
}
