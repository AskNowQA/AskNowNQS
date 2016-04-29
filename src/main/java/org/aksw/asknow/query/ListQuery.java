package org.aksw.asknow.query;

import java.util.HashSet;
import java.util.Set;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.annotation.Spotlight;
import org.aksw.asknow.query.sparql.ListSparql;
import org.aksw.asknow.query.sparql.PropertyValue;
import org.aksw.asknow.util.EntityAnnotate;
import org.aksw.asknow.util.FuzzyMatch;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

/** A query that expects a list of resources or literals. TODO: KO@Mohnish: is this correct? */
@Slf4j public class ListQuery implements Query {

	private ListQuery() {}
	public static final ListQuery INSTANCE = new ListQuery();

	@Override public Set<RDFNode> execute(Nqs nqs)
	{
	Set<String> properties = new HashSet<>();
	String dbpRes;
	String dbpRes2;
		if(nqs.qct.contains("[I1_1]")){
			System.out.println("complex list");
			//throw new NotImplementedException("complex list");
		
				dbpRes = EntityAnnotate.annotation(nqs.nlQuery);
				//String dbpRes="";
				for(String s: nqs.Resource){
					dbpRes =s;
					break;
				}
				
			if (dbpRes=="")
				{System.out.println("Could not annotate the Entity");
				return null;
				}
			else 
				{System.out.println("er1"+dbpRes);}
			
				String temp = nqs.qct.substring(nqs.qct.indexOf("[I1_1] = ")+9);
			    dbpRes2= temp.substring(0,temp.indexOf(","));
			    System.out.println("er2"+dbpRes2);
			    properties = PropertyValue.getProperties(dbpRes);
			    dbpRes2 = FuzzyMatch.getmatch(dbpRes2,properties);
			    System.out.println("er3"+dbpRes2);
			    return ListSparql.execute(dbpRes, dbpRes2);
			//return null;
		}
		else{
			//simple list\
			String tempInput = nqs.getInput();
			log.debug(tempInput);
			String[] parts = tempInput.split(" ", 2);
			dbpRes = parts[0];
			dbpRes2 = parts[1];
			log.debug(dbpRes +":"+dbpRes2);
			dbpRes = Spotlight.getDBpLookup1(dbpRes.substring(0, 1).toUpperCase() + dbpRes.substring(1));
			dbpRes2 = Spotlight.getDBpLookup1(dbpRes2);
			log.debug(dbpRes +":"+dbpRes2);
			return ListSparql.execute(dbpRes, dbpRes2);
		}	
	}
}