package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import org.aksw.asknow.util.XmlUtil;
import org.junit.Test;

public class XmlUtilTest
{
	@Test public void testValidateAgainstXSD()
	{
		assertTrue(XmlUtil.validateAgainstXSD(
				this.getClass().getClassLoader().getResourceAsStream("qald/qald5.nqs.xml"),
				this.getClass().getClassLoader().getResourceAsStream("nqs.xsd")
				));
	}
}