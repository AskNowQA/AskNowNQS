package org.aksw.asknow.query;

import java.util.Set;
import org.aksw.asknow.Nqs;
import org.apache.jena.rdf.model.RDFNode;

/** Functional interface for answering a NQS with a set of RDF nodes */
public interface Query
{	
	public Set<RDFNode> execute(Nqs template);
}