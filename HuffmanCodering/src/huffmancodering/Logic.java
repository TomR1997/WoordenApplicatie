/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancodering;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Tomt
 */
public class Logic {
    
    public void menu()
    {
       Scanner reader = new Scanner(System.in);
       System.out.println("Type text:");

        try {
            String input="";

            if (reader.hasNext()) {
                input = reader.next();
            }
            if(input!="")
            {
                frequency(input);
            }
  
        } catch (Exception e) {
            e.printStackTrace();
            menu();
        }       
    }
    
    public void frequency(String input){
        TreeMap tm = new TreeMap();
        int count =0;
        
        for (int i=0;i<input.length();i++)
        {
            if (tm.containsKey(input.charAt(i)))
            {
                int pos = (int) tm.get(input.charAt(i));
                tm.put(input.charAt(i),pos+1);
            }
            else
            {
                tm.put(input.charAt(i), 1);             
            }
            count++;
        }
        System.out.println(tm.toString());
        /* reverse
        Map sortedMap = sortByValues(tm);
        Set set = sortedMap.entrySet();
        System.out.println(set.toString()+" "+count);*/
    }
    
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
        public int compare(K k1, K k2) {
        int compare = map.get(k1).compareTo(map.get(k2));
        if (compare == 0)
            return 1;
        else 
            return compare;
            }
        };
 
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
  }
}
