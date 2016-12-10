package queryConstructor;

import java.util.ArrayList;

import annotation.relationAnnotationToken;
import phrase.phrase;
import question.questionAnnotation;
import token.token;

public class listQuery {

	/*
	 * List query handels templates of the form SELECT DISTINCT var0 WHERE{}
	 * */
	
	
	public static void listQuerylogic(questionAnnotation ques_annotation){
		
		ArrayList<phrase> annotatedPhraseList  = listQuery.getAnnotatedPhraseList(ques_annotation);
		
		
		if (annotatedPhraseList.size() == 1){
			//Only two types of sparql can be present here
			//extracting the first candidate 
			relationAnnotationToken tk = annotatedPhraseList.get(0).getListOfProbableRelation().get(0);
			// checking if the token is a prt of incoming or outgoing property
			if (tk.isIncomingProperty()){
				//part of incoming property 
				// ?x tk ph
				// tk.getUri() --> for property uri
				//	annotatedPhraseList.get(0).getUri() -- entity uri 
				
			}
			else{
				//part of outgoing property 
				// ?x tk ph
			}
		}
	}
	
	public static ArrayList<phrase> getAnnotatedPhraseList(questionAnnotation ques_annotation){
		
		//This function returns the annotated phrase. The objective is to identify the number of 
		//triple patterns like below 
		// {a,b,?c} or {?a,b,c}
		
		ArrayList<phrase> annotatedPhraseList = new ArrayList<phrase>();
		for(phrase ph : ques_annotation.getPhraseList()){
			if(ph.getUri() != null && ph.getRelToken() != null){
				annotatedPhraseList.add(ph)
			}
		}
		
		return annotatedPhraseList;
	}
}
