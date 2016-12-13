package utils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class queryExecutor {
	/*
	 * This class executes the sparql query given a sparql in string
	 * The return type of the functions are different 
	 * TODO: make them one function without breaking a lot of code.
	 * 
	 * */
	public static ResultSet query(String sparql){
		Query query = QueryFactory.create(sparql);
		QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
		ResultSet results = qExe.execSelect();
		return results;
	}
	
	public static boolean queryAsk(String sparql) {
		Query query = QueryFactory.create(sparql) ;
		QueryExecution qexec = QueryExecutionFactory.create(query) ;
		boolean result = qexec.execAsk() ;
		qexec.close();
		return result;
	}

}