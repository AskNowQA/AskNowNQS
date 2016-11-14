package phrase;



import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import question.questionAnnotation;
import tokenAnnotation.token;
import utils.spotlight;

public class phraseOrch {
	/*
	 * 
	 * This class contains rules to merge tokens into phrases and also access definers for phrases. 
	 * 
	 * */
	private ArrayList<phrase> metaPhrase= new ArrayList<phrase>();
	
	
	public void phraseMergerOrchestrator(questionAnnotation questionAnnotation){
		
		//pass it through DbPedia spotlight
		ArrayList<String> labelList = spotlightAnnotation(questionAnnotation.getpreProcessingQuestion());
		
		
		ArrayList<token> tokenlist = questionAnnotation.gettokenlist();
		
		
		
		System.out.println(questionAnnotation.gettokenlist());
		for (String label : labelList){
//			//splitting token by white space
//			String[] splited = label.split("\\s+");
			System.out.println("label is " + label);
			
			
			ArrayList<token> phrase_token = new ArrayList<token>();
			
			phrase ph = new phrase();
			
			for (token word : tokenlist){
				if (label.contains(word.getValue())){
					phrase_token.add(word);
					word.setIsPartOfPhrase(true);
				}
			}
			ph.setPhraseToken(phrase_token);
			
			ph.setPosTag("SP"); //spotlight token annotation --> SP
			metaPhrase.add(ph);
		}
		
		for(phrase ph : metaPhrase){
			System.out.println("phrase are:");
			for (token tk : ph.getPhraseToken()){
				System.out.println(tk.getValue());
			}
		}
	}
	
	public ArrayList<String> spotlightAnnotation(String question)
	{
		//calling spotlight
//		spotlight spot = new spotlight()
		JSONArray ques_spotlight_array = spotlight.getDBpLookup(question);
		JSONParser parser = new JSONParser();
		ArrayList<String> label = new ArrayList();
		try {
			Object obj = parser.parse(ques_spotlight_array.toString());
			JSONArray array = (JSONArray)obj;
			for (int i = 0; i < array.size(); i++){
				
				JSONObject obj2 = (JSONObject)array.get(i);	
				label.add(obj2.get("name").toString());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return label;
	}
}
