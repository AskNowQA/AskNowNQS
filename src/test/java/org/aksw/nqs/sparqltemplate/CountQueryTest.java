package org.aksw.nqs.sparqltemplate;

import org.aksw.asknow.Parser;
import org.aksw.asknow.Template;
import org.aksw.asknow.sparqltemplate.CountQuery;
import org.apache.jena.query.ResultSet;
import org.junit.Test;

public class CountQueryTest
{

	@Test public void test()
	{
		Template t = Parser.parse().get(43); 
		System.out.println(t);
		ResultSet rs = CountQuery.INSTANCE.execute(t);
		while(rs.hasNext())
		{
			System.out.println(rs.next());
		}
	}

}