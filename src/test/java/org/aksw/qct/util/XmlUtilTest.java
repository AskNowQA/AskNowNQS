package org.aksw.qct.util;

import static org.junit.Assert.assertTrue;
import org.aksw.nqs.util.XmlUtil;
import org.junit.Test;

public class XmlUtilTest
{

	@Test public void testValidateAgainstXSD()
	{
		assertTrue(XmlUtil.validateAgainstXSD(
				XmlUtilTest.class.getClassLoader().getResourceAsStream("benchmark.xml"),
				XmlUtilTest.class.getClassLoader().getResourceAsStream("benchmark.xsd")));
	}
}