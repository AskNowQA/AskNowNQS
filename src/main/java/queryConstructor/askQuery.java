package queryConstructor;

import java.util.ArrayList;
import java.util.regex.Pattern;

import annotation.relationAnnotationToken;
import phrase.phrase;
import question.questionAnnotation;
import utils.ontologyRelation;

public class askQuery {

	static double comparableScore = 0.2;
	public static String askQuerylogic(questionAnnotation ques_annotation){
		ArrayList<phrase> annotatedPhraseList  = listQuery.getAnnotatedPhraseList(ques_annotation);
		System.out.println(annotatedPhraseList.size() + "@askquery");
		//Logic for one annotated phrase 
		String sparql = "";
		Pattern type = Pattern.compile("type | type of |belongs");



		if(annotatedPhraseList.size() == 1){
			//Only two types of sparql can be present here
			//extracting the first candidate 
			return singleTripleSparql(annotatedPhraseList.get(0));
		}

		//Logic for two annotated phrases

		if(annotatedPhraseList.size() == 2){
			//type query
			System.out.println("at two annotated phrase @askQuery");
			for(phrase ph : ques_annotation.getPhraseList()){
				if(ph.getUri() == null){
					if(type.matcher(ph.getphraseString()).find()){
						//write a query
						//return
						sparql = "ASK WHERE { " +annotatedPhraseList.get(0).getUri() + " a " + annotatedPhraseList.get(1).getUri().replaceAll("resource", "ontology") + " }";
						return sparql;
					}
				}
			}
			if(annotatedPhraseList.get(0).getListOfProbableRelation() != null && annotatedPhraseList.get(1).getListOfProbableRelation() != null){

				Integer indexPhraseOne = ques_annotation.getPhraseList().indexOf(annotatedPhraseList.get(0));
				Integer indexPhraseTwo = ques_annotation.getPhraseList().indexOf(annotatedPhraseList.get(1));

				if(indexPhraseTwo-indexPhraseOne == 2){
					if(ques_annotation.getPhraseList().get(indexPhraseOne + 1).getphraseString().contentEquals("a")){
						//its a type query
						sparql = "ASK WHERE { " +annotatedPhraseList.get(0).getUri() + " a " + annotatedPhraseList.get(1).getUri().replaceAll("resource", "ontology") + " }";
						System.out.println("at a query @askQuery");
						return sparql;
					}
				}

				//not a type query 
				/*
				 * We will first try to find a common relation between the two annotated phrase. If not found find the one with the highest score.
				 * Then follow the regular sparql generation strategy
				 * */

				//space getting exponential .. If there are 100 relations then it runs for 100*100 times
				ArrayList<relationAnnotationToken> listOfCommonProbableRelation = new ArrayList<relationAnnotationToken>(annotatedPhraseList.get(0).getListOfProbableRelation());

				for(relationAnnotationToken relTk: annotatedPhraseList.get(0).getListOfProbableRelation()){
					for(relationAnnotationToken tempRelTk: annotatedPhraseList.get(1).getListOfProbableRelation()){
						if(relTk.getPropertyLabel() == tempRelTk.getPropertyLabel()){
							listOfCommonProbableRelation.add(relTk);
						}
					}
				}


				relationAnnotationToken PhraseOneHighestScoreRel = annotatedPhraseList.get(0).getListOfProbableRelation().get(0);
				relationAnnotationToken PhrasetwoHighestScoreRel = annotatedPhraseList.get(1).getListOfProbableRelation().get(0);


				//Now that there are few possiblites 
				//if there is no common relation. Simply take the hi
				if(listOfCommonProbableRelation.size() == 0){
					//No common relation. take the one with the highest
					if(PhraseOneHighestScoreRel.getScore() > PhrasetwoHighestScoreRel.getScore()){
						return singleTripleSparql( annotatedPhraseList.get(0));
					}
					else{
						return singleTripleSparql( annotatedPhraseList.get(1));
					}
				}

				else{
					//if common relation then compare the common relation score with the highest score of the other entites

					if(comparable(PhraseOneHighestScoreRel.getScore(), listOfCommonProbableRelation.get(0).getScore()) && comparable(PhrasetwoHighestScoreRel.getScore(), listOfCommonProbableRelation.get(0).getScore())  ){
						if(listOfCommonProbableRelation.get(0).isIncomingProperty()){
							//sparql of the form phrase2 property phrase1
							sparql = "ASK WHERE { " + annotatedPhraseList.get(1).getUri()+" <" + ontologyRelation.getOntologyRelation(listOfCommonProbableRelation.get(0),annotatedPhraseList.get(0)).getUri() + "> " + annotatedPhraseList.get(0).getUri()+ " }";
							System.out.println(sparql);
							return sparql;
						}
						else{
							//sparql of type phrase1 property phrase2
							sparql = "ASK WHERE { " + annotatedPhraseList.get(0).getUri()+" <" + ontologyRelation.getOntologyRelation(listOfCommonProbableRelation.get(0),annotatedPhraseList.get(0)).getUri()  + "> " + annotatedPhraseList.get(1).getUri()+ " }";
							System.out.println(sparql);
							return sparql;
						}
					}

					//if not comparable then take the highest score
					else{
						if(PhraseOneHighestScoreRel.getScore() > PhrasetwoHighestScoreRel.getScore()){
							return singleTripleSparql( annotatedPhraseList.get(0));
						}
						else{
							return singleTripleSparql( annotatedPhraseList.get(1));
						}
					}
				}
			}
			return sparql;
		}
		return sparql;

	}
	public static String singleTripleSparql(phrase ph){

		relationAnnotationToken tk = ph.getListOfProbableRelation().get(0);
		String sparql = "";
		// checking if the token is a part of incoming or outgoing property
		if (tk.isIncomingProperty()){
			//part of incoming property

			sparql = "ASK WHERE { ?var <"+ ontologyRelation.getOntologyRelation(tk,ph).getUri() + "> " + ph.getUri()+" . }";
			System.out.println(sparql);
			// ?x tk ph
			// tk.getUri() --> for property uri
			//	annotatedPhraseList.get(0).getUri() -- entity uri 
			return sparql;

		}
		else{
			//part of outgoing property 
			// ?x tk ph
			sparql = "ASK WHERE { " + ph.getUri()+" <" + ontologyRelation.getOntologyRelation(tk,ph).getUri() + "> ?var . }";
			System.out.println(sparql);
			return sparql;
		}
	}
	public static Boolean comparable(Float score1, Float score2){
		//returns true if score 2 is in range of score 1
		float lowerBound = (float) (score1 - comparableScore);
		float upperBound = (float) (score1 + comparableScore);

		if(lowerBound <= score2){
			return true;
		}

		else{
			return false;
		}
	}
	//logic for two annotated phrase 

	public static ArrayList<phrase> getAnnotatedPhraseList(questionAnnotation ques_annotation){

		//This function returns the annotated phrase. The objective is to identify the number of 
		//triple patterns like below 
		// {a,b,?c} or {?a,b,c}

		ArrayList<phrase> annotatedPhraseList = new ArrayList<phrase>();
		for(phrase ph : ques_annotation.getPhraseList()){
			System.out.println(ph.getUri());
			if(ph.getUri() != null  ){
				annotatedPhraseList.add(ph);
			}
		}
		return annotatedPhraseList;
	}

}
