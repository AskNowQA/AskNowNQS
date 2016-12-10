package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class parseQald {

	
	public static ArrayList<String[]> parseQald6(String filePath){
		
		//This function parses a QALD6 json file. it returns a arrayList of string array which consists of 
		//question and sparql.
		
		
        JSONParser parser = new JSONParser();
        ArrayList<String[]> question_sparql = new ArrayList<String[]>();
        
        
		try {
			JSONObject a = (JSONObject) parser.parse(new FileReader(filePath));
			JSONArray questions = (JSONArray) a.get("questions");
			
			
			
			for(int i=0;i<questions.size();i++){
				JSONObject obj2 = (JSONObject)questions.get(i);
				JSONArray obj2_questions = (JSONArray) obj2.get("question");
				String question_text = null;
				String sparql = null;
				for(int j=0; j<obj2_questions.size(); j++){
					
					JSONObject obj2_temp = (JSONObject)obj2_questions.get(j);
					
					if(obj2_temp.get("language").toString().equals("en")){
//						System.out.println(obj2_temp.get("string"));
						question_text = obj2_temp.get("string").toString();
					}
				}
				
				JSONObject obj2_sparql = (JSONObject) obj2.get("query");
				try{
					sparql  = obj2_sparql.get("sparql").toString();
					question_sparql.add(new String[]  {question_text,sparql});
				}
				catch(Exception e){
					question_sparql.add(new String[]  {question_text,""});
				}
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question_sparql;
	}
	
	public static void main (String are[]){
		ArrayList<String[]> qaldTuple = parseQald6("src/main/resources/qald-6-train-multilingual.json");
	}
}