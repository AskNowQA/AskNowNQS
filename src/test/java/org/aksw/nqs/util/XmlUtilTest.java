package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class XmlUtilTest
{
	@Test public void testValidateAgainstXSD()
	{
		assertTrue(XmlUtil.validateAgainstXSD(
				this.getClass().getClassLoader().getResourceAsStream("benchmark.xml"),
				this.getClass().getClassLoader().getResourceAsStream("benchmark.xsd")
				));
	}
}