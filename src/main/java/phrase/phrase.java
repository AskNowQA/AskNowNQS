package phrase;

import java.util.ArrayList;

import tokenAnnotation.token;

public class phrase {
/*
 * This class has methods to store information about phrase. That is the tokens which forms phrase
 *  and also other meta information needed for the phrase.
 * */
	
	private ArrayList<token> phraseToken = new ArrayList<token>();
	private String posTag = null;
	private String uri = null;
	private Boolean isPartOf = false;
	
	
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
	
	
	
}
