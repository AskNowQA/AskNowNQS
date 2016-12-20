package utils;

import annotation.relationAnnotation;
import annotation.relationAnnotationToken;
import phrase.phrase;

public class ontologyRelation {
	/*
	 * This is a helper class which takes into replaces the property to the ontology relation , if it exists in probable set of relationships. 
	 * */

	public static relationAnnotationToken getOntologyRelation(relationAnnotationToken relTK, phrase ph){
		
		for(relationAnnotationToken reltk : ph.getListOfProbableRelation()){
			if(reltk.getUri() == relTK.getUri().replace("property", "ontology")){
				return reltk;
			}
		}
		return relTK;
		
	}
	
}
