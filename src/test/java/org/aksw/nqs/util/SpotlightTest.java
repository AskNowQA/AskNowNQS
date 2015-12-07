package org.aksw.nqs.util;

import static org.junit.Assert.assertEquals;
import java.net.MalformedURLException;
import org.aksw.asknow.util.Spotlight;
import org.junit.Test;

public class SpotlightTest
{
	@Test
	public void testSpotlight() throws MalformedURLException
	{
//		assertEquals("http://dbpedia.org/resource/Barack_Obama",Spotlight.getDBpLookup("Barack Obama"));
		assertEquals("Barack_Obama",Spotlight.getDBpLookup("Barack Obama"));
	}
}