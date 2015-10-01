package org.aksw.nqs.util;
import java.io.*;
import java.net.*;
import org.aksw.nqs.test.TrySpotlightLink;
public class Spotlight {

	public static String getDBpLookup(String argument1) {
		String DBpEquivalent="";
		String argument = argument1.replaceAll(" ", "%20");
		try
		{   
			URL oracle = new URL("http://spotlight.sztaki.hu:2222/rest/annotate?text="+argument);
			//URL oracle = new URL("http://spotlight.dbpedia.org/rest/annotate?text="+argument);
			try
			{
				URLConnection yc = oracle.openConnection();
				try(BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
				{
					//System.out.println(in.readLine());
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						if(inputLine.contains("href=")){
							inputLine= inputLine.replace("<a href=\"", "");
							inputLine= inputLine.substring(0, inputLine.indexOf('\"'));
							System.out.println(inputLine+":::");
							DBpEquivalent=inputLine;
							//break;
						}
					}
					System.out.println("\n value="+DBpEquivalent);
					if (DBpEquivalent == "")
						{System.out.println("blank");
						try {
							DBpEquivalent=TrySpotlightLink.getDbpEntity(argument1);
							if(!DBpEquivalent.contains("notFound")){
								return getEntity(DBpEquivalent);
							}
							else
								DBpEquivalent = Lookup.getDBpLookup(argument);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						}
					return getEntity(DBpEquivalent);
				}
			} catch(IOException e) {throw new RuntimeException(e);}
		} catch(MalformedURLException e) {throw new IllegalArgumentException(argument, e);}
	}

	public static String getEntity (String uriStirng){
			return uriStirng.substring(uriStirng.lastIndexOf('/')+1);		 
	}

	public static void main(String[] args) throws Exception { System.out.println(getDBpLookup("h"));  }
	

}