package org.aksw.asknow.nqs;
import edu.stanford.nlp.ling.TaggedWord;

public class QueryToken extends TaggedWord {

	private String tokenString;
	private String tokenTag;
	private boolean isRole = false, isConcept = false,isRelation = false;
	
	public QueryToken(String string, String tag){
		tokenString = string.trim();
		tokenTag = tag.trim(); 
	}
	
	public String getString(){
		return tokenString;
	}
	
	public String getTag(){
		return tokenTag;
	}
	
	public void setString(String string){
		tokenString = string.trim();
	}
	
	@Override public void setTag(String tag){
		tokenTag = tag.trim();
	}
	
	public boolean tagEquals(String tag){
		return tokenTag.equalsIgnoreCase(tag.trim());
	}
	
	@Override public String toString(){
		return tokenString+"/"+tokenTag;
	}
	
	public String toStringWithoutTags(){
		return tokenString;
	}
	
	
	public boolean isAdjVariant(){
		if(tokenTag.equalsIgnoreCase("JJ") || tokenTag.equalsIgnoreCase("JJR") 
				|| tokenTag.equalsIgnoreCase("JJS"))
			return true;
		else
			return false;
	}
	
	public boolean isNounVariant(){
		if(tokenTag.equalsIgnoreCase("NN") || tokenTag.equalsIgnoreCase("NNS") 
				|| tokenTag.equalsIgnoreCase("NNP") || tokenTag.equalsIgnoreCase("NNPS")
				|| tokenTag.equalsIgnoreCase("NNP-NER"))
			return true;
		else
			return false;
	}
	
	public static boolean isNounVariant(String value){
		if(value.equalsIgnoreCase("NN") || value.equalsIgnoreCase("NNS") 
				|| value.equalsIgnoreCase("NNP") || value.equalsIgnoreCase("NNPS")
				|| value.equalsIgnoreCase("NNP-NER"))
			return true;
		else
			return false;
	}
	
	public static boolean isNERVariant(String value){
		if(value.equalsIgnoreCase("NNP-NER"))
			return true;
		return false;
	}
	
	public boolean isVerbVariant(){
		if(tokenTag.equalsIgnoreCase("VB") || tokenTag.equalsIgnoreCase("VBD") 
				|| tokenTag.equalsIgnoreCase("VBG") || tokenTag.equalsIgnoreCase("VBN")
				|| tokenTag.equalsIgnoreCase("VBP") || tokenTag.equalsIgnoreCase("VBZ"))
			return true;
		else
			return false;
	}
	
/*	public void setRoleToken(){
		isRole = true;
	}
	
	public void setRelationToken(){
		isRelation = true;
	}

	public void setConceptToken(){
		isConcept = true;
	}
	
	public boolean isRole(){
		return isRole;
	}
	
	public boolean isRelation(){
		return isRelation;
	}
	
	public boolean isConcept(){
		return isConcept;
	}*/
	
	public boolean isNN()
	{
		return tokenTag.equalsIgnoreCase("NN");
	}
	
	public boolean isNNP()
	{
		return tokenTag.equalsIgnoreCase("NNP") || tokenTag.equalsIgnoreCase("NNP-NER");
	}
	
	public boolean isNNS()
	{
		return tokenTag.equalsIgnoreCase("NNS");
	}
	
	public boolean isNNPS(){
		return tokenTag.equalsIgnoreCase("NNPS");
	}
	
	public boolean isRB(){
		return tokenTag.equalsIgnoreCase("RB");
	}
	
	public boolean isDT()
	{
		return tokenTag.equalsIgnoreCase("DT");
	}
	
	public boolean isJJ()
	{
		return tokenTag.equalsIgnoreCase("JJ");
	}
	
	public boolean isVBN()
	{
		return tokenTag.equalsIgnoreCase("VBN");
	}
	
	public boolean isVBG(){
		return tokenTag.equalsIgnoreCase("VBG");
	}
	
	public boolean isCD()
	{
		return tokenTag.equalsIgnoreCase("CD");
	}
	
	public boolean isIN()
	{
		return tokenTag.equalsIgnoreCase("IN");
	}
	
	public boolean isTO(){
		return tokenTag.equalsIgnoreCase("TO");
	}
	
	public boolean isWP()
	{
		return tokenTag.equalsIgnoreCase("WP");
	}
	
	public boolean isREL1(){
		return tokenTag.equalsIgnoreCase("REL1");
	}
	
	public boolean isRoleTagged(){
		return tokenTag.equalsIgnoreCase("ROLE");
	}
	
	public boolean isPRP(){
		return tokenTag.equalsIgnoreCase("PRP") || tokenTag.equalsIgnoreCase("PRP$");
	}

	public boolean isClause() {
		if(tokenString.equalsIgnoreCase("who") || tokenString.equalsIgnoreCase("which") ||
				tokenString.equalsIgnoreCase("when") ||tokenString.equalsIgnoreCase("where") ||
				tokenString.equalsIgnoreCase("whose") ||tokenString.equalsIgnoreCase("whom") ||
				tokenString.equalsIgnoreCase("what"))
			return true;
		else
			return false;
	}

	public boolean isPOS()
	{
		return tokenTag.equalsIgnoreCase("POS");
	}
	
	public boolean isCC()
	{
		return tokenTag.equalsIgnoreCase("CC");
	}

	public boolean isVBD()
	{
		return tokenTag.equalsIgnoreCase("VBD");
	}

	public boolean isVBDGN()
	{
		return this.isVBD() || this.isVBG() || this.isVBN();
	}

	public boolean isNNPNER()
	{
		return tokenTag.equalsIgnoreCase("NNP-NER");	
	}
}