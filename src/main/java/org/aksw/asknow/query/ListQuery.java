package org.aksw.asknow.query;

import java.util.HashSet;
import java.util.Set;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.query.sparql.ListSparql;
import org.aksw.asknow.query.sparql.PropertyValue;
import org.aksw.asknow.util.EntityAnnotate;
import org.aksw.asknow.util.FuzzyMatch;
import org.aksw.asknow.util.Spotlight;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

/** A query that expects a list of resources or literals. TODO: KO@Mohnish: is this correct? */
@Slf4j public class ListQuery implements Query {

	private ListQuery() {}
	public static final ListQuery INSTANCE = new ListQuery();

	@Override public Set<RDFNode> execute(Nqs t)
	{
	Set<String> properties = new HashSet<>();
	String dbpRes1;
	String dbpRes2;
		if(t.qct.contains("[I1_1]")){
			System.out.println("complex list");
			//throw new NotImplementedException("complex list");
		
				dbpRes1 = EntityAnnotate.annotation(t.nlQuery);
		
			if (dbpRes1=="")
				{System.out.println("Could not annotate the Entity");
				return null;
				}
			else 
				System.out.println(dbpRes1);
				String temp = t.qct.substring(t.qct.indexOf("[I1_1] = ")+9);
			    dbpRes2= temp.substring(0,temp.indexOf(","));
			    System.out.println(dbpRes2);
			    properties = PropertyValue.getProperties(dbpRes1);
			    dbpRes2 = FuzzyMatch.getmatch(dbpRes2,properties);
			    System.out.println(dbpRes2);
			    return ListSparql.execute(dbpRes1, dbpRes2);
			//return null;
		}
		else{
			//simple list\
			String tempInput = t.getInput();
			log.debug(tempInput);
			String[] parts = tempInput.split(" ", 2);
			dbpRes1 = parts[0];
			dbpRes2 = parts[1];
			log.debug(dbpRes1 +":"+dbpRes2);
			dbpRes1 = Spotlight.getDBpLookup(dbpRes1.substring(0, 1).toUpperCase() + dbpRes1.substring(1));
			dbpRes2 = Spotlight.getDBpLookup(dbpRes2);
			log.debug(dbpRes1 +":"+dbpRes2);
			return ListSparql.execute(dbpRes1, dbpRes2);
		}	
	}
}