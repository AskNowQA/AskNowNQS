package org.aksw.nqs.util;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import org.aksw.asknow.util.WordNetSynonyms;
import org.junit.Test;

public class WordNetSynonymsTest
{
	@Test public void test()
	{
		System.out.println(WordNetSynonyms.getSynonyms("study"));
		assertTrue(WordNetSynonyms.getSynonyms("study").containsAll(Arrays.asList("study", "work", "subject")));
	}
}