package org.aksw.asknow.sparqltemplate;

import java.util.*;
import org.aksw.asknow.Template;
import org.aksw.asknow.jena.*;
import org.aksw.asknow.jena.XofyJena.PatternType;
import org.aksw.asknow.util.Spotlight;
import org.aksw.asknow.util.WordNetSynonyms;
import org.apache.jena.rdf.model.RDFNode;

public class XofyQuery implements SparqlQuery {

	private XofyQuery() {}
	public static final XofyQuery INSTANCE = new XofyQuery();

	@Override public Set<RDFNode> execute(Template q1)
	{
		Set<String> properties = new HashSet<>();
		Set<String> possibleMatches = new HashSet<>();
		String dbpRes;
		String dbpResTag;
		String dbpPro;
		String dbpProTag;

		dbpRes = Spotlight.getDBpLookup(q1.getInput());

		properties = PropertyValue.getProperties(dbpRes);
		System.out.println(properties.toString());
		int possibleMatchSize = 0;
		Set<RDFNode> nodes = new HashSet<>();
		if(q1.getDesire().contains("DataProperty")){
			System.out.println(q1.getDesireBrackets()+";;;;;;");
			for (String string : properties) {
				if(string.toLowerCase().contains(q1.getDesireBrackets())){
					possibleMatches.add(string); possibleMatchSize++;System.out.println("...."+string);
					if(q1.nlQuery.contains(" of ")){
						nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN1));//property of resourse
					}
					else{						
						nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
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
							nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
						nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
						}
					}

					else if(string.toLowerCase().contains(q1.getRelation2())){
						possibleMatches.add(string); possibleMatchSize++;System.out.println("rel"+string);
						if(q1.nlQuery.contains(" of ")||q1.nlQuery.contains("In ")){
							nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//property of resourse
						}
						else{	System.out.println("patren2Xofy");					
						nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN2));//TODO update
						}
					}

				}
			}
			else if (possibleMatchSize==0){

				for (String string : properties) {
					if((string.toLowerCase().contains(q1.getRelation2()))&&(!q1.getRelation2().equals("of"))){
						possibleMatches.add(string); possibleMatchSize++;
						return CountJena.execute(possibleMatches,dbpRes,false);//TODO update
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
				nodes.addAll(XofyJena.pattern(possibleMatches,dbpRes,PatternType.PATTERN3));//TODO update
			}
		}
		return nodes;
	}	
}