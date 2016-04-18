package org.aksw.asknow.query;

import java.util.*;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.query.sparql.CountSparql;
import org.aksw.asknow.query.sparql.PropertyValue;
import org.aksw.asknow.util.EntityAnnotate;
import org.aksw.asknow.util.Spotlight;
import org.aksw.asknow.util.WordNetSynonyms;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

/**
 * Does Numeric and Count queries.
 * Numeric - Already aggregated.
 * Count - Uses SPARQL count to aggregate.
 * 
 * Is a singleton (use {@code INSTANCE}).
 */
@Slf4j public class CountQuery implements Query{
	
	private CountQuery() {}
	public static final CountQuery INSTANCE = new CountQuery();

	/**
	 * @see org.aksw.nqs.sparqltemplate.SparqlQuery#execute(org.aksw.nqs.Template)	 */
	@Override public Set<RDFNode> execute(Nqs t) {
		
		Set<String> properties = new HashSet<>();
		Set<String> possibleMatches = new HashSet<>();
		
		String dbpRes = Spotlight.getDBpLookup(t.getInput());
		if (dbpRes==""){
			dbpRes = EntityAnnotate.annotation(t.nlQuery);
		}
		if (dbpRes==""){
			System.out.println("Could not annotate the Entity");
			return null;
		}
		properties = PropertyValue.getProperties(dbpRes);
		log.debug("properties: "+properties);

		int possibleMatchSize=0;
			System.out.println(t.getRelation2().replaceAll("did","").trim());
			for (String prop : properties) {
				
				if(prop.toLowerCase().contains(t.getDesireBrackets())){
					System.out.println(t.getDesireBrackets()+";;;"+prop);
					possibleMatches.add("<"+prop+">"); possibleMatchSize++;
					
					//Property value is assumed to be number. 
					//Full-match between properties and Desire.
					return CountSparql.execute(possibleMatches,dbpRes,true);
					
						}
				
				}
				
			if (possibleMatchSize==0){
			Set<String> SynonymsWord1 = new HashSet<>();
			SynonymsWord1 = WordNetSynonyms.getSynonyms(t.getDesireBrackets());
			System.out.println("Synonums are "+SynonymsWord1);
			
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