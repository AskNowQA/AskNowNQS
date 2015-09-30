package org.aksw.qct.sparqltemplate;

import java.util.*;
import org.aksw.qct.Template;
import org.aksw.qct.jena.*;
import org.aksw.qct.util.Spotlight;
import org.aksw.qct.util.WordNetSynonyms;

public class XofySparql {

	public XofySparql(Template q1) {
		dbpRes = Spotlight.getDBpLookup(q1.getInput());
		
		ResourceResults = SimpleJena.getDbProperty(dbpRes);
		System.out.println(ResourceResults.toString());
		int possibleMatchSize = 0;
		if(q1.getDesire().contains("DataProperty")){
			System.out.println(q1.getDesireBrackets()+";;;;;;");
			for (String string : ResourceResults) {
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					PossibleMatch.add(string); possibleMatchSize++;System.out.println("...."+string);
					if(q1.nlQuery.contains(" of ")){
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
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
							XofyJena.pattern3(PossibleMatch,dbpRes);//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
							XofyJena.pattern2(PossibleMatch,dbpRes);//TODO update
						}
					}
					
					else if(string.toLowerCase().contains(q1.getRelation2())){
						PossibleMatch.add(string); possibleMatchSize++;System.out.println("rel"+string);
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
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

				
				Set<String> SynonymsWord1 = new HashSet<>();
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


	ArrayList<String> ResourceResults = new ArrayList<>();
	ArrayList<String> PossibleMatch = new ArrayList<>();

	String dbpRes;
	String dbpResTag;
	String dbpPro;
	String dbpProTag;
	Spotlight sp1 = new Spotlight();
}