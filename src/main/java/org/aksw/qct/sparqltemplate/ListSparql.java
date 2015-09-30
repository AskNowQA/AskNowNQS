package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.JenaList;
import org.aksw.qct.util.Spotlight;

public class ListSparql {

	public ListSparql(QctTemplate q1) {
		
		
		if(q1.qct.contains("[I1_1]")){
			//complex list
			
		}
		else{
			//simple list\
			String tempInput = q1.getInput();
			System.out.println(tempInput);
			String[] parts = tempInput.split(" ", 2);
			String dbpRes1 = parts[0];
			String dbpRes2 = parts[1];
			System.out.println(dbpRes1 +":"+dbpRes2);
			dbpRes1 = Spotlight.getDBpLookup(dbpRes1.substring(0, 1).toUpperCase() + dbpRes1.substring(1));
			dbpRes2 = Spotlight.getDBpLookup(dbpRes2);
			System.out.println(dbpRes1 +":"+dbpRes2);
			JenaList.query1(dbpRes1, dbpRes2);
		}
	}

	String dbpRes1 ="";
	String dbpRes2 ="";
	String prop1 ="";
	String prop2 ="";
	ArrayList<String> ResourceResults1 = new ArrayList<>();
	ArrayList<String> PossibleMatch1 = new ArrayList<>();
	ArrayList<String> ResourceResults2 = new ArrayList<>();
	ArrayList<String> PossibleMatch2 = new ArrayList<>();
	
}
