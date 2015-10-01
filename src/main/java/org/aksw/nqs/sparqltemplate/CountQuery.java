package org.aksw.nqs.sparqltemplate;

import java.util.*;
import org.aksw.nqs.Template;
import org.aksw.nqs.jena.CountJena;
import org.aksw.nqs.jena.PropertyValue;
import org.aksw.nqs.util.Spotlight;
import org.aksw.nqs.util.WordNetSynonyms;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.ResultSet;

public class CountQuery implements SparqlQuery{
	
	private CountQuery() {}
	public static final CountQuery INSTANCE = new CountQuery();

	@Override public ResultSet execute(Template t) {
		ArrayList<String> ResourceResults = new ArrayList<>();
		ArrayList<String> PossibleMatch = new ArrayList<>();
		
		String dbpRes = Spotlight.getDBpLookup(t.getInput());
		ResourceResults = PropertyValue.getProperties(dbpRes);
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
				
				if(string.toLowerCase().contains(t.getDesireBrackets())){
					System.out.println(t.getDesireBrackets()+";;;");
					PossibleMatch.add(string); possibleMatchSize++;
					return CountJena.execute(PossibleMatch,dbpRes);
											}
									}
				}
		else if (possibleMatchSize==0){
			Set<String> SynonymsWord1 = new HashSet<>();
			SynonymsWord1 = WordNetSynonyms.getSynonyms(t.getDesireBrackets());
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
			    return CountJena.pattern2(PossibleMatch,dbpRes);	
			}
		throw new NotImplementedException("no pattern found for count query");
	}
}