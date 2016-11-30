package annotation;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import phrase.phrase;
import tokenAnnotation.token;
import utils.spotlight;

public class AnnotationOrch {
	
	/*
	 * This class orchestrates the annotation. It first calls spotlight (entity linker) 
	 * and then moves to relation annotation (one-hop strategy and patty and other )
	 * 
	 * 
	 * */
	
	public void startAnnotationOrch(ArrayList<phrase> phraseList){
			//call spotlight annotation. 
			//for each phrase in the list pass it through the spotlight and check for its annotation.
		spotlight spot = new spotlight();
		
		for(phrase ph : phraseList){
			String tempString = new String();
			
			for (token tk: ph.getPhraseToken()){
				tempString = tempString + tk.getValue() + " ";
			}
			JSONArray DBpEquivalent = spot.getDBLookup(tempString, "0.0");			
			try{
				
				JSONObject obj2 = (JSONObject) DBpEquivalent.get(0);
				ph.setUri(obj2.get("uri").toString());
			}
			
			catch (Exception e){
				continue;
			}
		}
			
			
		
	}
}
