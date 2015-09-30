package org.aksw.qct.sparqltemplate;

import org.aksw.qct.Template;
import org.aksw.qct.jena.JenaList;
import org.aksw.qct.util.Spotlight;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.ResultSet;

public class ListQuery implements SparqlQuery {

	private ListQuery() {}
	public static final ListQuery INSTANCE = new ListQuery();

	@Override public ResultSet execute(Template t)
	{
		if(t.qct.contains("[I1_1]")){
			//complex list
			throw new NotImplementedException("complex list");
		}
		else{
			//simple list\
			String tempInput = t.getInput();
			System.out.println(tempInput);
			String[] parts = tempInput.split(" ", 2);
			String dbpRes1 = parts[0];
			String dbpRes2 = parts[1];
			System.out.println(dbpRes1 +":"+dbpRes2);
			dbpRes1 = Spotlight.getDBpLookup(dbpRes1.substring(0, 1).toUpperCase() + dbpRes1.substring(1));
			dbpRes2 = Spotlight.getDBpLookup(dbpRes2);
			System.out.println(dbpRes1 +":"+dbpRes2);
			return JenaList.execute(dbpRes1, dbpRes2);
		}	
	}
}