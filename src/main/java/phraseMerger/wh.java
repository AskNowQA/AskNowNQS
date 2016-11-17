package phraseMerger;

import java.util.ArrayList;
import java.util.regex.Pattern;

import phrase.phrase;
import question.questionAnnotation;

public class wh {
/*
 * 
 * This class stores the wh type and the associated phrases. This will help in predecting the SPARQL template. 
 * 
 * 
 * */
	
	public ArrayList<ArrayList<phrase>> whFormer(questionAnnotation questionAnnotation, ArrayList<phrase> metaPhrase){
		ArrayList<phrase> concept = new ArrayList<phrase>();
		Pattern WhPrase = Pattern.compile("WP|WRB|WDT");
		ArrayList<ArrayList<phrase>> whList = new ArrayList<ArrayList<phrase>>();
		for (int i=0; i<metaPhrase.size();i++){
			if (WhPrase.matcher(metaPhrase.get(i).getPosTag()).find() && !metaPhrase.get(i).getIsPartOf()){
				ArrayList<phrase> phList = new ArrayList<phrase>();
				phList.add(metaPhrase.get(i));
				metaPhrase.get(i).setIsPartOf(true);
				while(WhPrase.matcher(metaPhrase.get(i+1).getPosTag()).find() || WhPrase.matcher(metaPhrase.get(i+1).getPosTag()).find() ){
					if (!metaPhrase.get(i+1).getIsPartOf()) {
						phList.add(metaPhrase.get(i+1));
						metaPhrase.get(i+1).setIsPartOf(true);
						i = i+1;
					}
					else{
						break;
					}
				}
				whList.add(phList);
				
			}
		}
		return whList;
	}
	
}
