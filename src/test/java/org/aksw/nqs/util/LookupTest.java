package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import org.aksw.asknow.util.DbpediaLookup;
import org.junit.Test;

public class LookupTest
{
	@Test public void testGetDBpLookup()
	{
		assertTrue(DbpediaLookup.getDBpLookup("Berlin").contains("http://dbpedia.org/resource/Berlin"));
	}
}