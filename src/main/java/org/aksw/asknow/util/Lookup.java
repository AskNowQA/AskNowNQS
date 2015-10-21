package org.aksw.asknow.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import lombok.SneakyThrows;

public class Lookup {

	@SneakyThrows
	public static String getDBpLookup(String argument){
		URL oracle = new URL("http://lookup.dbpedia.org/api/search/KeywordSearch?QueryClass=place&QueryString="+argument);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine,DBpEquivalent="";
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