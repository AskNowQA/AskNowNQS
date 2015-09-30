package org.aksw.qct.jena;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class JenaList {

	static String sparqlHeader ="PREFIX dbo: <http://dbpedia.org/ontology/>" 
			+ "PREFIX yago: <http://dbpedia.org/class/yago/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX res: <http://dbpedia.org/resource/> ";

	public static void query1(String dbpRes1,String dbpRes2){//,String dbpResTag1,String dbpResTag2){
		String pattern1 = sparqlHeader
				+"SELECT DISTINCT ?uri " 
				+"WHERE {" 
				+"{?uri rdf:type dbo:"+dbpRes2+" . " 
				+"?uri ?p res:"+dbpRes1+" . "
				+"} UNION {res:"+dbpRes1+" dbo:"+dbpRes2+" ?uri . }}"; 

		String service1="http://dbpedia.org/sparql";
		System.out.println(pattern1);
		
		QueryExecution e=QueryExecutionFactory.sparqlService(service1, pattern1);
		System.out.println("cp1");
		System.out.println(e.execSelect().toString());
		ResultSet rs=e.execSelect();
		System.out.println("cp2");
		while (rs.hasNext()) {
			System.out.println("cp\3");
			QuerySolution qs=rs.nextSolution();
			System.out.println(qs);
		}
	}
}

