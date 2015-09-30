package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.SimpleJena;
import org.aksw.qct.util.Spotlight;

public class BooleanSparql {

	final String dbpRes1;
	final String dbpRes2;
	final String prop1;
	final String prop2;
	final ArrayList<String> ResourceResults1;
	final ArrayList<String> PossibleMatch1;
	final ArrayList<String> ResourceResults2;
	final ArrayList<String> PossibleMatch2;
	
	public BooleanSparql(QctTemplate q1) {
		ArrayList<String> ResourceResults = new ArrayList<>();
		String tempConcept = q1.getConcepts();
		tempConcept = cleanConcept(tempConcept);
		System.out.println(tempConcept);
		String[] parts = tempConcept.split(",");
		System.out.println(parts[0]);
		if(q1.getRoles().contains("than")){
			System.out.println(q1.getRoles());
		
		}//compare boolean
		
		else{
			System.out.println("is-atypeOfsubset boolean");
			dbpRes1 = Spotlight.getDBpLookup(parts[0]);
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

	}




	private String cleanConcept(String tempConcept) {
		tempConcept = tempConcept.replace(" a ", ",").trim();
		tempConcept = tempConcept.replace(", , ", ",").trim();
		tempConcept = tempConcept.replace(",,", ",").trim();

		return tempConcept;
	}





}
