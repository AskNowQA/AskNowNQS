package utils;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/** API for DBpedia Spotlight. Use {@link #getDBpLookup} in a static fashion.*/
public class spotlight
{

	/** Relies on a http connection to the service, which may be down. 
	 * Returns a json file as a string
	 *  @param phrase
	 ** @return */
	public static JSONArray getDBpLookup(String phrase)
	{
		JSONArray DBpEquivalent= new JSONArray(); 
		String argument = phrase.replaceAll(" ","%20");
		try
		{
			URL oracle = new URL("http://model.dbpedia-spotlight.org/en/annotate?text=" + argument);
			// URL oracle = new URL("http://spotlight.dbpedia.org/rest/annotate?text="+argument);
			//	System.out.println(oracle);
			Document doc = Jsoup.connect("http://model.dbpedia-spotlight.org/en/annotate?text=" + argument).get();
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
//				json_array.put(jo);
				json_array.add(jo);
			}
			DBpEquivalent = json_array;
		}
		catch(Exception e){

		}
		System.out.println(DBpEquivalent);
		System.out.println("****************");
		return DBpEquivalent;	
	}

	/*
	 * This gives multiple values and does not have a pre-defined confidence score
	 * */
	public static JSONArray getDBLookup(String phrase,String confidence){
		
		JSONArray DBpEquivalent= new JSONArray(); 
		String argument = phrase.replaceAll(" ","%20");
		try
		{
			URL oracle = new URL("http://model.dbpedia-spotlight.org/en/annotate?text=" + argument);
			Document doc = Jsoup.connect("http://model.dbpedia-spotlight.org/en/annotate?text=" + argument+"?confidence="+confidence).ignoreContentType(true).timeout(50000).header("Accept", "application/json").get();
			
			JSONArray json_array = new JSONArray();
			
			Element links = doc.select("body").first();
			System.out.println(links.text());			
			
			
			
			/**
			 * 
			 * New parsing code.
			 * 
			 * */
			JSONParser parser = new JSONParser();
			JSONObject json_content = (JSONObject) parser.parse(links.text());
			
			String name = (String) json_content.get("@text");
			System.out.println(name);
			JSONArray resource = (JSONArray) json_content.get("Resources");
			Iterator<JSONObject> iterator = resource.iterator();
			
			while (iterator.hasNext()) {
				
				JSONObject jo = new JSONObject();
				

				JSONObject temp_json_content = iterator.next();
				jo.put("offset",(String) temp_json_content.get("@offset"));
				jo.put("name",(String) temp_json_content.get("@surfaceForm"));
				jo.put("uri",(String) temp_json_content.get("@URI"));
				jo.put("label",(String) temp_json_content.get("@surfaceForm"));
				jo.put("finalScore",(String) temp_json_content.get("@similarityScore"));
				jo.put("contextualScore",(String) temp_json_content.get("@percentageOfSecondRank"));
				json_array.add(jo);

            }
			
			
			/*
			 * 
			 * Old stuff
			 * 
			 * */
			
//			Document doc_json = Jsoup.parse(links.text());
//
//			Elements content = doc.getElementsByTag("@surfaceForm");
//			
//			
//			
//			for (Element link : content) {
//				System.out.println("check");
//				System.out.println(link.attr("@offset"));
//
//				JSONObject jo = new JSONObject();
//				jo.put("offset",link.attr("@offset"));
//				jo.put("name",link.attr("@name"));
//				Elements children = link.children();
//				for (Element child_link : children){
//					jo.put("uri","<http://dbpedia.org/resource/"+child_link.attr("@uri")+">");
//					jo.put("label",child_link.attr("@label"));
//					jo.put("contextualScore",child_link.attr("@contextualScore"));
//					jo.put("finalScore",child_link.attr("@finalScore"));
//				}
////				json_array.put(jo);
//				json_array.add(jo);
//			}
			
			DBpEquivalent = json_array;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return DBpEquivalent;
	}
	
	
	
}