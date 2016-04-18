package org.aksw.asknow.query.sparql;

import java.util.Set;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;
// TODO KO@MO: javadoc and comments
@Slf4j public class ListSparql {
	// TODO KO@MO: javadoc and comments
	public static Set<RDFNode> execute(String dbpRes1,String dbpRes2){
		String query = "SELECT DISTINCT ?uri WHERE {" 
				+"{?uri rdf:type "+dbpRes2+" . " 
				+"?uri ?p "+dbpRes1+" . "
				+"} UNION {"+dbpRes1+" "+dbpRes2+" ?uri . }}"; 
		log.debug(query);
		System.out.println(query);
		return Dbpedia.nodeSet(Dbpedia.select(query));
	}
}