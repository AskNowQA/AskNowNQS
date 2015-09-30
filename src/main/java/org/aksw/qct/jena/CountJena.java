package org.aksw.qct.jena;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.jena.query.*;

public class CountJena{

	static String sparqlHeader ="PREFIX dbo: <http://dbpedia.org/ontology/>" 
			+ "PREFIX yago: <http://dbpedia.org/class/yago/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX res: <http://dbpedia.org/resource/> ";

	public static ResultSet pattern1(ArrayList<String> possibleMatch, String dbpRes) {
		String dbpPro = null;
		String dbpProTag = null;
		String pattern1 = "SELECT DISTINCT ?num" 
				+"WHERE {" 
				+"res:"+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num ." 
				+"}"; 


		int count =possibleMatch.size();
		//String varible1="";//dbo:capital
		String service1="http://dbpedia.org/sparql";
		String query1="";

		System.out.println("chekpoin: "+query1);
		int i=0;
		for(String temp="";count>0;count--)	{ 
			temp=possibleMatch.get(count-1).toString();
			//System.out.println(temp.toString());
			ArrayList<String> aList= new ArrayList<>(Arrays.asList(temp.split("/")));
			i=aList.size();
			//System.out.println(aList.get(i-2).toString()+count);
			if (aList.get(i-2).equals("ontology"))
				dbpProTag="dbo";
			else if (aList.get(i-2).equals("property"))
				dbpProTag="dbp";
			dbpPro=aList.get(i-1).toString();
			pattern1 = "SELECT DISTINCT ?num " 
					+"WHERE {" 
					+"res:"+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num ." 
					+"}";
			query1=sparqlHeader +pattern1;
			System.out.println(query1);
			try(QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1))
			{
				return e.execSelect();
			}
		}
		throw new IllegalArgumentException("no match for a count query with pattern 1");
	}

	public static ResultSet pattern2(ArrayList<String> possibleMatch, String dbpRes) {
		String dbpPro = null;
		String dbpProTag = null;
		String pattern1 = "SELECT COUNT ( DISTINCT ?num) " 
				+"WHERE {" 
				+"? num res:"+dbpRes+" "+dbpProTag+":"+dbpPro+" ." 
				+"}"; 


		int count =possibleMatch.size();
		//String varible1="";//dbo:capital
		String service1="http://dbpedia.org/sparql"; 
		String query1="";

		System.out.println("chekpoin: "+query1);
		int i=0; 
		for(String temp="";count>0;count--)	{ 
			temp=possibleMatch.get(count-1).toString();
			//System.out.println(temp.toString());
			ArrayList<String> aList= new ArrayList<>(Arrays.asList(temp.split("/")));
			i=aList.size();
			//System.out.println(aList.get(i-2).toString()+count);
			if (aList.get(i-2).equals("ontology"))
				dbpProTag="dbo";
			else if (aList.get(i-2).equals("property"))
				dbpProTag="dbp";
			dbpPro=aList.get(i-1).toString();
			String res = "res";
			pattern1 = "SELECT COUNT ( DISTINCT ?num) " 
					+"WHERE {" 
					+"{ ?num "+res+":"+dbpRes+" "+dbpProTag+":"+dbpPro+" .}"
					+" UNION "
					+"{ ?num "+dbpProTag+":"+dbpPro+" "+res+":"+dbpRes+" .}"
					+" UNION "
					+"{ "+res+":"+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num .}"
					+"}"; 


			query1=sparqlHeader +pattern1;
			System.out.println(query1);
			try(QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1))
			{return e.execSelect();}
		}
		throw new IllegalArgumentException("no match for a count query with pattern 2");
	}

}