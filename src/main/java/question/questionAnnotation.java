package question;

import java.util.ArrayList;

import preProcessing.preProcessing;
import nlp.nlp;
import tokenAnnotation.token;

public class questionAnnotation {
	
	/*
	 * 
	 * This class is used as storage unit for question annotation
	 * 
	 * */
	
	//TODO:add more meta data
	private String originalQuestion = null;
	private String annotatedOriginalQuestion = null;
	private String preProcessingQuestion = null;
	private ArrayList<token> tokenlist = null;
	public questionAnnotation(String question){
		originalQuestion = question;
	}


	public String getOriginalQuestion() {
		return originalQuestion;
	}


	public void setOriginalQuestion(String originalQuestion) {
		this.originalQuestion = originalQuestion;
	}


	public String getAnnotatedOriginalQuestion() {
		return annotatedOriginalQuestion;
	}


	public void setAnnotatedOriginalQuestion(String annotatedOriginalQuestion) {
		this.annotatedOriginalQuestion = annotatedOriginalQuestion;
	}
	
	
	public String getpreProcessingQuestion() {
		return annotatedOriginalQuestion;
	}


	public void setpreProcessingQuestion(String preProcessingQuestion) {
		this.preProcessingQuestion = preProcessingQuestion;
	}
	
	
	public ArrayList<token> gettokenlist() {
		return tokenlist;
	}


	public void settokenlist(ArrayList<token> tokenlist) {
		this.tokenlist = tokenlist;
	}
	
}
