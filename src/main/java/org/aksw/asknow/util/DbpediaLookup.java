package org.aksw.asknow.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import lombok.SneakyThrows;

/** DBpedia Lookup Service - Find DBpedia URIs for keywords. Use {@link #getDBpLookup(String)} in a static fashion.*/
public class DbpediaLookup {

	/** Returns a DBpedia URI for the given keywords. Creates an http connection.
	 * @param keywords //TODO KO@MO anything special about the keywords? what format (separators)?
	** @return a DBpedia resource URI, may be null.*/
	@SneakyThrows
	public static String getDBpLookup(String keywords){
		URL oracle = new URL("http://lookup.dbpedia.org/api/search/KeywordSearch?QueryClass=place&QueryString="+keywords);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        String DBpEquivalent = null;
        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("<URI>")){
            	inputLine= inputLine.replace("<URI>", "");
            	inputLine= inputLine.replace("</URI>", "");
            	DBpEquivalent=inputLine.trim();
            	break;}
        }
        in.close();
		return DBpEquivalent;
	}
	
}