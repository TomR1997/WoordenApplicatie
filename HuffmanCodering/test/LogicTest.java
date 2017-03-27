/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import huffmancodering.Logic;
import huffmancodering.TimeStamp;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomt
 */
public class LogicTest {
    private Logic logic;
    private TimeStamp timer;
    
    @Before
    public void setUp() {
        logic = new Logic();
        timer = new TimeStamp();
    }
    

    @Test
    public void frequencyTest() {
        timer.init();
        timer.setBegin("Start");
        String s = "test";
        
        Map map = logic.countFrequency(s);
        Iterator it = map.entrySet().iterator();
        String value="";
        while (it.hasNext()) {
            value += it.next()+" ";          
        }
        String[] values = value.split(" ");
        assertEquals("s=1",values[0]);
        assertEquals("t=2",values[1]);
        assertEquals("e=1",values[2]);
        
        timer.setEnd("End");
    }
    
    @Test
    public void exportTest() {
        timer.init();
        timer.setBegin("Start");
        String input ="test";
        Map<Character, Integer> map = logic.countFrequency(input);
                
        PriorityQueue queue = logic.sortFrequency(map);

        PriorityQueue tree = logic.generateTree(queue);
        logic.exportToFile(tree);
        
        assertNotNull("encoded.bin");
        timer.setEnd("End");
    }
    
    @Test
    public void sortFrequencyTest()
    {
        timer.init();
        timer.setBegin("Start");
        
        String s = "test"; 
        Map map = logic.countFrequency(s);
        PriorityQueue queue = logic.sortFrequency(map);
        String t="";
        for(int i=0;i<queue.size();i++) {
            t+= queue.poll()+" ";
        }
        String[] values = t.split(" ");
        assertEquals("s:",values[0]);
        assertEquals("1",values[1]);
        assertEquals("e:",values[2]);
        
        timer.setEnd("End");
    }
}
