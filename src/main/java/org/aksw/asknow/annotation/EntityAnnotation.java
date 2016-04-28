package org.aksw.asknow.annotation;


import java.util.HashSet;
import java.util.Set;

//import org.aksw.asknow.util.EntityAnnotate;

public class EntityAnnotation {

	@SuppressWarnings("null")
	public static Set<String> getEntityAnnotation(String nlQuery) {
		System.out.println("::"+nlQuery);
		Set<String> dbpRes = new HashSet<String>();
		//dbpRes = Fox.annotate(nlQuery);
		System.out.println(dbpRes.size());
		if (dbpRes.isEmpty()){
			
			dbpRes.addAll(Spotlight.getDBpLookup(nlQuery));
		}
		if (dbpRes.isEmpty()){
			System.out.println("Could not annotate the Entity");
			return null;
		}
		return dbpRes;
	}

	
}
