package org.aksw.nqs.jena;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class XofyJena {

	static String sparqlHeader ="PREFIX dbo: <http://dbpedia.org/ontology/>" 
			+ "PREFIX yago: <http://dbpedia.org/class/yago/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX res: <http://dbpedia.org/resource/> ";

	public static void pattern1(ArrayList<String> possibleMatch, String dbpRes) {
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
			QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1);
			ResultSet rs=e.execSelect();
			while (rs.hasNext()) {
						QuerySolution qs=rs.nextSolution();
						System.out.println(qs);
      							}


		}
		
	}

	public static void pattern2(ArrayList<String> possibleMatch, String dbpRes) {
		

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
			pattern1 = "SELECT ?num " 
						+"WHERE {" 
						+"{ ?num "+res+":"+dbpRes+" "+dbpProTag+":"+dbpPro+" .}"
						+" UNION "
						+"{ ?num "+dbpProTag+":"+dbpPro+" "+res+":"+dbpRes+" .}"
						+"}"; 
			 
			 
			query1=sparqlHeader +pattern1;
			System.out.println(query1);
			QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1);
			ResultSet rs=e.execSelect();
			while (rs.hasNext()) {
						QuerySolution qs=rs.nextSolution();
						System.out.println(qs);
      							}


		}
		
	
		
	}

	public static void pattern3(ArrayList<String> possibleMatch, String dbpRes) {// value of property of resource
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
			pattern1 = "SELECT ?num " 
						+"WHERE {" 
						+"{ "+res+":"+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num .}"
						+" UNION "
						+"{ "+dbpProTag+":"+dbpPro+" "+res+":"+dbpRes+" ?num .}"
						+"}"; 
			 
			 
			query1=sparqlHeader +pattern1;
			System.out.println(query1);
			QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1);
			ResultSet rs=e.execSelect();
			while (rs.hasNext()) {
						QuerySolution qs=rs.nextSolution();
						System.out.println(qs);
      							}


		}
		
	
		
	}
	
	}

	


