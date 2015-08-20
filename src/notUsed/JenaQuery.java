package notUsed;
import java.util.ArrayList;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class JenaQuery {

	public static void main(String[] args) {
		String I="Benjamin_Franklin" , D ="child";
		countQuery(D,I);
		getAllDbProperty(D,I);
	}



	public static ArrayList<String> getAllDbProperty (String desire, String Input){
		ArrayList<String> ResourceResults = new ArrayList<String>();//stores all properties from dbp related to QCT's INPUT
		String service = "http://dbpedia.org/sparql";
		String query = "PREFIX dbo: <http://dbpedia.org/ontology/>" 
				+"PREFIX yago: <http://dbpedia.org/class/yago/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX res: <http://dbpedia.org/resource/> "
				+ "SELECT DISTINCT ?property "
				+ "WHERE { "
				+"res:"+Input+ "?property ?value."
				+"} ";
		//System.out.println(query);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		System.out.println("Lenghth"+qe.toString().length());
		try {
			ResultSet results = qe.execSelect();

			for (; results.hasNext();) {
				QuerySolution sol = (QuerySolution) results.next();
				//System.out.println(sol.get("?property"));
				ResourceResults.add(sol.get("?property").toString());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			qe.close();
		}

		System.out.println("ALL Properties are:"+ResourceResults.toString());//print all properties
		return ResourceResults;
	}

	public static void countQuery (String desire, String Input){
		// TODO Auto-generated method stub

		String service1="http://dbpedia.org/sparql";
		String query1="PREFIX dbo: <http://dbpedia.org/ontology/>" 
				+"PREFIX yago: <http://dbpedia.org/class/yago/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX res: <http://dbpedia.org/resource/> "
				+ "SELECT COUNT(DISTINCT ?uri) "
				+ "WHERE { "
				+"res:"+Input+" dbo:"+desire+" ?uri"
				+"} ";

		//System.out.println(query1);


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
