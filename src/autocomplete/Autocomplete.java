package autocomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autocomplete implements IAutocomplete {

    Node root;

    int maxSuggestions;

    public Autocomplete() {
        root = new Node();
    }

    @Override
    public void addWord(String word, long weight) {
        // TODO Auto-generated method stub
        //prefixes was here
        Node tempNode = root;// is this pointer?

        String tempWord = "";

        word = word.toLowerCase();
        for (int z = 0; z < word.length(); z++) {

            if ((int) word.charAt(z) - 97 < 0) {
                return;
            }
            if ((int) word.charAt(z) - 97 > 25) {
                return;
            }
        }
        
        root.setPrefixes(root.getPrefixes() + 1);

        for (int i = 0; i < word.length(); i++) {

            tempWord = tempWord + word.charAt(i);
            

            if (tempNode.getReferences()[((int) word.charAt(i)) - 97] == null) {
                Node x = new Node(tempWord, weight);
                tempNode.setReferences(word.charAt(i) - 97, x);

            }
            tempNode = tempNode.getReferences()[word.charAt(i) - 97];
            tempNode.setPrefixes(tempNode.getPrefixes() + 1);
            if (i == word.length() - 1) {
                tempNode.setWords(tempNode.getWords() + 1);
                return;
            }

        }

    }

    @Override
    public Node buildTrie(String filename, int k) {
        // TODO Auto-generated method stub

        this.maxSuggestions = k;

        try {
            BufferedReader bR = new BufferedReader(new FileReader(filename));
            String next = "";

            //this.root = new Node(next, 0);
            next = bR.readLine();

            while ((next = bR.readLine()) != null) {

                String[] arr = next.trim().split("\t");

                if (arr.length < 2) {
                    break;
                }

                this.addWord(arr[1], Long.parseLong(arr[0]));

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public int numberSuggestions() {
        // TODO Auto-generated method stub
        int x = maxSuggestions;
        return x;
    }

    @Override
    public Node getSubTrie(String prefix) {
        
        
        for (int z = 0; z < prefix.length(); z++) {

            if ((int) prefix.charAt(z) - 97 < 0) {
                return null;
            }
            
            if ((int) prefix.charAt(z) - 97 > 25) {
                return null;
            }
        }
        // TODO Auto-generated method stub

        String tempWord = "";
        // tempWord = tempWord +prefix.charAt(i);
        
        Node tempNode = this.root;// is this pointer?

        for (int i = 0; i <= prefix.length(); i++) {

            if (i == prefix.length()) {
                return tempNode;
            }

            tempWord = tempWord + prefix.charAt(i);
            
            // Node tempNode = this.root;// is this pointer?

            if (tempNode.getReferences()[((int) prefix.charAt(i)) - 97] == null) {

                return null;

            } else {
                tempNode = tempNode.getReferences()[((int) prefix.charAt(i)) - 97];

            }

            // return 0;
        }

        return null;
    }

    @Override
    public int countPrefixes(String prefix) {
        // TODO Auto-generated method stub
        String tempWord = "";
        // tempWord = tempWord +prefix.charAt(i);
        
        Node tempNode = this.root;// is this pointer?
        
        for (int z = 0; z < prefix.length(); z++) {

            if ((int) prefix.charAt(z) - 97 < 0) {
                return 0;
            }
            if ((int) prefix.charAt(z) - 97 > 25) {
                return 0;
            }
        }
        

        for (int i = 0; i <= prefix.length(); i++) {

            if (i == prefix.length()) {
                return tempNode.getPrefixes();
            }

            tempWord = tempWord + prefix.charAt(i);
           
            // Node tempNode = this.root;// is this pointer?

            if (tempNode.getReferences()[((int) prefix.charAt(i)) - 97] == null) {

                return 0;

            } else {
                tempNode = tempNode.getReferences()[((int) prefix.charAt(i)) - 97];

            }

            // return 0;
        }

        return 0;// compliler happy
    }

    @Override
    public List<ITerm> getSuggestions(String prefix) {
        // TODO Auto-generated method stub
        ArrayList<ITerm> list = new ArrayList<ITerm>();

        for (int z = 0; z < prefix.length(); z++) {

            if ((int) prefix.charAt(z) - 97 < 0) {
                return list;
            }
            
            if ((int) prefix.charAt(z) - 97 > 25) {
                return list;
            }
        }

        String tempWord = "";
        // tempWord = tempWord +prefix.charAt(i);
        
        Node tempNode = this.root;// is this pointer?

        for (int i = 0; i <= prefix.length(); i++) {

            if (i == prefix.length()) {

                list = (ArrayList<ITerm>) subGetSuggestions(tempNode);
                Collections.sort(list, ITerm.byalphaOrder());

                return list; // sub function goes here
            }

            tempWord = tempWord + prefix.charAt(i);
           
            // Node tempNode = this.root;// is this pointer?

            if (tempNode.getReferences()[((int) prefix.charAt(i)) - 97] == null) {

                return list;

            } else {
                tempNode = tempNode.getReferences()[((int) prefix.charAt(i)) - 97];

            }

            // return null;
        }
        return list;

    }

    public List<ITerm> subGetSuggestions(Node node) {
        ArrayList<ITerm> returnList = new ArrayList<ITerm>();

        for (Node x : node.getReferences()) {
            if (x != null) {
                for (ITerm y : subGetSuggestions(x)) {
                    returnList.add(y);
                }
            }
        }

        if (node.getWords() > 0) {
            returnList.add(node.getTerm());
        }

        return returnList;

    }

}
