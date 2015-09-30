package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;
import org.aksw.qct.Template;
import org.aksw.qct.jena.SimpleJena;
import org.aksw.qct.util.Spotlight;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.ResultSet;

public class BooleanQuery implements SparqlQuery{

	private BooleanQuery() {}
	public static final BooleanQuery INSTANCE = new BooleanQuery();

	private String cleanConcept(String tempConcept) {
		tempConcept = tempConcept.replace(" a ", ",").trim();
		tempConcept = tempConcept.replace(", , ", ",").trim();
		tempConcept = tempConcept.replace(",,", ",").trim();

		return tempConcept;
	}

	@Override public ResultSet execute(Template t)
	{
		if(1==1) throw new NotImplementedException("does not conform to interface, needs to be changed");
		ArrayList<String> ResourceResults = new ArrayList<>();
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
			ResourceResults = SimpleJena.getDbProperty(dbpRes1);
			for (String string : ResourceResults) {
				if(string.toLowerCase().contains(parts[parts.length -1].trim())){
					System.out.println("TRUE");
					}
				}
			ResourceResults = SimpleJena.getDbPropertyValues(dbpRes1);
			for (String string : ResourceResults) {
				if(string.toLowerCase().contains(parts[1].trim().toLowerCase().replaceAll(" ", ""))){
					System.out.println("TRUE");break;
					}
				}
		}
		return null;
	}

}