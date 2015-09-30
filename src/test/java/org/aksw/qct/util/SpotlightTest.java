package org.aksw.qct.util;

import static org.junit.Assert.assertEquals;
import java.net.MalformedURLException;
import org.junit.Test;

public class SpotlightTest
{
	@Test
	public void testSpotlight() throws MalformedURLException
	{
		assertEquals(Spotlight.getDBpLookup("Barack Obama"),"http://dbpedia.org/resource/Barack_Obama");
	}
}