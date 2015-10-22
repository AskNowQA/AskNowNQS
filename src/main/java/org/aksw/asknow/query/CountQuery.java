package org.aksw.asknow.query;

import java.util.*;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.query.sparql.CountSparql;
import org.aksw.asknow.query.sparql.PropertyValue;
import org.aksw.asknow.util.Spotlight;
import org.aksw.asknow.util.WordNetSynonyms;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.rdf.model.RDFNode;

/**
 * Does Numeric and Count queries.
 * Numeric - Already aggregated.
 * Count - Uses SPARQL count to aggregate.
 * 
 * Is a singleton (use {@code INSTANCE}).
 */
public class CountQuery implements Query{
	
	private CountQuery() {}
	public static final CountQuery INSTANCE = new CountQuery();

	/* (non-Javadoc)
	 * @see org.aksw.nqs.sparqltemplate.SparqlQuery#execute(org.aksw.nqs.Template)
	 */
	@Override public Set<RDFNode> execute(Nqs t) {
		
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
					return CountSparql.execute(possibleMatches,dbpRes,true);
					
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
			 
			    return CountSparql.execute(possibleMatches,dbpRes,true);	
			}
		throw new NotImplementedException("no pattern found for count query");
	}
}