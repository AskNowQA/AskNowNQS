package org.aksw.asknow.query.sparql;

import java.util.*;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

// TODO KO@MO: javadoc and comments
@Slf4j public class XofySparql {

	static String sparqlHeader ="PREFIX dbo: <http://dbpedia.org/ontology/>" 
			+ "PREFIX yago: <http://dbpedia.org/class/yago/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX res: <http://dbpedia.org/resource/> ";
	
	// TODO KO@MO: what is the difference between these patterns and when should each be chosen? Rename accordingly.
	public static enum PatternType {PATTERN1,PATTERN2,PATTERN3}
	
	public static Set<RDFNode> pattern(Set<String> properties, String dbpRes, PatternType type) {
		Set<RDFNode> nodes = new HashSet<>();
		//String varible1="";//dbo:capital
	
		for(String p: properties)
		{	
			String pattern;
			switch(type)
			{
				case PATTERN1: pattern =
					"SELECT DISTINCT ?num " 
					+"WHERE {" 
					+dbpRes+" "+ p +" ?num ." 
					+"}";
					break;
				case PATTERN2: pattern =
					"SELECT ?num " 
					+"WHERE {" 
					+"{ ?num "+dbpRes+" "+ p+" .}"
					+" UNION "
					+"{ ?num "+ p + dbpRes+" .}"
					+" UNION {"
					+ dbpRes+" "+ p +" ?num  .}"
					+"}"; 
				break;
				case PATTERN3: pattern =
					"SELECT ?num " 
					+"WHERE {" 
					+"{"+dbpRes+" "+ p +" ?num .}"
					+" UNION "
					+"{ "+ p +" "+dbpRes+" ?num .}"
					+"}";
				break;
				default: throw new RuntimeException("should never happen");
			}

			String query=sparqlHeader +pattern;
			log.debug(query);
			nodes.addAll(Dbpedia.nodeSet(Dbpedia.select(query)));
		}
		return nodes;
	}
	
}