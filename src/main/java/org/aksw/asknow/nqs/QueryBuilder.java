package org.aksw.asknow.nqs;

import java.util.ArrayList;

public class QueryBuilder {
	/**Query String to work on */
	private String queryString;
	/**ArrayList of QueryTokens containing words of queryString and their respective POStags */
	private ArrayList<QueryToken> tokens;
	private String characterizedString;
	private QueryTokenizer tokenizer;
	private TokenMerger tm = new TokenMerger();
	private CharacterizationTemplate ct;
	
	public QueryBuilder(){
		tokenizer = new QueryTokenizer();
	}
	
	public QueryBuilder(String query){
		queryString = query;
		tokenizer = new QueryTokenizer(queryString);
		buildQuery();
	}
	
	public void setQuery(String query){
		queryString = query;
		tokenizer.createTokenList(queryString);
	}

	public void buildQuery() {
		 // Get Token List. Each Token contains a word(String) and it's corresponding POS Tag(String)
		tokens = tokenizer.getTokenList();
		//log.debug("Initial Tokens:", tokens.toString());
		//log.debug("tokenized", tokens.toString());

		QuerySyntaxHandeler qsh = new QuerySyntaxHandeler();
		if(tokens.size()>0 && !tokens.get(0).isWP()){			
			tokens = qsh.bringWPinFront(tokens);
			queryString = QueryModuleLibrary.getStringFromTokens(tokens);
		}
		//log.debug("after systax", tokens.toString());
		
		
		/*
		 * Handle AuxRelations in the tokens
		 * */
		tokens = (new AuxRelations(queryString,tokens)).tokens;
		//log.debug("after aux", tokens.toString());
		
		/*
		 * Clubbing Tags
		 * */
		tm.setTokens(tokens,queryString);
		tm.startMerger();
		tokens = tm.getTokens();
		
		//log.debug("after merger", tokens.toString());
		
		tokens = qsh.handleApostrophe(tokens);
		//log.debug("after apos", tokens.toString());
		
		/*
		 * Fit the query into the Characterization Template
		 * */
		ct = new CharacterizationTemplate(tokens); 
		ct.createTemplate();
		characterizedString = ct.getCharacterizedString();
	}

	public String getTaggedString(){
		return tokens.toString();
	}
	
	public void close(){
		tokens.clear();
	}

	public String getCharacterizedString(){
		return characterizedString;
	}
	
	public String getDesire(){
		if(ct!=null)
			return ct.getDesire();
		else 
			return null;
	}

	public String getInputs() {
		if(ct!=null)
			return ct.getInputs();
		else 
			return null;
	}

	public String getWH() {
		
		if(ct!=null)
			return ct.getWH();
		else 
			return null;
	}

	public boolean isCaracterized() {
		if(ct!=null)
			return ct.isCaracterized();
		else 
			return false;
	}
}
