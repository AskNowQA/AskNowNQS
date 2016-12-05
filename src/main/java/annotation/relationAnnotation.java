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
	
	public static ArrayList<ArrayList<String[]>> relAnnotation(ArrayList<phrase> phraseList, questionAnnotation ques_annotation) throws Exception{
		System.out.println("at relation annotation");
		String[] stopWord = {"Who","What","Who"};
		int counter = 5;
		ArrayList<ArrayList<String[]>> finalRelList = new ArrayList<ArrayList<String[]>>(); 
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
					for(String word : wordNet.getSynonyms(lp[1])){
						expandIncomingProperty.add(new String[] {lp[0],word});
					}
					expandIncomingProperty.add(lp);
				}
				
				
				for (String[] lp : ph.getOutgoingProperty()){
					//send each of the property label to WordNet.
					for(String word : wordNet.getSynonyms(lp[1])){
						expandOutgoingProperty.add(new String[] {lp[0],word});
					}
					expandOutgoingProperty.add(lp);
				}
				
				ph.setExpandIncomingProperty(expandIncomingProperty);
				ph.setExpandOutgoingProperty(expandOutgoingProperty);
				
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
				
//				for(String[] lp : listOfPairScore){
//					System.out.println( lp[1]+ " :: " + lp[2] + " :: " + lp[3]);
//				}
				Collections.reverse(listOfPairScore);
				
				ArrayList<String[]> sublistOfPairScore = new ArrayList<String[]>();
				
				
//				sublistOfPairScore = (ArrayList<String[]>) listOfPairScore.subList(0, Math.min(counter,listOfPairScore.size()));
				for(int i = 0 ; i<counter; i++){
					if (i >= listOfPairScore.size()){
						break;
					}
					
					sublistOfPairScore.add(listOfPairScore.get(i));
				}
				finalRelList.add(sublistOfPairScore);
			}	
		}
		return finalRelList;
	}
}
