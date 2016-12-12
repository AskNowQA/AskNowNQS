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
	//return url of the form <url>
	
	public static String getDbpEntity(String entity)
	{
		// case1 resource
		String uri;
		entity = entity.trim();
		entity = entity.replaceAll(" ",	"_");
		String temp = "";
		uri = checkRes(entity);
		if (!uri.contains("pageNotFound")) {
			temp = "<" + uri + ">" ;
			return temp; }
		try{
			entity = entity.substring(0,1).toUpperCase()+ entity.substring(1);
			uri = checkRes(entity);
			if (!uri.contains("pageNotFound")) { 
					temp = "<" + uri + ">" ;
					return temp;
				}
	
		}
		catch(Exception e){
			return "notFound";
			}
		return "notfound";
	}

	public static String checkRes(String entity)
	{

		try
		{
			URL uri = new URL("http://dbpedia.org/resource/" + entity);
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



}
