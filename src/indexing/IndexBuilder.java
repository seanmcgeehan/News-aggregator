//package indexing;

import java.io.FileWriter;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndexBuilder implements IIndexBuilder {

    public IndexBuilder() {

    }

    @Override
    public Map<String, List<String>> parseFeed(List<String> feeds) {

        // TODO Auto-generated method stub
        
        HashMap feedMap = new HashMap<String, List<String>>();

        for (String x : feeds) {
            Document doc;
            try {
                ArrayList<String> list = new ArrayList<String>();
                // ArrayList bodyList = new ArrayList<String>();
                doc = Jsoup.connect(x).get();

                Elements links = doc.getElementsByTag("link");// link body
                for (Element link : links) {
                    
                    String lt = link.text();
                    list.add(lt);//// make sure local host or whatever
                }

                for (String linkText : list) {
                    Document doc2 = Jsoup.connect(linkText).get();
                    Elements bodys = doc2.getElementsByTag("body");
                    ArrayList bodyList = new ArrayList<String>();
                    for (Element body : bodys) {
                        String bt = body.text();
                        String tempWord = "";

                        bt = bt.toLowerCase();


                        for (int i = 0; i < bt.length(); i++) {

                            if (bt.charAt(i) - 97 >= 0 && bt.charAt(i) - 97 <= 25) { 
                                
                                // maybe should include space

                                tempWord = tempWord + bt.charAt(i);
                            }
                            if (bt.charAt(i) == 32) {
                                bodyList.add(tempWord);
                                tempWord = "";
                            }

                        }
                        bodyList.add(tempWord);
                        tempWord = "";
                        // bodyList.add(tempWord);
                        // Sys
                        feedMap.put(linkText, bodyList);
                    }

                }

                // feedMap.put(x, bodyList);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return feedMap;
    }

    @Override
    public Map<String, Map<String, Double>> buildIndex(Map<String, List<String>> docs) {

        HashMap map = new HashMap<String, Map<String, Double>>();
        HashMap<String, Map<String, Integer>> map2 = new HashMap<String, Map<String, Integer>>();

        HashMap<String, TreeSet<String>> setMap = new HashMap<String, TreeSet<String>>();

        // TODO Auto-generated method stub

        // count map document frequency
        // HashMap<String, Integer> countMap = new HashMap<String, Integer>();

        for (String s : docs.keySet()) { // 1
            // TreeMap<String, Double> valueMap = new TreeMap<String, Double>();
            TreeMap<String, Integer> valueMap2 = new TreeMap<String, Integer>();

            for (String term : docs.get(s)) { /// 1
                // int countDoc = 0;
                // int countAllDocs = 0;
                // TreeSet set = new TreeSet<String>();
                // 

                if (valueMap2.containsKey(term)) {
                    valueMap2.put(term, valueMap2.get(term) + 1);
                } else {
                    valueMap2.put(term, 1);
                }

                if (setMap.containsKey(term)) {
                    setMap.get(term).add(s);
                } else {
                    setMap.put(term, new TreeSet<String>());
                    // setMap.put(term, setMap.get(term).add(s));
                    setMap.get(term).add(s);

                }

            }
            map2.put(s, valueMap2);

        }

        for (String s : docs.keySet()) { // 1
            TreeMap<String, Double> valueMap = new TreeMap<String, Double>();
            // TreeMap<String, Integer> valueMap2 = new TreeMap<String, Integer>();

            for (String term : docs.get(s)) { 

                valueMap.put(term, ((map2.get(s).get(term) * 1.0) / docs.get(s).size())
                        * (java.lang.Math.log(docs.keySet().size() 
                                * 1.0 / setMap.get(term).size())));

            }
            map.put(s, valueMap);

        }

        /*
         * for(String subS : docs.keySet()) { //2 must go/ not nested for(String subTerm
         * : docs.get(s)) {//2 if(subTerm.equals(term)) { //set.add(subS);
         * countAllDocs++; if(subS.equals(s)) { countDoc++; } }
         * 
         * 
         * 
         * }
         * 
         * 
         * }
         */
        // valueMap.put(term, ((countDoc*1.0)/countAllDocs)
        // * (java.lang.Math.log(docs.keySet().size()*1.0/valueMap.size()))) ; //set was
        // here
       

        return map;
    }

    public static Comparator<Entry<String, Double>> byvalueOrder() {

        Comparator<Entry<String, Double>> c = new Comparator<Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {

                return  (o2.getValue().compareTo(o1.getValue()));

            }

        };

        return c;

    }

    public static Comparator<Entry<String, List<String>>> bynumArticles() {

        Comparator<Entry<String, List<String>>> c = new Comparator<Entry<String, List<String>>>() {
            @Override
            public int compare(Entry<String, List<String>> o1, Entry<String, List<String>> o2) {

                if ((o1.getValue().size() - o2.getValue().size()) != 0) {

                    return ((int) (o2.getValue().size() - o1.getValue().size()));
                } else {

                    return o2.getKey().compareTo(o1.getKey());
                }

            }

        };

        return c;

    }

    @Override
    public Map<?, ?> buildInvertedIndex(Map<String, Map<String, Double>> index) {

        HashMap<String, TreeSet<Entry<String, Double>>>map = 
                new HashMap<String, TreeSet<Entry<String, Double>>>();
        // ArrayList<Entry<String,Double>> list = new ArrayList<Entry<String,Double>>();
        HashSet set = new HashSet<String>();
        
        HashMap<String, ArrayList<Entry<String,Double>>>map2 = 
                new HashMap<String, ArrayList<Entry<String,Double>>>();

        // Sys

        // TODO Auto-generated method stub
        for (String x : index.keySet()) {
            for (String term : index.get(x).keySet()) {
                // Sys
                set.add(term);
            }

            // S
        } // all of the terms in set

        for (String y : new ArrayList<String>(set)) {
            ArrayList<Entry<String,Double>> list2 = new ArrayList<Entry<String,Double>>();
            TreeSet<Entry<String, Double>> list = new 
                    TreeSet<Entry<String, Double>>(IndexBuilder.byvalueOrder());
            for (String x : index.keySet()) {
                for (String term : index.get(x).keySet()) {
                    if (term.equals(y)) {
                        // list.ad
                        
                        if (map.containsKey(y)) {
                            for (Entry ent : map.get(y)) {
                              
                                list2.add(ent);
                              //S
                            }
                        }
                        list2.add(new AbstractMap.SimpleEntry<>(x, index.get(x).get(y)));
                        Collections.sort(list2, IndexBuilder.byvalueOrder());
                        map2.put(y, list2);
                        //Sy
                    }
                }

            }
            // need to sort list
            // ArrayList<Entry<String,Double>> list2 = new
            // ArrayList<Entry<String,Double>>(list);
            // Collections.sort(list2, IndexBuilder.byvalueOrder());//meep

            // ;
            //S
            map.put(y, list);
        }

        // 

        return map2;
    }

    // term then ArrayList<Entry<doc,Double>>

    @Override
    public Collection<Entry<String, List<String>>> buildHomePage(Map<?, ?> invertedIndex) {
        // TODO Auto-generated method stub

        //

        ArrayList<Entry<String, List<String>>> list = new ArrayList<Entry<String, List<String>>>();

        for (Object term : invertedIndex.keySet()) {

            ArrayList<String> docList = new ArrayList<String>();

            for (Entry<String, Double> x : 
                (ArrayList<Entry<String, Double>>) invertedIndex.get(term)) {
                
                docList.add(x.getKey());

            }

            if (!IIndexBuilder.STOPWORDS.contains(term)) {

                list.add(new AbstractMap.SimpleEntry<>((String) term, docList));
            }

        }
        Collections.sort(list, IndexBuilder.bynumArticles());// stop words

        return list;
    }

    @Override
    public Collection<?> createAutocompleteFile(Collection<Entry<String, List<String>>> homepage) {
        // TODO Auto-generated method stub

        ArrayList<String> termList = new ArrayList<String>();

        for (Entry<String, List<String>> x : homepage) {
            termList.add(x.getKey());

        }

        Collections.sort(termList);

        try {
            FileWriter fw = new FileWriter("autocomplete.txt");
            fw.write(termList.size() + "\n");
            for (String x : termList) {
                fw.write("" + 0 + " " + x + "\n");
            }

            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // IIndexBuilder.STOPWORDS;

        return termList;
    }

    @Override
    public List<String> searchArticles(String queryTerm, Map<?, ?> invertedIndex) {
        // TODO Auto-generated method stub

        // ArrayList<Entry<String, List<String>>> list = new ArrayList<Entry<String,
        // List<String>>>();

        ArrayList<String> docList = new ArrayList<String>();

        // ArrayList<Entry<String,Double>> nlist = new
        // ArrayList<Entry<String,Double>>((TreeSet)invertedIndex.get(queryTerm));

        for (Entry<String, Double> x : 
            (ArrayList<Entry<String, Double>>) invertedIndex.get(queryTerm)) {
            docList.add(x.getKey());

        }

        return docList;
    }

}
