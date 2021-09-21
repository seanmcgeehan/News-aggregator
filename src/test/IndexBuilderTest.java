//package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

//import indexing.IIndexBuilder;
//import indexing.IndexBuilder;

public class IndexBuilderTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        IIndexBuilder idxBuilder = new IndexBuilder();
        ArrayList<String> rss = new ArrayList<String>();
        rss.add("http://cit594.ericfouh.com/sample_rss_feed.xml");

        Map<String, List<String>> pFeed = idxBuilder.parseFeed(rss);

        assertEquals(pFeed.size(), 5);// test to make sure 5
        Map<String, Map<String, Double>> bIndex = idxBuilder.buildIndex(pFeed);
        // (bIndex.get("http://cit594.ericfouh.com/page1.html").get("data"));
        assertEquals(bIndex.size(), 5);

        Map<?, ?> invert = idxBuilder.buildInvertedIndex(bIndex);
        assertEquals(invert.size(), 92);
        
        Collection<Entry<String, List<String>>> x = idxBuilder.buildHomePage(invert);
        idxBuilder.createAutocompleteFile(x);

    }

}
