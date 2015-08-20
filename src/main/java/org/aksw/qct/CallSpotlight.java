package org.aksw.qct;
import java.net.*;
import java.io.*;
public class CallSpotlight {

	public static String getDBpLookup(String argument) {
		argument = argument.replaceAll(" ", "%20");
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
					String inputLine,DBpEquivalent="";
					while ((inputLine = in.readLine()) != null) {
						if(inputLine.contains("href=")){
							inputLine= inputLine.replace("<a href=\"", "");
							inputLine= inputLine.substring(0, inputLine.indexOf('\"'));
							System.out.println(inputLine);
							DBpEquivalent=inputLine;
							//break;
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

	//public static void main(String[] args) throws Exception { System.out.println(getDBpLookup("In which military conflicts did Lawrence of Arabia participate"));  }


}