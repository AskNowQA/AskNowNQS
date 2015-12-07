package org.aksw.asknow.nqs;

import java.util.ArrayList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class QueryBuilder {
	/**Query String to work on */
	private String queryString;
	/**ArrayList of QueryTokens containing words of queryString and their respective POStags */
	private ArrayList<QueryToken> tokens;
	@Getter private String characterizedString;
	private QueryTokenizer tokenizer;
	private TokenMerger tm = new TokenMerger();	
	@Getter private CharacterizationTemplate ct;
	
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
		log.debug("Initial Tokens:", tokens.toString());
		log.debug("tokenized", tokens.toString());

		QuerySyntaxHandler qsh = new QuerySyntaxHandler();
		if(tokens.size()>0 && !tokens.get(0).isWP()){			
			tokens = qsh.bringWPinFront(tokens);
			queryString = QueryModuleLibrary.getStringFromTokens(tokens);
		}
		log.debug("after systax", tokens.toString());
		
		
		/*
		 * Handle AuxRelations in the tokens
		 * */
		tokens = (new AuxRelations(queryString,tokens)).tokens;
		log.debug("after aux", tokens.toString());
		
		/*
		 * Clubbing Tags
		 * */
		tm.setTokens(tokens,queryString);
		tm.startMerger();
		tokens = tm.getTokens();
		
		log.debug("after merger", tokens.toString());
		
		tokens = qsh.handleApostrophe(tokens);
		log.debug("after apos", tokens.toString());
		
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

}