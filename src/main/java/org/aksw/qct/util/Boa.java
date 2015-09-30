package org.aksw.qct.util;
import java.net.*;
import java.util.HashSet;
import java.io.*;
public class Boa {

	public static HashSet<String> getBoaEquivalent(String property) throws Exception{
		HashSet<String> boaSet = new HashSet<>();
		URL oracle = new URL("http://linkedspending.aksw.org/solr/en_boa/select?q=nlr-no-var%3A"+property+"&wt=json&indent=true");
        URLConnection yc = oracle.openConnection();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine="";
       // String temp="", type ="";
        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("\"uri\":")){
            	//System.out.println(inputLine);
            	inputLine= inputLine.substring(inputLine.indexOf("http"),inputLine.lastIndexOf("\""));
        	
        	boaSet.add(inputLine);
        	}
        }
        in.close();
        
		return boaSet;
	}
   public static void main(String[] args) throws Exception { System.out.println(getBoaEquivalent("winner").toString());    }
}