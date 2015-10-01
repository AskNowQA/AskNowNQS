package org.aksw.nqs.jena;

import org.apache.jena.query.ResultSet;

public class RankingJena
{
	public static ResultSet execute(String dbpRes1, String dbpRes2, String parameter, boolean topfirst)
	{
		String query = "SELECT DISTINCT ?uri WHERE {"
				+ "  { ?uri rdf:type dbo:Person . } UNION { ?uri rdf:type dbo:" + dbpRes2+" . }"
				+ "   ?uri dbo:" + parameter + " ?num. " + "?uri ?p res:" + dbpRes1 + " } ";

		return Dbpedia.select(query+" ORDER BY "+(topfirst?"DESC":"ASC")+"(?num) OFFSET 0 LIMIT 1");
	}
}