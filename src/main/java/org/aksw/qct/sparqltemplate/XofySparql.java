package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.aksw.qct.CallSpotlight;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.WordNetSynonyms;
import org.aksw.qct.jena.CountJena;
import org.aksw.qct.jena.Jena;
import org.aksw.qct.jena.XofyJena;

public class XofySparql {

	public XofySparql(QctTemplate q1) {
		dbpRes = CallSpotlight.getDBpLookup(q1.getInput());
		
		ResourceResults = Jena.getDbProperty(dbpRes);
		System.out.println(ResourceResults.toString());
		int possibleMatchSize = 0;
		if(q1.getDesire().contains("DataProperty")){
			System.out.println(q1.getDesireBrackets()+";;;;;;");
			for (String string : ResourceResults) {
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					PossibleMatch.add(string); possibleMatchSize++;System.out.println("...."+string);
					if(q1.getNLQuery().contains(" of ")){
						XofyJena.pattern3(PossibleMatch,dbpRes);//property of resourse
					}
					else{						
						XofyJena.pattern2(PossibleMatch,dbpRes);//TODO update
					}
				}
			}
			}
		else
		{
			
			if (possibleMatchSize==0){
				for (String string : ResourceResults) {
					System.out.println("looking for:"+q1.getDesire());
					//System.out.println(string +" : "+q1.getDesire());
					if(string.toLowerCase().contains(q1.getDesire())){
						PossibleMatch.add(string); possibleMatchSize++;System.out.println("KK"+string);
						if(q1.getNLQuery().contains(" of ")||q1.getNLQuery().contains("In ")){
							XofyJena.pattern3(PossibleMatch,dbpRes);//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
							XofyJena.pattern2(PossibleMatch,dbpRes);//TODO update
						}
					}
					
					else if(string.toLowerCase().contains(q1.getRelation2())){
						PossibleMatch.add(string); possibleMatchSize++;System.out.println("rel"+string);
						if(q1.getNLQuery().contains(" of ")||q1.getNLQuery().contains("In ")){
							XofyJena.pattern3(PossibleMatch,dbpRes);//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
							XofyJena.pattern2(PossibleMatch,dbpRes);//TODO update
						}
					}
					
					
					
					
					
					
					
				}
			}
			else if (possibleMatchSize==0){
			
				for (String string : ResourceResults) {
					if((string.toLowerCase().contains(q1.getRelation2()))&&(!q1.getRelation2().equals("of"))){
						PossibleMatch.add(string); possibleMatchSize++;System.out.println("lll"+string);
						CountJena.pattern2(PossibleMatch,dbpRes);//TODO update
					}
				}
			}
			else if (possibleMatchSize==0){
				System.out.println("SynonymsWord1");

				
				Set<String> SynonymsWord1 = new HashSet<String>();
				SynonymsWord1 = WordNetSynonyms.getSynonyms(q1.getDesire());
				
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
				XofyJena.pattern3(PossibleMatch,dbpRes);//TODO update
			}
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