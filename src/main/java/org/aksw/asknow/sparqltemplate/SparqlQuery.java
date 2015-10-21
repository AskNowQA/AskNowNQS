package org.aksw.asknow.sparqltemplate;

import java.util.Set;
import org.aksw.asknow.Template;
import org.apache.jena.rdf.model.RDFNode;

public interface SparqlQuery
{	
	public Set<RDFNode> execute(Template template);
}