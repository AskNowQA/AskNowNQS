package org.aksw.asknow.sparqltemplate;

import org.aksw.asknow.Template;
import org.apache.jena.query.ResultSet;

public interface SparqlQuery
{	
	public ResultSet execute(Template template);
}