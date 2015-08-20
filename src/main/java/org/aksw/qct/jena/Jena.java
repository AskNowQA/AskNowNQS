package org.aksw.qct.jena;

import java.util.ArrayList;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class Jena {

	public static ArrayList<String> getDbProperty(String input){
		ArrayList<String> ResourceResults = new ArrayList<String>();//stores all properties from dbp related to QCT's INPUT

		//dbpedia spotlight



		String service = "http://dbpedia.org/sparql";
		String query = "PREFIX dbo: <http://dbpedia.org/ontology/>" 
				+"PREFIX yago: <http://dbpedia.org/class/yago/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX res: <http://dbpedia.org/resource/> "
				+ "SELECT DISTINCT ?property "
				+ "WHERE { "
				+"res:"+input+ "?property ?value."
				+"} ";
		System.out.println(query);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		System.out.println(qe.toString().length());
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

		// System.out.println(ResourceResults.toString());//print all properties
		return ResourceResults;
	}

	
}
