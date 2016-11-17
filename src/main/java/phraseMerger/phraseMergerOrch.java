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

	
	
	public ArrayList<ArrayList<phrase>> startPhaseMergerOrch(questionAnnotation questionAnnotation, ArrayList<phrase> metaPhrase){
		//The actual logic throught which everything is orchestrated. 
		concept concept = new concept();
		ArrayList<ArrayList<phrase>> conceptList = concept.conceptFormer(questionAnnotation, metaPhrase);
		return conceptList;
	}
	
	public void printConceptList(ArrayList<ArrayList<phrase>> conceptList){
		System.out.println("");
		for(int i=0; i<conceptList.size(); i++){
			System.out.print("**");
			for(phrase ph: conceptList.get(i)){
				
				for (token tk: ph.getPhraseToken()){
					System.out.print(tk.getValue() + " ");
				}
			}
		}
	}
}
