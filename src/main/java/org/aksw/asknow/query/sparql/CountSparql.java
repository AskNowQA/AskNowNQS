package org.aksw.asknow.query.sparql;

import java.util.*;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

/**  */
@Slf4j public class CountSparql {

	private CountSparql() {} // only static methods

	/**
	 * @param candidateUris 
	 * @param dbp Res KB (DBpedia) resources
	 * @param isNumber
	 * @return
	 */
	public static Set<RDFNode> execute(Set<String> candidateUris, String dbpRes,boolean isNumber)
//	public static long execute(Set<String> candidateUris, String dbpRes,boolean isNumber)
	{
		for(String uri: candidateUris)
		{
			List<String> uriParts= Arrays.asList(uri.split("/"));
			int size=uriParts.size();
			
			String dbpPro =uriParts.get(size-1);
			String tag = Dbpedia.tag(uri);
			if(!isNumber)
			{
				String query = "SELECT COUNT ( DISTINCT ?num) as ?count" 
						+"WHERE {" 
						+"{ ?num res:"+dbpRes+" "+tag+":"+dbpPro+" .}"
						+" UNION "
						+"{ ?num "+tag+":"+dbpPro+" res:"+dbpRes+" .}"
						+" UNION "
						+"{ res:"+dbpRes+" "+tag+":"+dbpPro+" ?num .}"
						+"}";
				log.debug(query);
				return Collections.singleton(Dbpedia.select(
			query).next().getLiteral("?count"));
				//next().getLiteral("?count").getLong();
			}
			return Collections.singleton(Dbpedia.select(
					"SELECT DISTINCT ?num WHERE {res:"+dbpRes+" "+tag+":"+dbpPro+" ?num .}")
//					.next().getLiteral("?num").getLong();
					.next().getLiteral("?num"));
		}
		throw new IllegalArgumentException("empty candidate uris");
	}
}