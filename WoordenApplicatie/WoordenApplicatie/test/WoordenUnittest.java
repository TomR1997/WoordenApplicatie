/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import woordenapplicatie.gui.WoordenController;
import static woordenapplicatie.gui.WoordenController.sortByValues;

/**
 *
 * @author Tomt
 */
public class WoordenUnittest {
   private WoordenController wcontroller;
    
    @Before
    public void setUp() {
        wcontroller = new WoordenController();
    }

    @Test
    public void aantalTest()
    {
        HashSet hs = new HashSet();
        int count =0;  
        String s = "een, twee, drie";
        
        String replace = s.replaceAll(",", "");
        replace = replace.replaceAll("\n", " ");
        String[] split = replace.split(" ");
          
        for (int i=0;i<s.length();i++)
        {
            hs.add(split[i]);
            count++;
        }
        assertEquals(3,count);
    }
    
    @Test
    public void sorteerTest()
    {
        String s = "een, twee, drie";
        
        String replace = s.replaceAll(",", "");
        replace = replace.replaceAll("\n", " ");
        String[] split = replace.split(" ");
        TreeSet ts = new TreeSet();
            
        for (int i=0;i<split.length;i++)
        {
            ts.add(split[i]);
        }
        ts = (TreeSet) ts.descendingSet();
        
        assertEquals("twee, een, drie", ts.toString());
    }
    
    @Test
    public void frequentieTest(){
        
        String s = "een, twee, drie";
        
        String replace = s.replaceAll(",", "");
        replace = replace.replaceAll("\n", " ");
        String[] split = replace.split(" ");
        
        TreeMap tm = new TreeMap();
        int count =1;
        for (int i=0;i<split.length;i++)
        {
            if (tm.containsKey(split[i]))
            {
                count = (int) tm.get(split[i]);
                tm.put(split[i],count+1);
            }
            else
            {
                tm.put(split[i], 1);
            }      
        }
        
        Map sortedMap = sortByValues(tm);
 
        Set set = sortedMap.entrySet();
        assertEquals("drie=1, een=1, twee=1",set.toString());
    }
}
