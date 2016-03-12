package org.aksw.asknow.util.experiment;



import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field; 
import org.apache.lucene.document.StringField; 
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	static String indexPath = "index";
	
	public static void index() throws IOException {
		Directory dir = null;
		IndexWriter writer = null;
		try {
			
			//1. where to store our index files
			 
			dir = FSDirectory.open(Paths.get(indexPath));
			/* use the following line instead, if you only want a ram index */
			// Directory index = new RAMDirectory();
			/*
			 * 2. get an instance of an analyzer, here we use the
			 * StandardAnalyer in earlier versions we had to add a version
			 * parameter, now this is no longer required nor possible
			 */
			Analyzer analyzer = new StandardAnalyzer();
			
			//3. Create an index writer and its configuration object
			
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			//iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			// if we want to override an existent index we should use the
			// following line instead
			iwc.setOpenMode(OpenMode.CREATE);
			writer = new IndexWriter(dir, iwc);
			
			
			
			//4. add a sample document to the index
			
			
			BufferedReader br = new BufferedReader(new FileReader
					("src/main/resources/dbpedia-relation-paraphrases.txt"));
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split("\\t| ");
				Document doc = new Document();
				Field relation = new StringField("relation", fields[0], Field.Store.YES);
				doc.add(relation);
				fields[0]="";
				//System.out.println(fields[1]);
				fields[0]=StringUtils.join(fields, " ");
				fields[0]=fields[0].replace(";", "");
				//System.out.println(fields[0]);
				Field pattern = new TextField("pattern", fields[0], Field.Store.YES);
				doc.add(pattern);
				writer.addDocument(doc);
				line = br.readLine();
			}
			br.close();  
//			List<String> patterns = new ArrayList<String>();

		} finally {
			if (writer != null)
				writer.close();
			if (dir != null)
				dir.close();
		}
		
	}
	
	
	
	public static Map<Integer,String> search(String field, String value) throws IOException, ParseException {
		/*
		 * 1. build an index reader and index searcher
		 */
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    
	    /* 
	     * 2. build an analyzer again - use the same as in the indexing process
	     */
	    Analyzer analyzer = new StandardAnalyzer();
	    
	    /*
	     * 3. Build a query parser who will parse our query, written in Lucene Query Language 
	     */
	    QueryParser parser = new QueryParser(field, analyzer);
	    
	    /*
	     * 4. we search the value in a given field, e.g. "versioninfo:LUCENE-5945"
	     */
	    Query query = parser.parse(field + ":" + value);
	    
	    /*
	     * 5. we trigger the search, interested in the 5 first matches
	     */
	    int k=10000;
	    TopDocs results = searcher.search(query, k);
	    
	    /*
	     * 6. We get the hit information via the scoreDocs attribute in the TopDocs object
	     */
	    ScoreDoc[] hits = results.scoreDocs;
	    //System.out.println(Arrays.toString(hits));
	  //  int numTotalHits = results.totalHits;
	    //System.out.println(numTotalHits + " total matching documents");
	    
	    //All results are taken and ordered by frequence
	    if (hits.length > 0) {
	  //  	float max = hits[0].score;
	    	Map<String,Integer> m = new TreeMap<String,Integer>();
	    	//String[] tmp = new String[k];
	    	int i=0;
	    	while (i<k && i< hits.length){
	    		Document doc = searcher.doc(hits[i].doc);
	    		if (m.get(doc.get("relation"))!=null){
	    			m.put(doc.get("relation"), m.get(doc.get("relation"))+1);
	    		} else {
	    			m.put(doc.get("relation"), 1);
	    		}
	    		i++;
	    	}
	    	//Sort the map
	    	Map<Integer,String> mSorted = new TreeMap<Integer, String>(Collections.reverseOrder());
	    	for (Map.Entry<String, Integer> entry : m.entrySet())
	    	{
	    	    mSorted.put(entry.getValue(), entry.getKey());
	    	}
	    	//for (Map.Entry<Integer,String> entry : mSorted.entrySet())
	    	//{
	    	//    System.out.println(entry.getKey() + "/" + entry.getValue());
	    	//}
	    	System.out.println(mSorted.toString());
	    	return mSorted;
	    }
	    return null;
	}
	
}