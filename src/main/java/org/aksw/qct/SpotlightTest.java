package org.aksw.qct;

import java.net.MalformedURLException;
import java.net.URL;

public class SpotlightTest {
	public static String getDBpLookup(String argument) {
		argument = argument.replaceAll(" ", "%20");
		
		{
			try {
				URL oracle = new URL("http://spotlight.sztaki.hu:2222/rest/annotate?text="+argument);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return argument;
    }
}