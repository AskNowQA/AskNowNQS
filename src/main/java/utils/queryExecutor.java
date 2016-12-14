package utils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

public class queryExecutor {
	/*
	 * This class executes the sparql query given a sparql in string
	 * The return type of the functions are different 
	 * TODO: make them one function without breaking a lot of code.
	 * 
	 * */
	public static ResultSet query(String sparql){
		Query query = QueryFactory.create(sparql,Syntax.syntaxARQ);
		QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://131.220.153.66:8900/sparql", query );
		ResultSet results = qExe.execSelect();
		return results;
	}
	
	public static boolean queryAsk(String sparql) {
		Query query = QueryFactory.create(sparql,Syntax.syntaxARQ) ;
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://131.220.153.66:8900/sparql",query) ;
		boolean result = qexec.execAsk() ;
		qexec.close();
		return result;
	}

}