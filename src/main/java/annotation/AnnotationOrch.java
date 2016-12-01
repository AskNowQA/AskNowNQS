package annotation;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import phrase.phrase;
import question.questionAnnotation;
import token.token;
import utils.spotlight;

public class AnnotationOrch {
	
	/*
	 * This class orchestrates the annotation. It first calls spotlight (entity linker) 
	 * and then moves to relation annotation (one-hop strategy and patty and other )
	 * 
	 * 
	 * */
	
	public void startAnnotationOrch(ArrayList<phrase> phraseList, questionAnnotation ques_annotation){
			//call spotlight annotation. 
			//for each phrase in the list pass it through the spotlight and check for its annotation.
		
		entityAnnotation.Annotation(phraseList);
		
			
		
	}
}
