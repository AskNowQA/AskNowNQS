package annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import phrase.phrase;
import question.questionAnnotation;
import token.token;
import utils.word2vec;
import utils.wordNet;

public class relationAnnotation {
	
	/*
	 * Handels relation annotation. 
	 * */
	
	public static void relAnnotation(ArrayList<phrase> phraseList, questionAnnotation ques_annotation) throws Exception{
		System.out.println("at relation annotation");
		String[] stopWord = {"Who","What","Who"};
		for (phrase ph : phraseList){
			if (ph.getUri() != null){
				//Retrives list of relation coming in and going out of the annotated entity 
				ArrayList<String[]> listOfPair	= OneHopRelationQuery.getPredicateList(ph.getUri());
				
				ArrayList<String[]> listOfPairCopy = new ArrayList<String[]>() ;
				
				for(String[] lp : listOfPair){
					listOfPairCopy.add(new String[] {lp[0],lp[1]});
//					System.out.println("lp0 is " + lp[0] + "lp1 is " + lp[1]);
				}
				
				//Expand this list using wordnet 
				for (String[] lp : listOfPairCopy){
					//send each of the property label to WordNet.
					for(String word : wordNet.getSynonyms(lp[1])){
						listOfPair.add(new String[] {lp[0],word});
					}
				}
				
				ArrayList<String[]> listOfPairScore = new ArrayList<String[]>();
				
//				for (String[] lp : listOfPair){
//					for ( token tk : ques_annotation.gettokenlist())
//					listOfPairScore.add(new String [] {lp[0],lp[1],tk.getValue(),String.valueOf(word2vec.sendToVec(lp[1], tk.getValue()))});
//				}
				
				
//TODO: Simplify this for loop 				
				for (String[] lp : listOfPair){
					for (phrase phr : phraseList){
						if (phr.getUri() == null ){
							for (token tk : phr.getPhraseToken()){
								if (!Arrays.asList(stopWord).contains(tk.getValue())) {
									String score = String.valueOf(word2vec.sendToVec(lp[1], tk.getValue()));
									if (Float.valueOf(score) > -1.0){		
									listOfPairScore.add(new String [] {lp[0],lp[1],tk.getValue(),score});}
								}
							}
						}
					}
				}
				
						
				//This method sorts the ListOfPairScore arrayList by score value of the word2vec.
				Collections.sort(listOfPairScore,new Comparator<String[]>() {
		            public int compare(String[] strings, String[] otherStrings) {
		                return Float.valueOf(strings[3]).compareTo(Float.valueOf(otherStrings[3]));
		            }
		        });
				
				for(String[] lp : listOfPairScore){
					System.out.println( lp[1]+ " :: " + lp[2] + " :: " + lp[3]);
				}				
			}
		}
	}
}
