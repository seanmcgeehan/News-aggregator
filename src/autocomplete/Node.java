package autocomplete;
/**
 * ==== Attributes ==== - words: number of words - term: the ITerm object -
 * prefixes: number of prefixes - references: Array of references to
 * next/children Nodes
 * 
 * ==== Constructor ==== Node(String word, long weight)
 * 
 * @author Your_Name
 */
public class Node {
    int words;// check if should be long
    Term term;
    int prefixes;

    Node[] references = new Node[26];

    public Node() {
        words = 0;
        prefixes = 0;
        // term = new Term("", 0);
    }

    public Node(String word, long weight) {

        term = new Term(word, weight);
        words = 0;
        prefixes = 0;

    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(int prefixes) {
        this.prefixes = prefixes;
    }

    public Node[] getReferences() {
        return references;
    }

    public void setReferences(int index, Node x) {
        if (index <= 25) {
            this.references[index] = x;
        }

    }

    public void setTerm(Term termset) {
        term = termset;
    }

    public Term getTerm() {
        return this.term;
    }
}
