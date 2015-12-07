package org.aksw.asknow.query.sparql;

import java.util.Set;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class ListSparql {

	public static Set<RDFNode> execute(String dbpRes1,String dbpRes2){
		String query = "SELECT DISTINCT ?uri WHERE {" 
				+"{?uri rdf:type dbo:"+dbpRes2+" . " 
				+"?uri ?p res:"+dbpRes1+" . "
				+"} UNION {res:"+dbpRes1+" dbo:"+dbpRes2+" ?uri . }}"; 
		log.debug(query);	
		return Dbpedia.nodeSet(Dbpedia.select(query));
	}
}