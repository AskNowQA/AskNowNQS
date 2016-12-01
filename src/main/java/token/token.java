package token;

public class token {
	//TODO: extend to other functionality
	private  String value = null;
	private  String posTag = null;
	private  String nerStart = null;
	private  String nerEnd = null;
	private  String nerTag = null;
	private  String lemma = null;
	private  Integer index = null;
	private  Boolean isPartOfPhrase = null;
	private  String phraseID = null; 		//TODO: Use ENUM later here.
	
	public token(Integer index, String value, String posTag, String nerTag, String lemma){
		this.index = index;
		this.value = value;
		this.posTag = posTag;
		this.nerTag = nerTag;
		this.lemma = lemma;
		this.isPartOfPhrase = false;
	}

	public  String getValue() {
		return this.value;
	}

	public  String getPosTag() {
		return posTag;
	}

	public  String getNerStart() {
		return nerStart;
	}

	public  String getNerEnd() {
		return nerEnd;
	}

	public  String getNerTag() {
		return nerTag;
	}

	public  String getLemma() {
		return lemma;
	}

	public  Integer getIndex() {
		return index;
	}
	
	public  Boolean isPartOfPhrase(){
		return isPartOfPhrase;
	}
	
	public  void setIsPartOfPhrase(Boolean value){
		isPartOfPhrase = value;
	}
	//TODO: implement all getters and setters for the above attributes
}
