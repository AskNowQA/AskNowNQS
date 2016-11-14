package phrase;



import java.util.ArrayList;
import java.util.regex.Pattern;

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
		
//		spotLightMerger(questionAnnotation);
		nerMerger(questionAnnotation);
		System.out.println("emm some one there ?");
		printMetaPhrase();
	}
	
	public void nerMerger(questionAnnotation questionAnnotation) {
		Pattern nerTags = Pattern.compile("LOCATION|PERSON|ORGANIZATION|MISC");

		
		
		for (int i=0 ; i< questionAnnotation.gettokenlist().size();i++){
			System.out.println(questionAnnotation.gettokenlist().get(i).getNerTag());
			
			if(nerTags.matcher(questionAnnotation.gettokenlist().get(i).getNerTag()).find() && !questionAnnotation.gettokenlist().get(i).isPartOfPhrase()){
				ArrayList<token> phrase_token = new ArrayList<token>();
				phrase_token.add(questionAnnotation.gettokenlist().get(i));
				questionAnnotation.gettokenlist().get(i).setIsPartOfPhrase(true);
				while(questionAnnotation.gettokenlist().get(i+1).getNerTag() == questionAnnotation.gettokenlist().get(i).getNerTag()){
					if (questionAnnotation.gettokenlist().get(i+1).isPartOfPhrase() == false){
						phrase_token.add(questionAnnotation.gettokenlist().get(i+1));
						questionAnnotation.gettokenlist().get(i+1).setIsPartOfPhrase(true);
						i = i + 1;
					}
				}
				phrase ph = new phrase();
				ph.setPhraseToken(phrase_token);
				ph.setPosTag("NER");
				metaPhrase.add(ph);
			}
		}
	}
	
	
	public void spotLightMerger(questionAnnotation questionAnnotation){
		//pass it through DbPedia spotlight
				ArrayList<String> labelList = spotlightAnnotation(questionAnnotation.getpreProcessingQuestion());
				
				
				ArrayList<token> tokenlist = questionAnnotation.gettokenlist();
				
				
				
				System.out.println(questionAnnotation.gettokenlist());
				for (String label : labelList){
//					//splitting token by white space
//					String[] splited = label.split("\\s+");
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
	
	public void printMetaPhrase(){
		for(phrase ph : metaPhrase){
			System.out.println("phrase are:");
			for (token tk : ph.getPhraseToken()){
				System.out.println(tk.getValue());
			}
		}
	}
}
