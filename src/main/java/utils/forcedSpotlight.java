package utils;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
//import lombok.extern.slf4j.Slf4j;
import java.util.regex.Pattern;


public class forcedSpotlight {
	
	//Sometimes the spotlight is unable to find basic entites for example Ganga
	//For this we simple hit the dbpedia server directly by forming own url 
	
	public static String getDbpEntity(String entity)
	{
		// case1 resource
		String uri;
		entity = entity.trim();
		entity = entity.replaceAll(" ",	"_");

		uri = checkRes(entity);
		if (!uri.contains("pageNotFound")) {
			return uri; }
		uri = checkOntology(entity);
		if (!uri.contains("pageNotFound")) { 
			return uri; }
		uri = checkRes(entity);
		if (!uri.contains("pageNotFound")) { 
			return uri; }
		uri = checkOntology(entity);
		if (!uri.contains("pageNotFound")) {
			return uri; }
		entity = entity.substring(0,1).toUpperCase()+ entity.substring(1);
		uri = checkRes(entity);
		if (!uri.contains("pageNotFound")) { 
			return uri; }
		uri = checkOntology(entity);
		if (!uri.contains("pageNotFound")) { 
			return uri; }

		return "notFound";
	}

	public static String checkRes(String entity)
	{

		try
		{
			URL uri = new URL("http://dbpedia.org/page/" + entity);
			URLConnection yc = uri.openConnection();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
			{
				String temppage = in.toString();
				if (temppage.contains(
						"No further information is available. (The requested entity is unknown)"))
					return ("pageNotFound");
				else return uri.toString();
			}

		}
		catch (MalformedURLException e)
		{
			System.out.println(
					" the URL is not in a valid form");
		}
		catch (IOException e)
		{
			// System.out.println(" the connection couldn't be established");
			return ("pageNotFound");
		}
		return null;
	}

	public static String checkOntology(String entity)
	{

		try
		{
			URL uri = new URL("http://dbpedia.org/ontology/" + entity);
		
			URLConnection yc = uri.openConnection();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())))
			{
				String temppage = in.toString();
				if (temppage.contains(
						"No further information is available. (The requested entity is unknown)"))
					return ("pageNotFound");
				else 
					return "<"+uri+">";
			}

		}
		catch (MalformedURLException e)
		{
			System.out.println(
					" the URL is not in a valid form");
		}
		catch (IOException e)
		{
			// System.out.println(" the connection couldn't be established");
			return ("pageNotFound");
		}
		// TODO KO@MO: why return null? check and comment
		return null;

}

}
