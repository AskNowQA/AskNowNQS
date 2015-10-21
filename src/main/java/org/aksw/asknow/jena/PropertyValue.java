package org.aksw.asknow.jena;
import java.util.HashSet;
import java.util.Set;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/** Provides utility methods to fetch properties and values (objects) for resources in the KB */
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

	/**
	 * @param resource uri in the knowledge base
	 * @return all properties of {@code resource} (all properties p for all triples (resource,p,o) in the kb)
	 */
	public static Set<String> getProperties(String resource){ 
		return getDbp(resource, "property");
	}
	
	/**
	 * @param resource uri in the knowledge base
	 * @return all values of {@code resource} (all values o for all triples (resource,p,o) in the kb)
	 */
	public static Set<String> getValues(String resource){ 
		return getDbp(resource, "value");
	}

}