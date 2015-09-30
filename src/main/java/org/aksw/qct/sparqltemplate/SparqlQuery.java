package org.aksw.qct.sparqltemplate;

import org.aksw.qct.Template;
import org.apache.jena.query.ResultSet;

public interface SparqlQuery
{	
	public ResultSet execute(Template template);
}