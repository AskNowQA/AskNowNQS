package annotation;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import phrase.phrase;
import token.token;
import utils.spotlight;

 public  class entityAnnotation {

	public static void Annotation(ArrayList<phrase> phraseList){
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
