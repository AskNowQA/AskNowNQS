package phrase;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import annotation.relationAnnotationToken;
import token.token;

public class phrase {
/*
 * This class has methods to store information about phrase. That is the tokens which forms phrase
 *  and also other meta information needed for the phrase.
 * */
	
	private ArrayList<token> phraseToken = new ArrayList<token>();
	private String posTag = null;
	private String uri = null;
	private String phraseString = null;
	private Boolean isPartOf = false;
	private ArrayList<String []> incomingProperty = new ArrayList<String []>();
	private ArrayList<String []> outgoingProperty = new ArrayList<String []>();
	private ArrayList<String []> expandIncomingProperty = new ArrayList<String []>();
	private ArrayList<String []> expandOutgoingProperty = new ArrayList<String []>();
	private ArrayList<token> RelToken = new ArrayList<token>();
	private ArrayList<relationAnnotationToken> listOfProbableRelation = new ArrayList<relationAnnotationToken>();
	
	
	public ArrayList<relationAnnotationToken> getListOfProbableRelation() {
		return listOfProbableRelation;
	}


	public void setListOfProbableRelation(ArrayList<relationAnnotationToken> listOfProbableRelation) {
		this.listOfProbableRelation = listOfProbableRelation;
	}


	public ArrayList<String[]> getIncomingProperty() {
		return incomingProperty;
	}

	
	public void setIncomingProperty(ArrayList<String[]> incomingProperty) {
		this.incomingProperty = incomingProperty;
	}

	public ArrayList<String[]> getOutgoingProperty() {
		return outgoingProperty;
	}

	public void setOutgoingProperty(ArrayList<String[]> outgoingProperty) {
		this.outgoingProperty = outgoingProperty;
	}

	public ArrayList<String[]> getExpandIncomingProperty() {
		return expandIncomingProperty;
	}

	public void setExpandIncomingProperty(ArrayList<String[]> expandIncomingProperty) {
		this.expandIncomingProperty = expandIncomingProperty;
	}

	public ArrayList<String[]> getExpandOutgoingProperty() {
		return expandOutgoingProperty;
	}

	public void setExpandOutgoingProperty(ArrayList<String[]> expandOutgoingProperty) {
		this.expandOutgoingProperty = expandOutgoingProperty;
	}

	public ArrayList<token> getRelToken() {
		return RelToken;
	}

	public void setRelToken(ArrayList<token> relToken) {
		RelToken = relToken;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ArrayList<token> getPhraseToken() {
		return phraseToken;
	}
	
	public void setPhraseToken(ArrayList<token> phraseToken) {
		this.phraseToken = phraseToken;
		setPhraseString();
	}
	
	public String getPosTag() {
		return posTag;
	}
	
	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}
	
	public void setIsPartOf(Boolean value){
		this.isPartOf = value;
	}
	
	public Boolean getIsPartOf(){
		return isPartOf;
	}
	
	public void setPhraseString(){
		ArrayList<String> tokenLists = new ArrayList<String>();
		for(token tk: getPhraseToken()){
			tokenLists.add(tk.getValue());
//			System.out.println("*"+tk.getValue());
		}
		
		phraseString = StringUtils.join(tokenLists, " ");
	}
	
	public String getphraseString(){
		return phraseString;
	}
	
	
}
