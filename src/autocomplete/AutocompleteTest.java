package autocomplete;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AutocompleteTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Autocomplete a = new Autocomplete();
        ///autograder/submission/
        //"/Users/seanmcgeehan/eclipse-workspace/AutoComplete/src/pokemon.txt"
        Node nod = a.buildTrie("/autograder/submission/pokemon.txt", 7);
        //assertNotNull(nod);
        assertEquals(a.getSubTrie("").getPrefixes(), 729);
        a.getSubTrie("a");
        assertEquals(a.countPrefixes(""), 729);
        assertEquals(a.countPrefixes("alakazam"), 1);

        assertEquals(((Term) a.getSuggestions("ala").get(0)).getTerm(), "alakazam");
    }

}
///AutoComplete/src/pokemon.txt