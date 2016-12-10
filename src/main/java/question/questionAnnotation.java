package question;

import java.util.ArrayList;

import preProcessing.preProcessing;
import nlp.nlp;
import phrase.phrase;
import token.token;

public class questionAnnotation {
	
	/*
	 * 
	 * This class is used as storage unit for question annotation
	 * 
	 * */
	
	//TODO:add more meta data
	//TODO: add annotatedPreProcessingQuestion information. 
	private String originalQuestion = null;
	private String annotatedOriginalQuestion = null;
	private String preProcessingQuestion = null;
	private ArrayList<token> tokenlist = null;
	private ArrayList<phrase> phraseList = null;
	
	public String getPreProcessingQuestion() {
		return preProcessingQuestion;
	}


	public void setPreProcessingQuestion(String preProcessingQuestion) {
		this.preProcessingQuestion = preProcessingQuestion;
	}


	public ArrayList<token> getTokenlist() {
		return tokenlist;
	}


	public void setTokenlist(ArrayList<token> tokenlist) {
		this.tokenlist = tokenlist;
	}


	public ArrayList<phrase> getPhraseList() {
		return phraseList;
	}


	public void setPhraseList(ArrayList<phrase> phraseList) {
		this.phraseList = phraseList;
	}


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
		return preProcessingQuestion;
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
