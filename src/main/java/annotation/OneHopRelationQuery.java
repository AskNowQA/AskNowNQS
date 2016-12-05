package annotation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import utils.queryExecutor;

import com.hp.hpl.jena.query.ResultSetFormatter;

public class OneHopRelationQuery {
	public static ArrayList<ArrayList<String[]>> getPredicateList(String dbr){
		String sparql = "select distinct ?rel ?label where { {"
				+ dbr+" ?rel ?x"
				+ "?rel <http://www.w3.org/2000/01/rdf-schema#label> ?label."
				+ "filter(langMatches(lang(?label),\"EN\")) }";
		
		String sparqlout = "select distinct ?rel ?label where { {"
				+ "?x ?rel" + dbr 
				+ "?rel <http://www.w3.org/2000/01/rdf-schema#label> ?label."
				+ "filter(langMatches(lang(?label),\"EN\")) }";
		
		
		String sparql_incoming = "";
		String sparql_outgoing = "";
		ArrayList<String[]> incomingPropertyList = executeOneHop(sparql_incoming);
		ArrayList<String[]> outgoingPropertyList = executeOneHop(sparql_incoming);
		ArrayList<ArrayList<String[]>> allPropertyList = new ArrayList<ArrayList<String[]>>();
		allPropertyList.add(incomingPropertyList);
		allPropertyList.add(outgoingPropertyList);
		return allPropertyList;
	}
	
	
	public static ArrayList<String[]> executeOneHop(String sparql){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		ResultSetFormatter.outputAsJSON(outputStream, queryExecutor.query(sparql));
		
		String json = new String(outputStream.toByteArray());
		//System.out.println(json);
		String[] temp = new String[2]; 
		ArrayList<String[]> listOfPair = new ArrayList<String[]>();
		String t=json;
		for(int i=0;i<=json.length();i++){

			try{
				t=t.substring(t.indexOf("\"rel\":"));
				temp[0]=t.substring(t.indexOf("\"value\"")+10,t.indexOf("}")-2 );  
				t=t.substring(t.indexOf("\"label\":"));
				temp[1]=t.substring(t.indexOf("\"value\"")+10,t.indexOf("}")-2 );
				listOfPair.add(new String[] {temp[0], temp[1]}); 
			}
			catch (Exception e){
				break;
			}
		}	
		return listOfPair;
	}


}
