package annotation;

import java.util.ArrayList;

import phrase.phrase;
import question.questionAnnotation;
import token.token;
import utils.word2vec;

public class relationAnnotation {
	
	/*
	 * Handels relation annotation. 
	 * */
	
	public static void relAnnotation(ArrayList<phrase> phraseList, questionAnnotation ques_annotation) throws Exception{
		for (phrase ph : phraseList){
			if (ph.getUri() != null){
				//Retrives list of relation coming in and going out of the annotated entity 
				ArrayList<String[]> listOfPair	= OneHopRelationQuery.getPredicateList(ph.getUri());
				//Expand this list using wordnet 
				
				ArrayList<String[]> listOfPairScore = new ArrayList<String[]>();
				
				for (String[] lp : listOfPair){
					for ( token tk : ques_annotation.gettokenlist())
					listOfPairScore.add(new String [] {lp[0],lp[1],tk.getValue(),String.valueOf(word2vec.sendToVec(lp[0], tk.getValue()))});
				}
			}
		}
	}
}
