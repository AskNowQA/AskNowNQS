package queryConstructor;
import java.util.ArrayList;

import annotation.relationAnnotationToken;
import phrase.phrase;
import question.questionAnnotation;
import token.token;
import utils.ontologyRelation;


public class countQuery {


public static String countQuerylogic(questionAnnotation ques_annotation){
		
		ArrayList<phrase> annotatedPhraseList  = countQuery.getAnnotatedPhraseList(ques_annotation);
		System.out.println(annotatedPhraseList.size());
		String sparql = "";
		
		if (annotatedPhraseList.size() == 1){
			
			//first check for this type of sparql 
			//count(a)	a type b
			
			
			
			
			//Only two types of sparql can be present here
			//extracting the first candidate 
			if(annotatedPhraseList.get(0).getListOfProbableRelation().size() == 0){
				//write a sparql query here
				sparql = "SELECT (COUNT(DISTINCT ?var) as ?c) WHERE { ?var <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " + annotatedPhraseList.get(0).getUri().replaceAll("resource", "ontology") + " . }";
				return sparql;
			}
			
			
			relationAnnotationToken tk = annotatedPhraseList.get(0).getListOfProbableRelation().get(0);
			
			// checking if the token is a prt of incoming or outgoing property
			if (tk.isIncomingProperty()){
				//part of incoming property
				
				sparql = "SELECT (COUNT(DISTINCT ?var) as ?c) WHERE { ?var <"+ ontologyRelation.getOntologyRelation(tk,annotatedPhraseList.get(0)).getUri() + "> " + annotatedPhraseList.get(0).getUri()+" . }";
				System.out.println(sparql);
				// ?x tk ph
				// tk.getUri() --> for property uri
				//	annotatedPhraseList.get(0).getUri() -- entity uri 
				return sparql;
				
			}
			else{
				//part of outgoing property 
				// ?x tk ph
				sparql = "SELECT (COUNT(DISTINCT ?var) as ?c) WHERE { " + annotatedPhraseList.get(0).getUri()+" <" + ontologyRelation.getOntologyRelation(tk,annotatedPhraseList.get(0)).getUri() + "> ?var . }";
				System.out.println(sparql);
				return sparql;
			}
		}
		return sparql;
	}
	
	public static ArrayList<phrase> getAnnotatedPhraseList(questionAnnotation ques_annotation){
		
		//This function returns the annotated phrase. The objective is to identify the number of 
		//triple patterns like below 
		// {a,b,?c} or {?a,b,c}
		
		ArrayList<phrase> annotatedPhraseList = new ArrayList<phrase>();
		for(phrase ph : ques_annotation.getPhraseList()){
			if(ph.getUri() != null ){
				annotatedPhraseList.add(ph);
			}
		}
		return annotatedPhraseList;
	}




}


