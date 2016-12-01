package phrase;



import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import question.questionAnnotation;
import token.token;
import utils.spotlight;

public class phraseOrch {
	/*
	 * 
	 * This class contains rules to merge tokens into phrases and also access definers for phrases. 
	 * 
	 * */
	private ArrayList<phrase> phraseList= new ArrayList<phrase>();
	
	
	public ArrayList<phrase> startPhraseMerger(questionAnnotation questionAnnotation){
		
		spotLightMerger(questionAnnotation);
		nerMerger(questionAnnotation);
		NNMerger(questionAnnotation);
		addRemainingPhrase(questionAnnotation);
		phraseList = AllignMetaPhrase(questionAnnotation, phraseList);
//		printMetaPhrase();
//		conceptFormer(questionAnnotation);
		return phraseList;
	}
	
	public void nerMerger(questionAnnotation questionAnnotation) {
		Pattern nerTags = Pattern.compile("LOCATION|PERSON|ORGANIZATION|MISC");

		
		
		for (int i=0 ; i< questionAnnotation.gettokenlist().size();i++){
			
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
				phraseList.add(ph);
			}
		}
	}
	
	public void spotLightMerger(questionAnnotation questionAnnotation){
		//pass it through DbPedia spotlight
				ArrayList<String> labelList = spotlightAnnotation(questionAnnotation.getpreProcessingQuestion());
				
				
				ArrayList<token> tokenlist = questionAnnotation.gettokenlist();
				
				
				

				for (String label : labelList){
//					//splitting token by white space
//					String[] splited = label.split("\\s+");

					
					
					ArrayList<token> phrase_token = new ArrayList<token>();
					
					phrase ph = new phrase();
					
					for (token word : tokenlist){
						if (label.contains(word.getValue()) && !word.isPartOfPhrase()){
							phrase_token.add(word);
							word.setIsPartOfPhrase(true);
						}
					}
					if (!phrase_token.isEmpty()) {
						ph.setPhraseToken(phrase_token);
						
						ph.setPosTag("SP"); //spotlight token annotation --> SP
						phraseList.add(ph);
					}
					
				}
	}
	
	public void NNMerger(questionAnnotation questionAnnotation){
		// NN, NN(,p,s,ps) -> NN(,p,s,ps)
				// VBG, NN(,p,s,ps) -> NN(,p,s,ps)
		// CD, NN(,p,s,ps) -> NN(,p,s,ps)
		Pattern nounTags = Pattern.compile("NN|NNS|NNP|NNPS");
		Pattern mergerTags = Pattern.compile("NN|VBG|CD");
		
		for (int i=0 ; i< questionAnnotation.gettokenlist().size();i++){

			
			if(mergerTags.matcher(questionAnnotation.gettokenlist().get(i).getPosTag()).find() && !questionAnnotation.gettokenlist().get(i).isPartOfPhrase()){
				ArrayList<token> phrase_token = new ArrayList<token>();
				phrase_token.add(questionAnnotation.gettokenlist().get(i));
				questionAnnotation.gettokenlist().get(i).setIsPartOfPhrase(true);
				while(nounTags.matcher(questionAnnotation.gettokenlist().get(i+1).getPosTag()).find()){
					if (questionAnnotation.gettokenlist().get(i+1).isPartOfPhrase() == false){
						phrase_token.add(questionAnnotation.gettokenlist().get(i+1));
						questionAnnotation.gettokenlist().get(i+1).setIsPartOfPhrase(true);
						i = i + 1;
					}
				}
				phrase ph = new phrase();
				ph.setPhraseToken(phrase_token);
				ph.setPosTag("NN");
				phraseList.add(ph);
			}
		}
		
		
	}
	
	
	public void quantifierHandler(){
		//TODO: Add appropriate rules.
	}
	public void addRemainingPhrase(questionAnnotation questionAnnotation){
		for (token word: questionAnnotation.gettokenlist()){
			if (!word.isPartOfPhrase()){
				phrase ph = new phrase();
				ArrayList<token> temp = new ArrayList<token>();
				temp.add(word);
				ph.setPhraseToken(temp);
				ph.setPosTag(word.getPosTag());
				word.setIsPartOfPhrase(true);
				phraseList.add(ph);
			}
		}
	}
	
	
	public void conceptFormer(questionAnnotation questionAnnotation){
		/*
		 * JJ
		 * JJS
		 * JJ
		 * CD - NN,SP,NER -->One concept
		 * */
		ArrayList<phrase> concept = new ArrayList<phrase>();
		Pattern NounPhrase = Pattern.compile("NN|NER|SP|NNS") ;
//		TODO: Complete the patterns list
		Pattern AdjList = Pattern.compile("JJ|JJS|ADJ|RBS|SP");
		ArrayList<ArrayList<phrase>> conceptList = new ArrayList<ArrayList<phrase>>();
		
		
		for (int i=0; i<phraseList.size();i++){
			if (AdjList.matcher(phraseList.get(i).getPosTag()).find() && !phraseList.get(i).getIsPartOf()){
				
				
				ArrayList<phrase> phList = new ArrayList<phrase>();
				phList.add(phraseList.get(i));
				phraseList.get(i).setIsPartOf(true);
				
				while(AdjList.matcher(phraseList.get(i+1).getPosTag()).find() || NounPhrase.matcher(phraseList.get(i+1).getPosTag()).find() ){
					
					if (!phraseList.get(i+1).getIsPartOf()) {
						phList.add(phraseList.get(i+1));
						phraseList.get(i+1).setIsPartOf(true);
						i = i+1;
					}
					else{
						break;
					}
				}
				conceptList.add(phList);
			}
		}
		
		for (int i=0; i<phraseList.size();i++){
			if (NounPhrase.matcher(phraseList.get(i).getPosTag()).find()){
				
				
				if(!phraseList.get(i).getIsPartOf()){
				
					
					
					ArrayList<phrase> phList = new ArrayList<phrase>();
					phList.add(phraseList.get(i));
					phraseList.get(i).setIsPartOf(true);
					conceptList.add(phList);
				
				
				
				}
			}
		}
		
		
		for(int i=0; i<conceptList.size(); i++){
			System.out.print("**");
			for(phrase ph: conceptList.get(i)){
				
				for (token tk: ph.getPhraseToken()){
					System.out.print(tk.getValue() + " ");
				}
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
	
	public void printMetaPhrase(){
		for(phrase ph : phraseList){
			System.out.println("");
			for (token tk : ph.getPhraseToken()){
				System.out.print(tk.getPosTag()+ " "+tk.getValue() + " ");
			}
		}
	}
	
	public ArrayList<phrase> AllignMetaPhrase(questionAnnotation questionAnnotation,ArrayList<phrase> metaPhrases ){
		/*
		 * This method returns the arraylist in the smae order as that of token list
		 * TODO: Check for logical and syntactical error.
		 * 
		 * */
		 ArrayList<phrase> AllignMetaPhrase= new ArrayList<phrase>();
		 ArrayList<phrase> tempPhrase= new ArrayList<phrase>();
		 for (phrase ph: metaPhrases){
			 tempPhrase.add(ph);
		 }
		 for(token word : questionAnnotation.gettokenlist()){
			//find the token in the list 
			//find the appropriate phrase for that token 
			//push that phrase into the array
			for (phrase ph: metaPhrases){
				for (token tk: ph.getPhraseToken()){
					if (tk.getValue() == word.getValue()){
						if (tempPhrase.contains(ph)){
							AllignMetaPhrase.add(ph);
							tempPhrase.remove(ph);
							break;
						}
					}
				}
			}
			
		}
		 return AllignMetaPhrase;
	}
}
