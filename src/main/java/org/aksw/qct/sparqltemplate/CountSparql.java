package org.aksw.qct.sparqltemplate;

import java.util.*;
import org.aksw.qct.*;
import org.aksw.qct.jena.CountJena;
import org.aksw.qct.jena.Jena;

public class CountSparql {

	public CountSparql(QctTemplate q1) {
		dbpRes = CallSpotlight.getDBpLookup(q1.getInput());
		ResourceResults = Jena.getDbProperty(dbpRes);
		System.out.println("kjfjg   "+ResourceResults.toString());

		int possibleMatchSize=0;
		/*for (String string : ResourceResults) {
				if((string.toLowerCase().contains("number"))||(string.toLowerCase().contains("num"))||(string.toLowerCase().contains("total"))){
					PossibleMatch.add(string); 
					possibleMatchSize++;
					CountJena.pattern1(PossibleMatch,dbpRes);
					}
	
			}
			*/
		///----------------------------------
		if (possibleMatchSize==0){
			for (String string : ResourceResults) {
				
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					System.out.println(q1.getDesireBrackets()+";;;");
					PossibleMatch.add(string); possibleMatchSize++;
					CountJena.pattern2(PossibleMatch,dbpRes);
											}
									}
				}
		else if (possibleMatchSize==0){
			WordNetSynonyms instance = new WordNetSynonyms();
			Set<String> SynonymsWord1 = new HashSet<String>();
			SynonymsWord1 = instance.getSynonyms(q1.getDesireBrackets());
			System.out.println(SynonymsWord1);
			
			String tempDesire;			// create an iterator	
			Iterator<String> iterator =  SynonymsWord1.iterator();
			    while (iterator.hasNext()){
				   	tempDesire=iterator.next();
					for (String string : ResourceResults) {
						
						if(string.toLowerCase().contains(tempDesire.toLowerCase())){
							PossibleMatch.add(string);
												}
										}
				}
			    CountJena.pattern2(PossibleMatch,dbpRes);
			
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
