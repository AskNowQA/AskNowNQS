package org.aksw.asknow.sparqltemplate;

import java.util.HashSet;
import java.util.Set;
import org.aksw.asknow.Template;
import org.aksw.asknow.jena.PropertyValue;
import org.aksw.asknow.util.Spotlight;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.rdf.model.RDFNode;

public class BooleanQuery implements SparqlQuery{

	private BooleanQuery() {}
	public static final BooleanQuery INSTANCE = new BooleanQuery();

	private String cleanConcept(String tempConcept) {
		tempConcept = tempConcept.replace(" a ", ",").trim();
		tempConcept = tempConcept.replace(", , ", ",").trim();
		tempConcept = tempConcept.replace(",,", ",").trim();

		return tempConcept;
	}

	@Override public Set<RDFNode> execute(Template t)
	{
		if(1==1) throw new NotImplementedException("does not conform to interface, needs to be changed");
		Set<String> properties = new HashSet<>();
		String tempConcept = t.getConcepts();
		tempConcept = cleanConcept(tempConcept);
		System.out.println(tempConcept);
		String[] parts = tempConcept.split(",");
		System.out.println(parts[0]);
		if(t.getRoles().contains("than")){
			System.out.println(t.getRoles());
		
		}//compare boolean
		
		else{
			System.out.println("is-atypeOfsubset boolean");
			String dbpRes1 = Spotlight.getDBpLookup(parts[0]);
			System.out.println("value bahi"+dbpRes1);
			properties = PropertyValue.getProperties(dbpRes1);
			for (String prop : properties) {
				if(prop.toLowerCase().contains(parts[parts.length -1].trim())){
					System.out.println("TRUE");
					}
				}
			properties = PropertyValue.getValues(dbpRes1);
			for (String prop : properties) {
				if(prop.toLowerCase().contains(parts[1].trim().toLowerCase().replaceAll(" ", ""))){
					System.out.println("TRUE");break;
					}
				}
		}
		return null;
	}

}