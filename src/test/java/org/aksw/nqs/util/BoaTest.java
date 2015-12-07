package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import org.aksw.asknow.util.Boa;
import org.junit.Test;

public class BoaTest
{
	@Test public void testGetBoaEquivalent() throws Exception
	{
		   assertTrue(Boa.getBoaEquivalent("married").contains("http://dbpedia.org/ontology/spouse"));
	}
}