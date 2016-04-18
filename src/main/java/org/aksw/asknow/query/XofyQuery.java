package org.aksw.asknow.query;

import java.util.*;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.query.sparql.*;
import org.aksw.asknow.query.sparql.XofySparql.PatternType;
import org.aksw.asknow.util.EntityAnnotate;
import org.aksw.asknow.util.FuzzyMatch;
import org.aksw.asknow.util.Spotlight;
import org.aksw.asknow.util.WordNetSynonyms;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

/** TODO KO@Mohnish: Add Javadoc */
@Slf4j public class XofyQuery implements Query {

	private XofyQuery() {}
	public static final XofyQuery INSTANCE = new XofyQuery();

	@Override public Set<RDFNode> execute(Nqs q1)
	{
		Set<String> properties = new HashSet<>();
		Set<String> possibleMatches = new HashSet<>();
		String dbpRes;
	
		dbpRes = Spotlight.getDBpLookup(q1.getInput());
		
		if (dbpRes==""){
			dbpRes = EntityAnnotate.annotation(q1.nlQuery);
		}
		if (dbpRes==""){
			System.out.println("Could not annotate the Entity");
			return null;
		}
		//dbpRes = EntityAnnotateQald.annotation("The "+q1.getInput());
		//dbpRes="<http://dbpedia.org/resource/The_Big_Bang_Theory>";
		//dbpRes="<"+dbpRes+">";
		properties = PropertyValue.getProperties(dbpRes);
		
		log.trace("properties: "+properties);
		int possibleMatchSize = 0;
		Set<RDFNode> nodes = new HashSet<>();
		if(q1.getDesire().contains("DataProperty")){
			log.trace(q1.getDesireBrackets()+";;;;;;");
			for (String string : properties) {
				
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					possibleMatches.add(string); possibleMatchSize++;log.trace("...."+string);
					if(q1.nlQuery.contains(" of ")){
						nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN1));//property of resourse
						System.out.println("property of resourse");
					}
					else{						
						nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
						System.out.println("property not-of resourse");
					}
				}
			}
			possibleMatches.add(FuzzyMatch.getmatch(q1.getRelation2(), properties));
		 return	XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN1);
			
		}
		else
		{ 

			if (possibleMatchSize==0){
				
				for (String string : properties) {
					System.out.println("looking for:"+q1.getDesire().replace("the ", "").trim() +q1.getRelation2() + string.toLowerCase());
					//log.trace(string +" : "+q1.getDesire());
							if(string.toLowerCase().contains(q1.getDesire().replace("the ", "").replaceAll(" ", ""))){
								possibleMatches.add("<"+string+">"); possibleMatchSize++;System.out.println("KK"+string);
									if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
										nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//property of resourse
										}
									else{	System.out.println("patren2Xofy");					
										nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
										}
									}
							
							else if((string.toLowerCase().contains(q1.getRelation2())&&!(q1.getRelation2().trim().equals("of")))){
						possibleMatches.add(string); possibleMatchSize++;System.out.println("rel"+string);
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
							System.out.println("match case 5");
							nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//property of resourse
							break;
						}
						else{	System.out.println("patren2Xofy");					
						nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
						}
					}

				}
			}
			else if (possibleMatchSize==0){

				for (String string : properties) {
					if((string.toLowerCase().contains(q1.getRelation2()))&&(!q1.getRelation2().equals("of"))){
						possibleMatches.add(string); possibleMatchSize++;
						return CountSparql.execute(possibleMatches,dbpRes,false);//TODO update
					}
				}
			}
			else if (possibleMatchSize==0){
				log.trace("SynonymsWord1");


				Set<String> synonymsWord1 = new HashSet<>();
				synonymsWord1 = WordNetSynonyms.getSynonyms(q1.getDesire());

				log.trace(synonymsWord1.toString());

				String tempDesire;			// create an iterator	
				Iterator<String> iterator =  synonymsWord1.iterator();
				while (iterator.hasNext()){
					tempDesire=iterator.next();
					for (String string : properties) {

						if(string.toLowerCase().contains(tempDesire.toLowerCase())){
							possibleMatches.add(string);
						}
					}
				}
				nodes.addAll(XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//TODO update
			}
		}
		 possibleMatches.add(FuzzyMatch.getmatchfrom(q1.getRelation2(),q1.getDesire(), properties));
		 //return	XofySparql.pattern(possibleMatches,dbpRes,PatternType.PATTERN1);
		return nodes;
	}	
}