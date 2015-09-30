package org.aksw.qct.sparqltemplate;

import java.util.*;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.CountJena;
import org.aksw.qct.jena.SimpleJena;
import org.aksw.qct.util.Spotlight;
import org.aksw.qct.util.WordNetSynonyms;

public class CountSparql {

	ArrayList<String> ResourceResults = new ArrayList<>();
	ArrayList<String> PossibleMatch = new ArrayList<>();
	
	String dbpRes;
	String dbpResTag;
	String dbpPro;
	String dbpProTag;
	Spotlight sp1 = new Spotlight();

	public CountSparql(QctTemplate q1) {
		dbpRes = Spotlight.getDBpLookup(q1.getInput());
		ResourceResults = SimpleJena.getDbProperty(dbpRes);
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
			Set<String> SynonymsWord1 = new HashSet<>();
			SynonymsWord1 = WordNetSynonyms.getSynonyms(q1.getDesireBrackets());
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
}