package org.aksw.asknow.query;

import java.util.Set;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.query.sparql.ListSparql;
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
		if(t.qct.contains("[I1_1]")){
			//complex list
			throw new NotImplementedException("complex list");
		}
		else{
			//simple list\
			String tempInput = t.getInput();
			log.debug(tempInput);
			String[] parts = tempInput.split(" ", 2);
			String dbpRes1 = parts[0];
			String dbpRes2 = parts[1];
			log.debug(dbpRes1 +":"+dbpRes2);
			dbpRes1 = Spotlight.getDBpLookup(dbpRes1.substring(0, 1).toUpperCase() + dbpRes1.substring(1));
			dbpRes2 = Spotlight.getDBpLookup(dbpRes2);
			log.debug(dbpRes1 +":"+dbpRes2);
			return ListSparql.execute(dbpRes1, dbpRes2);
		}	
	}
}