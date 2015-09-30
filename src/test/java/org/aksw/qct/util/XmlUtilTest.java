package org.aksw.qct.util;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class XmlUtilTest
{

	@Test public void testValidateAgainstXSD()
	{
		assertTrue(XmlUtil.validateAgainstXSD(
				XmlUtilTest.class.getClassLoader().getResourceAsStream("qctpool.xml"),
				XmlUtilTest.class.getClassLoader().getResourceAsStream("qct.xsd")));
	}
}