package org.aksw.nqs.sparqltemplate;

import java.util.*;
import org.aksw.nqs.Template;
import org.aksw.nqs.jena.*;
import org.aksw.nqs.util.Spotlight;
import org.aksw.nqs.util.WordNetSynonyms;

public class XofyQuery {

	public XofyQuery(Template q1) {
		Set<String> properties = new HashSet<>();
		Set<String> possibleMatches = new HashSet<>();

		dbpRes = Spotlight.getDBpLookup(q1.getInput());
		
		properties = PropertyValue.getProperties(dbpRes);
		System.out.println(properties.toString());
		int possibleMatchSize = 0;
		if(q1.getDesire().contains("DataProperty")){
			System.out.println(q1.getDesireBrackets()+";;;;;;");
			for (String string : properties) {
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					possibleMatches.add(string); possibleMatchSize++;System.out.println("...."+string);
					if(q1.nlQuery.contains(" of ")){
						XofyJena.pattern3(possibleMatches,dbpRes);//property of resourse
					}
					else{						
						XofyJena.pattern2(possibleMatches,dbpRes);//TODO update
					}
				}
			}
			}
		else
		{
			
			if (possibleMatchSize==0){
				for (String string : properties) {
					System.out.println("looking for:"+q1.getDesire());
					//System.out.println(string +" : "+q1.getDesire());
					if(string.toLowerCase().contains(q1.getDesire())){
						possibleMatches.add(string); possibleMatchSize++;System.out.println("KK"+string);
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
							XofyJena.pattern3(possibleMatches,dbpRes);//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
							XofyJena.pattern2(possibleMatches,dbpRes);//TODO update
						}
					}
					
					else if(string.toLowerCase().contains(q1.getRelation2())){
						possibleMatches.add(string); possibleMatchSize++;System.out.println("rel"+string);
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
							XofyJena.pattern3(possibleMatches,dbpRes);//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
							XofyJena.pattern2(possibleMatches,dbpRes);//TODO update
						}
					}
					
					
					
					
					
					
					
				}
			}
			else if (possibleMatchSize==0){
			
				for (String string : properties) {
					if((string.toLowerCase().contains(q1.getRelation2()))&&(!q1.getRelation2().equals("of"))){
						possibleMatches.add(string); possibleMatchSize++;System.out.println("lll"+string);
						CountJena.execute(possibleMatches,dbpRes,false);//TODO update
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
					for (String string : properties) {

						if(string.toLowerCase().contains(tempDesire.toLowerCase())){
							possibleMatches.add(string);
						}
					}
				}
				XofyJena.pattern3(possibleMatches,dbpRes);//TODO update
			}
		}

	}


	
	String dbpRes;
	String dbpResTag;
	String dbpPro;
	String dbpProTag;
	Spotlight sp1 = new Spotlight();
}