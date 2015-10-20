package org.aksw.nqs.jena;

import java.util.*;
import org.apache.jena.query.ResultSet;

public class CountJena {

	private CountJena() {} // only static methods

	/**
	 * @param candidateUris 
	 * @param dbp Res KB (DBpedia) resources
	 * @param isNumber
	 * @return
	 */
	public static ResultSet execute(Set<String> candidateUris, String dbpRes,boolean isNumber)
	{
		for(String uri: candidateUris)
		{
			List<String> uriParts= Arrays.asList(uri.split("/"));
			int size=uriParts.size();
			
			String dbpPro =uriParts.get(size-1);
			String tag = Dbpedia.tag(uri);
			if(!isNumber)
			{
				return Dbpedia.select(
			"SELECT COUNT ( DISTINCT ?num) " 
					+"WHERE {" 
					+"{ ?num res:"+dbpRes+" "+tag+":"+dbpPro+" .}"
					+" UNION "
					+"{ ?num "+tag+":"+dbpPro+" res:"+dbpRes+" .}"
					+" UNION "
					+"{ res:"+dbpRes+" "+tag+":"+dbpPro+" ?num .}"
					+"}");
			}
			return Dbpedia.select("SELECT DISTINCT ?num WHERE {res:"+dbpRes+" "+tag+":"+dbpPro+" ?num .}");
		}
		throw new IllegalArgumentException("empty candidate uris");
	}
}