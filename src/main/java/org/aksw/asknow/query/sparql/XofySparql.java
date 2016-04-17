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
		int i=0;
		for(String p: properties)
		{
			String dbpPro;
			String dbpProTag;
			//System.out.println(temp.toString());
			List<String> aList= Arrays.asList(p.split("/"));
			i=aList.size();
			//System.out.println(aList.get(i-2).toString()+count);
			if (aList.get(i-2).equals("ontology"))
				dbpProTag="dbo";
			else if (aList.get(i-2).equals("property"))
				dbpProTag="dbp";
			else throw new IllegalArgumentException(aList.get(i-2)+"is neither ontology nor property");
			dbpPro=aList.get(i-1).toString();
			String pattern;
			switch(type)
			{
				case PATTERN1: pattern =
					"SELECT DISTINCT ?num " 
					+"WHERE {" 
					+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num ." 
					+"}";
					break;
				case PATTERN2: pattern =
					"SELECT ?num " 
					+"WHERE {" 
					+"{ ?num "+dbpRes+" "+dbpProTag+":"+dbpPro+" .}"
					+" UNION "
					+"{ ?num "+dbpProTag+":"+dbpPro + dbpRes+" .}"
					+"}"; 
				break;
				case PATTERN3: pattern =
					"SELECT ?num " 
					+"WHERE {" 
					+"{"+dbpRes+" "+dbpProTag+":"+dbpPro+" ?num .}"
					+" UNION "
					+"{ "+dbpProTag+":"+dbpPro+" "+dbpRes+" ?num .}"
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