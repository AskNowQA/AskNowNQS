package org.aksw.nqs.jena;

import org.apache.jena.query.ResultSet;

public class JenaList {

	public static ResultSet execute(String dbpRes1,String dbpRes2){
		String query = "SELECT DISTINCT ?uri WHERE {" 
				+"{?uri rdf:type dbo:"+dbpRes2+" . " 
				+"?uri ?p res:"+dbpRes1+" . "
				+"} UNION {res:"+dbpRes1+" dbo:"+dbpRes2+" ?uri . }}"; 
		System.out.println(query);	
		return Dbpedia.select(query);
	}
}