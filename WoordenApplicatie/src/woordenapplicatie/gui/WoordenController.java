package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "een, twee, drie, vier\n" +
                                                "hoedje van, hoedje van\n" +
                                                "een, twee, drie, vier\n" +
                                                "hoedje van papier\n"/* +
                                                "\n" +
                                                "Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier"*/;
    
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }
    
    @FXML
    private void aantalAction(ActionEvent event) {
        HashSet hs = new HashSet();
        int count =0;       
        
        for (int i=0;i<getSplit().length;i++)
        {
            hs.add(getSplit()[i]);
            count++;
        }
        taOutput.setText("Totaal: "+ count+ "\n"+"Verschillende woorden: "+ hs.size());
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        TreeSet ts = new TreeSet();
            
        for (int i=0;i<getSplit().length;i++)
        {
            ts.add(getSplit()[i]);
        }
        ts = (TreeSet) ts.descendingSet();
        taOutput.setText(ts.toString());
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        TreeMap tm = new TreeMap();
        int count =1;
        for (int i=0;i<getSplit().length;i++)
        {
            if (tm.containsKey(getSplit()[i]))
            {
                count = (int) tm.get(getSplit()[i]);
                tm.put(getSplit()[i],count+1);
            }
            else
            {
                tm.put(getSplit()[i], 1);
            }      
        }
        
        Map sortedMap = sortByValues(tm);
 
        Set set = sortedMap.entrySet();
        taOutput.setText(set.toString());
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        /*TreeMap<String, LinkedList<Integer>> concord = new TreeMap<String, LinkedList<Integer>>();
        
        for (int i=0;i<getSplit().length;i++)
        {
            if (!concord.containsKey(getSplit()[i]) || concord.get(getSplit()[i]) == null) 
            {
                LinkedList ll = new LinkedList<Integer>();
                ll.add(i + 1);
                concord.put(getSplit()[i], ll);
            } 
            else 
            {
                LinkedList ll = concord.get(getSplit()[i]);
                ll.add(i + 1);
            }
        }*/
        
        //taOutput.setText(concord.toString());
        
         TreeMap<String, LinkedList<Integer>> tm = new TreeMap<>();
        String s = DEFAULT_TEXT.replaceAll(",", "");
        int pos =0;
        int count=0;
        for (int i=0;i<s.length();i++)
        {
            String t = s.substring(pos,i);
            if (t.contains("\n"))
            {
                pos=i;
                t=t.replaceAll("\n", " ");
                String[] split = t.split(" ");
                
                for (int q=0;q<split.length;q++)
                {
                    if (tm.containsKey(split[q]))
                    {
                        LinkedList ll = tm.get(split[q]);
                        ll.add(count + 1);
                    }
                    else
                    {                     
                        LinkedList ll = new LinkedList<Integer>();
                        ll.add(count + 1);
                        tm.put(split[q], ll);
                    }
                } 
                count++;
            }
        }
        taOutput.setText(tm.toString());
    }
   
    private String[] getSplit()
    {
        String replace = DEFAULT_TEXT.replaceAll(",", "");
        replace = replace.replaceAll("\n", " ");
        String[] split = replace.split(" ");
        return split;
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
