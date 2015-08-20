package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;

import org.aksw.qct.CallSpotlight;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.Jena;

public class RankingSparql {

	public RankingSparql(QctTemplate q1) {
		dbpRes = CallSpotlight.getDBpLookup(q1.getInput());
		ResourceResults = Jena.getDbProperty(dbpRes);
		
		if ((q1.getDesire().toLowerCase().contains("defination"))||(q1.getInput().toLowerCase().contains("world"))||(q1.getInput().toLowerCase().contains("earth"))){
			System.out.println("Its a list query qtr1");
			}
		
	}

	ArrayList<String> ResourceResults = new ArrayList<String>();
	ArrayList<String> PossibleMatch = new ArrayList<String>();
	
	String dbpRes;
	String dbpResTag;
	String dbpPro;
	String dbpProTag;
	CallSpotlight sp1 = new CallSpotlight();
		

}
