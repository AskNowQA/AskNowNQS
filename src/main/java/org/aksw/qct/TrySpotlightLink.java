package org.aksw.qct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//http://dbpedia.org/ontology/Holiday

public class TrySpotlightLink {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getDbpEntity("non-profit organizations"));

	}
	
	
	public static String getDbpEntity(String entity){
		//case1 resource
		String url;
		entity=entity.trim();
		entity = entity.replaceAll(" ", "_");
				
		url = checkRes(entity.substring(0, entity.length()-2));
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		url =checkOntology(entity);
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		url = checkRes(entity);
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		url =checkOntology(entity);
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		entity = entity.substring(0, 1).toUpperCase() + entity.substring(1);
		url = checkRes(entity);
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		url =checkOntology(entity);
		if (!url.contains("pageNotFound"))
		{
			return url;
		}
		
		return "notFound";
	}
	public static String checkRes(String entity){
		
		try {
		    URL url = new URL("http://dbpedia.org/page/"+entity);
		    URLConnection conn = url.openConnection();
		   // conn.connect();
		  
		    URLConnection yc = url.openConnection();
		    
		    try(BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
			{
				String temppage =in.toString();
				if (temppage.contains("No further information is available. (The requested entity is unknown)"))
					return ("pageNotFound"); 
				else
					return url.toString();

			}
		    
		    
		} catch (MalformedURLException e) {
		    System.out.println(" the URL is not in a valid form");
		} catch (IOException e) {
			//System.out.println(" the connection couldn't be established");
			return ("pageNotFound");
		}
		return null;
		
		
		
		
	}
	
public static String checkOntology(String entity){
		
		try {
		    URL url = new URL("http://dbpedia.org/ontology/"+entity);
		    URLConnection conn = url.openConnection();
		   // conn.connect();
		  
		    URLConnection yc = url.openConnection();
		    
		    try(BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
			{
				String temppage =in.toString();
				if (temppage.contains("No further information is available. (The requested entity is unknown)"))
					return ("pageNotFound"); 
				else
					return "PageFound"+url;

			}
		    
		    
		} catch (MalformedURLException e) {
		    System.out.println(" the URL is not in a valid form");
		} catch (IOException e) {
			//System.out.println(" the connection couldn't be established");
			return ("pageNotFound");
		}
		return null;
		
		
		
		
	}
	
	/*
	 * //preprocess 
	 */
	/////removespace
	//entity = entity.replaceAll(" ", "");
	//Array
	//loop
	///check
	////true /break

	/*	
		try
		{
			URL url = new URL("http://dbpedia.org/page/"+entity);
			//URL oracle = new URL("http://spotlight.dbpedia.org/rest/annotate?text="+argument);
			try
			{
				URLConnection yc = url.openConnection();
				try(BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
				{
					String temppage =in.toString();
					if (temppage.contains("No further information is available. (The requested entity is unknown)"))
						System.out.println("pageNotFound"+url);
					else
						System.out.println("PageFound"+url);

				}
			} catch(IOException e) {System.out.println("helo"); throw new RuntimeException(e);}
		} catch(MalformedURLException e) {throw new IllegalArgumentException(entity, e);}

		return entity;

	}
*/

}
