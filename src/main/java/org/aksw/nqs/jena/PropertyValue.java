package org.aksw.nqs.jena;
import java.util.HashSet;
import java.util.Set;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class PropertyValue {

	private static Set<String> getDbp(String resource, String variable){ 
		Set<String> properties = new HashSet<>();//stores all properties from dbp related to QCT's INPUT
		String query = "SELECT DISTINCT ?"+variable+" WHERE { "
				+"res:"+resource+ " ?property ?value.} ";
		ResultSet results = Dbpedia.select(query);
		while (results.hasNext()) {
			QuerySolution sol = (QuerySolution) results.next();
			properties.add(sol.get("?"+variable).toString());//" : "+sol.get("?value").toString());//TODO ?
		}
		// System.out.println(ResourceResults.toString());//print all properties
		return properties;
	}


	public static Set<String> getProperties(String resource){ 
		return getDbp(resource, "property");
	}
	
	public static Set<String> getValues(String resource){ 
		return getDbp(resource, "value");
	}

}