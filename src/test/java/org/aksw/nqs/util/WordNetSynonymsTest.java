package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import org.junit.Test;

public class WordNetSynonymsTest
{

	@Test public void test()
	{
		assertTrue(WordNetSynonyms.getSynonyms("study").contains(Arrays.asList("study", "work", "subject")));
	}

}
