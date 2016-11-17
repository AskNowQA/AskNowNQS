package phraseMerger;

import java.util.ArrayList;
import java.util.regex.Pattern;

import phrase.phrase;
import question.questionAnnotation;
import tokenAnnotation.token;

public class concept {

	public ArrayList<ArrayList<phrase>> conceptFormer(questionAnnotation questionAnnotation, ArrayList<phrase> metaPhrase){
		/*
		 * JJ
		 * JJS
		 * JJ
		 * CD - NN,SP,NER -->One concept
		 * */
		ArrayList<phrase> concept = new ArrayList<phrase>();
		Pattern NounPhrase = Pattern.compile("NN|NER|SP|NNS") ;
//		TODO: Complete the patterns list
		Pattern AdjList = Pattern.compile("JJ|JJS|ADJ|RBS|SP");
		ArrayList<ArrayList<phrase>> conceptList = new ArrayList<ArrayList<phrase>>();
		
		
		for (int i=0; i<metaPhrase.size();i++){
			if (AdjList.matcher(metaPhrase.get(i).getPosTag()).find() && !metaPhrase.get(i).getIsPartOf()){
				
				
				ArrayList<phrase> phList = new ArrayList<phrase>();
				phList.add(metaPhrase.get(i));
				metaPhrase.get(i).setIsPartOf(true);
				
				while(AdjList.matcher(metaPhrase.get(i+1).getPosTag()).find() || NounPhrase.matcher(metaPhrase.get(i+1).getPosTag()).find() ){
					
					if (!metaPhrase.get(i+1).getIsPartOf()) {
						phList.add(metaPhrase.get(i+1));
						metaPhrase.get(i+1).setIsPartOf(true);
						i = i+1;
					}
					else{
						break;
					}
				}
				conceptList.add(phList);
			}
		}
		
		for (int i=0; i<metaPhrase.size();i++){
			if (NounPhrase.matcher(metaPhrase.get(i).getPosTag()).find()){
				
				
				if(!metaPhrase.get(i).getIsPartOf()){
				
					
					
					ArrayList<phrase> phList = new ArrayList<phrase>();
					phList.add(metaPhrase.get(i));
					metaPhrase.get(i).setIsPartOf(true);
					conceptList.add(phList);
				
				
				
				}
			}
		}
		
		return conceptList;
	}
	
	
	
}
