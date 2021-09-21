package autocomplete;
//import java.util.Comparator;
//import java.util.List;

public class Term implements ITerm {

    private long weight;

    private String termString;

    public Term(String query, long weighty) {
        if (query == null || weighty < 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.termString = query;
        this.weight = weighty;

    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(ITerm that) {
        return termString.compareTo(((Term) (that)).getTerm());
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + termString;

    }

    public String getTerm() {
        return termString;

    }

    public void setTerm(String s) {

        termString = s;

    }

    public long getWeight() {

        return weight;

    }

    public void setWeight(long w) {

        weight = w;
    }

}