package org.aksw.qct;
import java.net.*;
import java.io.*;
public class BOAcall {

	public static String getBoaEquivalent(String property) throws Exception{
		URL oracle = new URL("http://linkedspending.aksw.org/solr/en_boa/select?q=nlr-no-var%3A"+property+"&wt=json&indent=true");
        URLConnection yc = oracle.openConnection();
        //TODO try with resource
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine,BOAequivalent="";
        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("\"uri\":")){
        	System.out.println(inputLine);
        	BOAequivalent=inputLine;
        	break;}
        }
        in.close();
		return BOAequivalent;
	}
   public static void main(String[] args) throws Exception { System.out.println(getBoaEquivalent("produces"));    }
}