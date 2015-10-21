package org.aksw.asknow.sparqltemplate;

import java.util.*;

import org.aksw.asknow.Template;
import org.aksw.asknow.jena.CountJena;
import org.aksw.asknow.jena.PropertyValue;
import org.aksw.asknow.util.Spotlight;
import org.aksw.asknow.util.WordNetSynonyms;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.ResultSet;

/**
 * Does Numeric and Count queries.
 * Numeric - Already aggregated.
 * Count - Uses SPARQL count to aggregate.
 * 
 * Is a singleton.
 */
public class CountQuery implements SparqlQuery{
	
	private CountQuery() {}
	public static final CountQuery INSTANCE = new CountQuery();

	/* (non-Javadoc)
	 * @see org.aksw.nqs.sparqltemplate.SparqlQuery#execute(org.aksw.nqs.Template)
	 */
	@Override public ResultSet execute(Template t) {
		
		Set<String> properties = new HashSet<>();
		Set<String> possibleMatches = new HashSet<>();
		
		String dbpRes = Spotlight.getDBpLookup(t.getInput());
		properties = PropertyValue.getProperties(dbpRes);
		System.out.println(properties);

		int possibleMatchSize=0;
			
			for (String prop : properties) {
				
				if(prop.toLowerCase().contains(t.getDesireBrackets())){
					System.out.println(t.getDesireBrackets()+";;;");
					possibleMatches.add(prop); possibleMatchSize++;
					
					//Property value is assumed to be number. 
					//Full-match between properties and Desire.
					return CountJena.execute(possibleMatches,dbpRes,true);
					
											}
									}
				
			if (possibleMatchSize==0){
			Set<String> SynonymsWord1 = new HashSet<>();
			SynonymsWord1 = WordNetSynonyms.getSynonyms(t.getDesireBrackets());
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
			 
			    return CountJena.execute(possibleMatches,dbpRes,true);	
			}
		throw new NotImplementedException("no pattern found for count query");
	}
}