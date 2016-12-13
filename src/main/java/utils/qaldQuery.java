package utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hp.hpl.jena.query.ResultSetFormatter;

public class qaldQuery {

public static ArrayList<String> returnResultsQald(String sparql){
		
		/*
		 * Returns a parsed results from a given sparql query.
		 * */
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		ResultSetFormatter.outputAsJSON(outputStream, queryExecutor.query(sparql));
		String json = new String(outputStream.toByteArray());
		JSONParser parser = new JSONParser();
		ArrayList<String> result_temp = new ArrayList<String>();
		try {
			JSONObject json_obj = (JSONObject) parser.parse(json);
			JSONObject json_results = (JSONObject) json_obj.get("results");
			JSONArray json_result_array =  (JSONArray) json_results.get("bindings");
			for(int i=0;i<json_result_array.size();i++){
				JSONObject binding_object = (JSONObject) json_result_array.get(i);
//				System.out.println(binding_object);
				JSONObject binding_object_uri = (JSONObject) binding_object.get("uri");
//				System.out.println(binding_object_uri);
				result_temp.add(binding_object_uri.get("value").toString());
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return result_temp;
		}
		
		return result_temp;
		
	}
	
	
public static ArrayList<String> returnResults(String sparql){
		
		/*
		 * Returns a parsed results from a given sparql query.
		 * */
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, queryExecutor.query(sparql));
		String json = new String(outputStream.toByteArray());
		JSONParser parser = new JSONParser();
		ArrayList<String> result_temp = new ArrayList<String>();
		try {
			JSONObject json_obj = (JSONObject) parser.parse(json);
			JSONObject json_results = (JSONObject) json_obj.get("results");
			JSONArray json_result_array =  (JSONArray) json_results.get("bindings");
			for(int i=0;i<json_result_array.size();i++){
				JSONObject binding_object = (JSONObject) json_result_array.get(i);
//				System.out.println(binding_object);
				JSONObject binding_object_uri = (JSONObject) binding_object.get("var");
//				System.out.println(binding_object_uri);
				result_temp.add(binding_object_uri.get("value").toString());
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return result_temp;
		}
		
		return result_temp;
		
	}
	
}
