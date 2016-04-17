package org.aksw.asknow.util.experiment;



import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.Parser;
import org.aksw.asknow.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.lucene.queryparser.classic.ParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j public class AnnotateRelationQALD {
	
	static Indexer ind = new Indexer();
    
	public static void main(String[] args)
	{	
		try {ind.index();
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    
    System.out.println("PATTY index done");
		for(int i=0;i<15;i++)
		{
			relationannoate(i);
		}
	}	

	//	/** @param rs a one dimensional result set
	//	 * @return a string representation of the result set.*/
	//	static String resultSetString(ResultSet rs)
	//	{
	//		return rs.toString();
	//	}

	static void relationannoate(int n)
	{
		// TODO: add hawk autosparql commons qald5 eval code usage
		log.info(query(n).toString());
	}

	static String query(int n)
	{	String annotatedrelation="";
	List<Nqs> templates = Parser.parse();
	Nqs q1 = templates.get(n);
	System.out.println(q1.getAll());
	//System.out.println(q1.getDesire());
	
	 try {
			Map<Integer,String> relations=ind.search("pattern", q1.getRelation2());
			System.out.println(relations);
			Map<Integer,String> desires=ind.search("pattern", q1.getDesire());
			System.out.println(desires);
			Map<Integer,String> nlquery=ind.search("pattern", q1.nlQuery);
			System.out.println(nlquery);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return annotatedrelation;
	}
}