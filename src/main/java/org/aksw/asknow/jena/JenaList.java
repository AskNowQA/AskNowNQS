package org.aksw.asknow.jena;

import java.util.Set;
import org.apache.jena.rdf.model.RDFNode;

public class JenaList {

	public static Set<RDFNode> execute(String dbpRes1,String dbpRes2){
		String query = "SELECT DISTINCT ?uri WHERE {" 
				+"{?uri rdf:type dbo:"+dbpRes2+" . " 
				+"?uri ?p res:"+dbpRes1+" . "
				+"} UNION {res:"+dbpRes1+" dbo:"+dbpRes2+" ?uri . }}"; 
		System.out.println(query);	
		return Dbpedia.nodeSet(Dbpedia.select(query));
	}
}