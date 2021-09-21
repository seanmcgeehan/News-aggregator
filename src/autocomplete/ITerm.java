package autocomplete;
import java.util.Comparator;

/**
 * @author ericfouh
 */
public interface ITerm extends Comparable<ITerm> {

    /**
     * Compares the two terms in descending order by weight.
     * 
     * @return comparator Object
     */
    public static Comparator<ITerm> byReverseWeightOrder() {

        Comparator<ITerm> c = new Comparator<ITerm>() {
            @Override
            public int compare(ITerm o1, ITerm o2) {

                return (int) (((Term) o2).getWeight() - ((Term) o1).getWeight());

            }

        };

        return c;

    }

    /**
     * Compares the two terms in lexicographic order but using only the first r
     * characters of each query.
     * 
     * @param r
     * @return comparator Object
     */
    public static Comparator<ITerm> byPrefixOrder(int r) {

        if (r < 0) {
            throw new java.lang.IllegalArgumentException();
        }

        Comparator<ITerm> c = new Comparator<ITerm>() {
            @Override
            public int compare(ITerm o1, ITerm o2) {
                if (((Term) o1).getTerm().length() < r || ((Term) o2).getTerm().length() < r) {
                    return ((Term) o1).getTerm().compareTo(((Term) (o2)).getTerm());
                }

                return ((Term) o1).getTerm().substring(0, r)
                        .compareTo(((Term) (o2)).getTerm().substring(0, r));
            }
        };

        return c;

    }

    public static Comparator<ITerm> byalphaOrder() {
        Comparator<ITerm> c = new Comparator<ITerm>() {
            @Override
            public int compare(ITerm o1, ITerm o2) {

                return ((Term) o1).getTerm().compareTo(((Term) (o2)).getTerm());
            }
        };

        return c;

    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(ITerm that);

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString();

}
