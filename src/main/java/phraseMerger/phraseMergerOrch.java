package phraseMerger;

import java.util.ArrayList;
import java.util.regex.Pattern;

import phrase.phrase;
import question.questionAnnotation;
import tokenAnnotation.token;

public class phraseMergerOrch {
/*
 * 
 * This orchestrates the phrase Merger. Concepts are made up of phrases. This represents entites and relations in the AskNow NQS. 
 * There are three types of merger. 
 * First one is Concept merger, the How merger and finally relation merger. 
 * 
 * */

	
	
	public ArrayList<ArrayList<phrase>> startPhraseMergerOrch(questionAnnotation questionAnnotation, ArrayList<phrase> metaPhrase){
		//The actual logic throught which everything is orchestrated. 
		concept concept = new concept();
		wh wh = new wh();
		relation rel = new relation();
		ArrayList<ArrayList<phrase>> conceptList = concept.conceptFormer(questionAnnotation, metaPhrase);
		ArrayList<ArrayList<phrase>> whList = wh.whFormer(questionAnnotation, metaPhrase);
		ArrayList<ArrayList<phrase>> relList = rel.relFormer(questionAnnotation, metaPhrase);
		System.out.println("the wh type is ");
		printConceptList(whList);
//		System.out.println("");
		System.out.println("the concept type is ");
//		System.out.println("");
		printConceptList(conceptList);
//		System.out.println("");
		System.out.println("the rel type is ");
//		System.out.println("");
		printConceptList(relList);
		return whList;
	}
	
	public void printConceptList(ArrayList<ArrayList<phrase>> conceptList){
		for(int i=0; i<conceptList.size(); i++){
			System.out.print("**");
			for(phrase ph: conceptList.get(i)){
				
				for (token tk: ph.getPhraseToken()){
					System.out.print(tk.getValue() + " ");
				}
			}
		}
		System.out.println("");
	}
}
