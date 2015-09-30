package org.aksw.qct.sparqltemplate;

import org.aksw.qct.Parser;
import org.aksw.qct.Template;
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
