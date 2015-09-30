package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;

import org.aksw.qct.CallSpotlight;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.CountJena;
import org.aksw.qct.jena.Jena;

public class BooleanSparql {

	public BooleanSparql(QctTemplate q1) {
		ArrayList<String> ResourceResults = new ArrayList<String>();
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
			dbpRes1 = CallSpotlight.getDBpLookup(parts[0]);
			System.out.println("value bahi"+dbpRes1);
			ResourceResults = Jena.getDbProperty(dbpRes1);
			for (String string : ResourceResults) {
				if(string.toLowerCase().contains(parts[parts.length -1].trim())){
					System.out.println("TRUE");
					}
				}
			ResourceResults = Jena.getDbPropertyValues(dbpRes1);
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




	String dbpRes1 ="";
	String dbpRes2 ="";
	String prop1 ="";
	String prop2 ="";
	ArrayList<String> ResourceResults1 = new ArrayList<String>();
	ArrayList<String> PossibleMatch1 = new ArrayList<String>();
	ArrayList<String> ResourceResults2 = new ArrayList<String>();
	ArrayList<String> PossibleMatch2 = new ArrayList<String>();
}
