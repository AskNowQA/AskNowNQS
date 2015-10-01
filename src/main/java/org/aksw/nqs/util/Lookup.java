package org.aksw.nqs.util;
import java.net.*;
import java.io.*;
public class Lookup {

	public static String getDBpLookup(String argument) throws Exception{
		URL oracle = new URL("http://lookup.dbpedia.org/api/search/KeywordSearch?QueryClass=place&QueryString="+argument);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine,DBpEquivalent="";
        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("<URI>")){
            	inputLine= inputLine.replace("<URI>", "");
            	inputLine= inputLine.replace("</URI>", "");
            	System.out.println(inputLine);
            	DBpEquivalent=inputLine;
            	break;}
        }
        in.close();
		return DBpEquivalent;
	}
	
    public static void main(String[] args) throws Exception {
    	
        System.out.println(getDBpLookup("Berlin"));
    }
}