package annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import phrase.phrase;
import question.questionAnnotation;
import token.token;
import utils.word2vec;
import utils.wordNet;

public class relationAnnotation {
	
	/*
	 * Handels relation annotation. 
	 * */
	
	public static ArrayList<ArrayList<relationAnnotationToken>> relAnnotation(ArrayList<phrase> phraseList, questionAnnotation ques_annotation) throws Exception{
		String[] stopWord = {"Who","What","Who","the","an","a","that","them","they","their","those","list","as","when","is", "how", "many", "often", "are", "is", "there", "in", "were","?"};
		//TODO: Add property stopword here.
		String[] propertyStopWord = {"thumbnail","abstract"};
		ArrayList<ArrayList<relationAnnotationToken>> finalRelList = new ArrayList<ArrayList<relationAnnotationToken>>(); 
		
		Integer printRelationNumber = 20;
		for (phrase ph : phraseList){
			if (ph.getUri() != null){
				//Retrives list of relation coming in and going out of the annotated entity 
				ArrayList<ArrayList<String[]>> listOfPair	= OneHopRelationQuery.getPredicateList(ph.getUri());
				ph.setIncomingProperty(listOfPair.get(0));
				ph.setOutgoingProperty(listOfPair.get(1));
				
				
				//Expand this list using wordnet 
				
				ArrayList<String[]> expandIncomingProperty = new ArrayList<String[]>();
				ArrayList<String[]> expandOutgoingProperty = new ArrayList<String[]>();
				
				for (String[] lp : ph.getIncomingProperty()){
					//send each of the property label to WordNet.
					if(!Arrays.asList(propertyStopWord).contains(lp[1]) && !lp[0].contains("property")) {
						for(String word : wordNet.getSynonyms(lp[1])){
							expandIncomingProperty.add(new String[] {lp[0],word});
						}
						expandIncomingProperty.add(lp);
					}
				}
				
				
				for (String[] lp : ph.getOutgoingProperty()){
					//send each of the property label to WordNet.
					
					if(!Arrays.asList(propertyStopWord).contains(lp[1]) && !lp[0].contains("property")){
						for(String word : wordNet.getSynonyms(lp[1])){
							expandOutgoingProperty.add(new String[] {lp[0],word});
						}
						expandOutgoingProperty.add(lp);
					}
				}
				
				ph.setExpandIncomingProperty(expandIncomingProperty);
				ph.setExpandOutgoingProperty(expandOutgoingProperty);
				
				ArrayList<relationAnnotationToken> listOfPairScore = new ArrayList<relationAnnotationToken>();
								//First compare everything with phrases 
								//Then compare everything with token
				
				
				for (String[] lp:expandIncomingProperty){
					for(phrase phr : phraseList){
						if(phr.getUri() == null){
							
							ArrayList<String> tokenList = new ArrayList<String>();
							for(token tk: phr.getPhraseToken()){
								tokenList.add(tk.getValue());
//								System.out.println("*"+tk.getValue());
							}
							
							String joinedString = StringUtils.join(tokenList, " ");
							if(Arrays.asList(stopWord).contains(joinedString.toLowerCase().trim())){
								continue;
								
							}
//							System.out.println(joinedString);
							
							String score = String.valueOf(word2vec.sendToVec(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", ""), joinedString.replaceAll("[^a-zA-Z0-9\\ ]", "")));
//							System.out.println(joinedString+  " " + lp[1] + " " + score);
							if (Float.valueOf(score) > -1.0){
								relationAnnotationToken relToken = new relationAnnotationToken();
								relToken.setScore(Float.valueOf(score));
								relToken.setIncomingProperty(true);
								relToken.setPropertyLabel(lp[1]);
								relToken.setPh(phr);
								relToken.setUri(lp[0]);
								listOfPairScore.add(relToken);
								relToken.setTok(null);
								}
						}
					}
				}
				
				
//				for (String[] lp : expandIncomingProperty){
//					for (phrase phr : phraseList){
//						if (phr.getUri() == null ){
//							for (token tk : phr.getPhraseToken()){
//								if (!Arrays.asList(stopWord).contains(tk.getValue())) {
////									System.out.println(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", "").replaceAll(" ", "_") + " " + tk.getValue().replaceAll("[^a-zA-Z0-9\\ ]", "").replaceAll(" ", "_"));
//									String score = String.valueOf(word2vec.sendToVec(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", ""), tk.getValue().replaceAll("[^a-zA-Z0-9\\ ]", "")));
//									if (Float.valueOf(score) > -1.0){
//										relationAnnotationToken relToken = new relationAnnotationToken();
//										relToken.setScore(Float.valueOf(score));
//										relToken.setIncomingProperty(true);
//										relToken.setPropertyLabel(lp[1]);
//										relToken.setTok(tk);
//										relToken.setUri(lp[0]);
//										listOfPairScore.add(relToken);
//										}
//								}
//							}
//						}
//					}
//				}
				
//				for (String[] lp : expandOutgoingProperty){
//					for (phrase phr : phraseList){
//						if (phr.getUri() == null ){
//							for (token tk : phr.getPhraseToken()){							
//								if (!Arrays.asList(stopWord).contains(tk.getValue())) {
////									System.out.println(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", "") + " " + tk.getValue().replaceAll("[^a-zA-Z0-9\\ ]", ""));
//									String score = String.valueOf(word2vec.sendToVec(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", ""), tk.getValue().replaceAll("[^a-zA-Z0-9\\ ]", "")));
//									if (Float.valueOf(score) > -1.0){
//										relationAnnotationToken relToken = new relationAnnotationToken();
//										relToken.setScore(Float.valueOf(score));
//										relToken.setOutgoingProperty(true);;
//										relToken.setPropertyLabel(lp[1]);
//										relToken.setTok(tk);
//										relToken.setUri(lp[0]);
//										relToken.setPh(phr);
//										listOfPairScore.add(relToken);
//										}
//								}
//							}
//						}
//					}
//				}
				
				for (String[] lp:expandOutgoingProperty){
					for(phrase phr : phraseList){
						
						if(phr.getUri() == null){
							ArrayList<String> tokenList = new ArrayList<String>();
							for(token tk: phr.getPhraseToken()){
//								System.out.println("*"+tk.getValue());
								tokenList.add(tk.getValue());
							}
							
							String joinedString = StringUtils.join(tokenList, " ");
//							System.out.println(joinedString);
							if(Arrays.asList(stopWord).contains(joinedString.toLowerCase().trim())){
								continue;
								
							}
							String score = String.valueOf(word2vec.sendToVec(lp[1].replaceAll("[^a-zA-Z0-9\\ ]", ""), joinedString.replaceAll("[^a-zA-Z0-9\\ ]", "")));
//							System.out.println(joinedString+  " " + lp[1] + " " + score);
							if (Float.valueOf(score) > -1.0){
								relationAnnotationToken relToken = new relationAnnotationToken();
								relToken.setScore(Float.valueOf(score));
								relToken.setOutgoingProperty(true);
								relToken.setPropertyLabel(lp[1]);
								relToken.setPh(phr);
								relToken.setUri(lp[0]);
								listOfPairScore.add(relToken);
								}
						}
					}
				}
				
				
				
				
				//This method sorts the ListOfPairScore arrayList by score value of the word2vec.
				Collections.sort(listOfPairScore,new Comparator<relationAnnotationToken>() {
		            public int compare(relationAnnotationToken strings, relationAnnotationToken otherStrings) {
		                return Float.valueOf(strings.getScore()).compareTo(Float.valueOf(otherStrings.getScore()));
		            }
		        });
				
				Collections.reverse(listOfPairScore);
				
				ArrayList<relationAnnotationToken> sublistOfPairScore = new ArrayList<relationAnnotationToken>();
				
			
				for(int i = 0 ; i<listOfPairScore.size(); i++){
					if (i >= listOfPairScore.size()){
						break;
					}
					
					sublistOfPairScore.add(listOfPairScore.get(i));
				}
				finalRelList.add(sublistOfPairScore);
				ph.setListOfProbableRelation(sublistOfPairScore);
			}	
		}
		return finalRelList;
	}
}
