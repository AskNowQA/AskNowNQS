package phraseMerger;

import java.util.ArrayList;
import java.util.regex.Pattern;

import phrase.phrase;
import question.questionAnnotation;

public class relation {

	
	public ArrayList<ArrayList<phrase>> relFormer(questionAnnotation questionAnnotation, ArrayList<phrase> metaPhrase){
		
		
		ArrayList<ArrayList<phrase>> relList = new ArrayList<ArrayList<phrase>>();
		for (int i=0; i<metaPhrase.size();i++){
			if (!metaPhrase.get(i).getIsPartOf()){
				ArrayList<phrase> phList = new ArrayList<phrase>();
				phList.add(metaPhrase.get(i));
				relList.add(phList);
			}
		}
		
		return relList;
		
	}
}
