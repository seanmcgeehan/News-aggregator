# News-aggregator

this program reproduces the functionality of a news aggregator.
A news aggregator is a type of search engine, and its implementation follows the same steps.

  1. The first component of a web search engine is the crawler. This is a program that downloads
    web page content that we wish to search for.

  2. The second component is the indexer, which will take these downloaded pages and create an
    inverted index.

  3. The third component is retrieval, which answers a user’s query by talking to the user’s browser.
    The browser will show the search results and allow the user to interact with the web.
    
  For step 1 we used JSoup to scrape and parse documents (using RSS and HTML). The program extracts HTML documents for each RSS
feed and maps a document (the key) with all the words in that document (the value) for all documents
from all the RSS feeds.

   For step 2 the program uses a forward index that maps documents to a list of terms that occur in them. To identify relevent terms we used Term Frequency-Inverse Document Frequency. The term frequency represents the count of a term
in a document and the inverse document frequency penalizes words that appear in many documents.

  for step 3 the program uses an inverted index. An inverted index is a map of a tag term (the key) and a
Collection (the value) of entries consisting of a document and the TF-IDF value of the term in that
document. The collection is sorted by reverse tag term TF-IDF value. In other words, the document in
which a term has the highest TF-IDF should be visited first. 



![alt text](https://github.com/seanmcgeehan/News-aggregator/blob/main/Screen%20Shot%202022-01-23%20at%2012.15.25%20PM.png?raw=true)
