package org.aksw.nqs.sparqltemplate;

import java.util.Set;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.Parser;
import org.aksw.asknow.query.CountQuery;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Test;

public class CountQueryTest
{

	@Test public void test()
	{
		Nqs t = Parser.parse().get(43); 
		System.out.println(t);
		Set<RDFNode> result = CountQuery.INSTANCE.execute(t);
		System.out.println(result);
	}

}