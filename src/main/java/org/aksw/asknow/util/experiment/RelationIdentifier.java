package org.aksw.asknow.util.experiment;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

public class RelationIdentifier {

	@SuppressWarnings("static-access")
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		System.out.println("hello");
		Indexer ind = new Indexer();
         try {
			ind.index();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
         
         System.out.println("PATTY index done");
         
         
         try {
			Map<Integer,String> relations=ind.search("pattern", "movies did direct");
			System.out.println(relations);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         
	}

}
