package annotation;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.apache.log4j.Logger;
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
	
	final static Logger logger = Logger.getLogger(AnnotationOrch.class);
	
	public ArrayList<ArrayList<relationAnnotationToken>> startAnnotationOrch(ArrayList<phrase> phraseList, questionAnnotation ques_annotation){
			//call spotlight annotation. 
			//for each phrase in the list pass it through the spotlight and check for its annotation.
		
		ArrayList<ArrayList<relationAnnotationToken>> finalRelList = new ArrayList<ArrayList<relationAnnotationToken>>();
		entityAnnotation.Annotation(phraseList);
		try {
			finalRelList = relationAnnotation.relAnnotation(phraseList, ques_annotation);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return finalRelList;
	}
}
