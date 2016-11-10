package utils;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

/** API for DBpedia Spotlight. Use {@link #getDBpLookup} in a static fashion.*/
public class spotlight
{

	/** Relies on a http connection to the service, which may be down. 
	 * Returns a json file as a string
	 *  @param phrase
	 ** @return */
	public static JSONArray getDBpLookup(String phrase)
	{
		System.out.println("phrase is " + phrase);
		JSONArray DBpEquivalent= new JSONArray(); 
		String argument = phrase.replaceAll(" ","%20");
		try
		{
			URL oracle = new URL("http://spotlight.sztaki.hu:2222/rest/annotate?text=" + argument);
			// URL oracle = new URL("http://spotlight.dbpedia.org/rest/annotate?text="+argument);
			//	System.out.println(oracle);
			Document doc = Jsoup.connect("http://spotlight.sztaki.hu:2222/rest/candidates?text=" + argument).get();
			//				parsing file in a new way
			JSONArray json_array = new JSONArray();
			Elements content = doc.getElementsByTag("surfaceForm");

			for (Element link : content) {

				JSONObject jo = new JSONObject();
				jo.put("offset",link.attr("offset"));
				jo.put("name",link.attr("name"));
				Elements children = link.children();
				for (Element child_link : children){
					jo.put("uri","<http://dbpedia.org/resource/"+child_link.attr("uri")+">");
					jo.put("label",child_link.attr("label"));
					jo.put("contextualScore",child_link.attr("contextualScore"));
					jo.put("finalScore",child_link.attr("finalScore"));
				}
				json_array.put(jo);
			}
			DBpEquivalent = json_array;
		}
		catch(Exception e){

		}
		return DBpEquivalent;	
	}

	
	
}