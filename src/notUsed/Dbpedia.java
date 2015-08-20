package notUsed;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class Dbpedia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input="India";
		String service1="http://dbpedia.org/sparql";
		String query1="PREFIX dbo: <http://dbpedia.org/ontology/>" 
				+"PREFIX yago: <http://dbpedia.org/class/yago/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX res: <http://dbpedia.org/resource/> "
				+ "SELECT COUNT(DISTINCT ?uri) "
				+ "WHERE { "
				+"res:Benjamin_Franklin dbo:child ?uri"
				+"} ";

		System.out.println(query1);
		
		
		try(QueryEngineHTTP qe = new QueryEngineHTTP(service1,query1))
		{
			ResultSet rs=qe.execSelect();
			while (rs.hasNext()) {
				QuerySolution qs=rs.nextSolution();
				System.out.println(qs);
			}
		}
	}

}