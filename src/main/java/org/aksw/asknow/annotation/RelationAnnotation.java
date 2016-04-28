package org.aksw.asknow.annotation;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

//import org.aksw.hawk.experiment.IndexComparer;
//import org.aksw.hawk.index.DBOIndex;
import org.aksw.asknow.annotation.PattyIndex;
import org.aksw.asknow.query.sparql.PropertyValue;


public class RelationAnnotation {

	static PattyIndex pattyindex = new PattyIndex();
	
	/*
	 *getRelAnnotation INPUTS: relation probable candidate from NQS (desire and relation tags) 
	 *				  OUTPUTS: 
	 */
	public static Set<String> getRelAnnotation(HashSet<String> candidates, String entityURI) {
		//TODO 1. filter out the candidate: remove "of", "in", dataproperty(x)
		//        HashSet<String> filterCandidate(HashSet<String> candidates)
		Set<String> prop_label =PropertyValue.getPropertiesLabel(entityURI);
		//Set<String> entityProperties = PropertyValue.getProperties(entityURI);// all properties that are related to main entity
		Set<String> uriBucketPatty = new HashSet<>(); //Bucket to store all patty relation
		for (String c : candidates) {
			uriBucketPatty.addAll(getURISet(c, "pattyindex"));	
		}

		return match(prop_label,uriBucketPatty,candidates);
	}



	private static Set<String> match(Set<String> entityProperties, Set<String> uriBucketPatty,HashSet<String> candidates) {
		// TODO Auto-generated method stub
		Pattern skipWordList = Pattern.compile("of|on|the|from|in");
		Set<String> selectedRelation= new HashSet<String>();
		for (String r : entityProperties ){
			for(String c: candidates){
				System.out.println(c);
				c = c.trim().replaceAll(" +", " "); //avoid multiple spaces
				for(String term : c.split(" ")){
					//System.out.println(term);
					//skip terms like : of , on , from, 
					if(skipWordList.matcher(term).find())
						continue;
					if((r.toLowerCase().contains(term.toLowerCase().trim()))){
						//System.out.println((int)term);
						selectedRelation.add(term+":"+r);
					}
				}
			}

			for(String p: uriBucketPatty){
				if(r.toLowerCase().contains(p.toLowerCase())){
					selectedRelation.add(r);
				}
			}

		}

		return selectedRelation;
	}

	private static HashSet<String> getRelEntity(HashSet<String> uriBucketPatty) {
		// TODO Auto-generated method stub
		return null;
	}




	private static HashSet<String> getURISet(String token, String indextype){
		HashSet<String> result = new HashSet<String>();
		//for(String term : question.split(" "))
		{
			if(indextype.equals("pattyindex")){
				//System.out.println("Term:"+token);
				result.addAll(pattyindex.search(token));}
			//else if(index.equals("dboindex")){
			//result.addAll(dboindex.search(term));	
			//}
			//... else if.. usw
		}
		System.out.println(result);
		result = clearRedundant(result);

		return result;
	}



	private static HashSet<String> clearRedundant(HashSet<String> result) {
		// TODO Auto-generated method stub
		HashSet<String> updatedresult = new HashSet<String>();
		for(String r : result){
			if(r.contains("http://dbpedia.org/ontology/"))
				updatedresult.add(r.replace("http://dbpedia.org/ontology/", ""));

		}

		return updatedresult;
	}

}
