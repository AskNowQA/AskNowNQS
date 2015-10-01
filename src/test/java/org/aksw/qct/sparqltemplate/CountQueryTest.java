package org.aksw.qct.sparqltemplate;

import org.aksw.nqs.Parser;
import org.aksw.nqs.Template;
import org.aksw.nqs.sparqltemplate.CountQuery;
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