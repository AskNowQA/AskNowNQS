package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LookupTest
{
	@Test public void testGetDBpLookup()
	{
		assertTrue(Lookup.getDBpLookup("Berlin").contains("http://dbpedia.org/resource/Berlin"));
	}
}