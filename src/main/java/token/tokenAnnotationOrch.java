package token;

import java.util.ArrayList;

import nlp.nlp;

public class tokenAnnotationOrch {

	/*
	 * Perfomrss NER, pos, tokenizing and lemmatizing of the word. Each individual word of the question 
	 * is stored in token and also stores other meta data in the token class. All tokens are stored in the arraylist
	 * and is returend. This arraylist is then storedd in questionAnnotation class. 
	 * 
	 * */
	public ArrayList<token> getWordTokenArrayList(String question){
		//returns array list of annotated word tokens object. 
		nlp nlp_pipeline = new nlp();
		//TODO: OPtimize the below code
		ArrayList<String> NERTags = nlp_pipeline.getNERTags(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> posTags = nlp_pipeline.getPosTags(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> tokens = nlp_pipeline.getTokens(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> lemmas = nlp_pipeline.getLemmas(nlp_pipeline.getAnnotatedToken(question));
		//Array List of annotated word token  
		ArrayList<token> tokenlist = new ArrayList<token>();
		
		for (int i=0 ; i<tokens.size();i++){
			//TODO: Change the way indexing happens
			token tokener = new token(i, tokens.get(i), posTags.get(i), NERTags.get(i), lemmas.get(i));
			
			tokenlist.add(tokener);
			
			tokener = null;
			
		}
		
		
		return tokenlist;
	}
	
}
