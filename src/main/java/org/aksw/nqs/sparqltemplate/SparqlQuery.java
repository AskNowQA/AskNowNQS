package org.aksw.nqs.sparqltemplate;

import org.aksw.nqs.Template;
import org.apache.jena.query.ResultSet;

public interface SparqlQuery
{	
	public ResultSet execute(Template template);
}