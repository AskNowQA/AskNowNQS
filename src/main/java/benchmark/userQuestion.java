package benchmark;

import java.util.ArrayList;
import java.util.Scanner;

import annotation.AnnotationOrch;
import annotation.relationAnnotationToken;
import init.initializer;
import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import queryConstructor.SparqlSelector;
import question.quesOrch;
import question.questionAnnotation;
import utils.qaldQuery;

public class userQuestion {
	
	public static void main(String args[]){
		
		/*
		 * The class takes a question as user input and executes it to print the sparql and answer. This is in parallel to the executeQuestion class.
		 * For detailed comments on function check qald6 and executeQuestion class.
		 * */
		
		initializer init = new initializer(); //boiler plate initializer. 
		
		
		while(true)
		{
			
		
			System.out.println("Enter your question, To exit type 'exit' ");
			Scanner scanner = new Scanner(System.in);
			String question = scanner.nextLine();
			if (question == "exit"){
				//break from the while loop
				System.out.println("exiting the system !!");
				break;
				
			}
		
		
		/*
		 * Question orchestrator: Takes input as straing (question) and returns annotated question.
		 * 
		 * General Note: FOr more information about any class check the class specific documentation.
		 * */
			
			ArrayList<String> askNow_answer = null;	//This will store the answer generated after the question is executed.
			quesOrch question_orch = new quesOrch(); 
			questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
			phraseOrch phrase = new phraseOrch();
			ArrayList<phrase> phraseList = phrase.startPhraseMerger(ques_annotation);
			phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
			AnnotationOrch annotation = new AnnotationOrch();
			ArrayList<ArrayList<relationAnnotationToken>> relAnnotation = annotation.startAnnotationOrch(phraseList,ques_annotation);
			ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
			ques_annotation.setPhraseList(phraseList);
			String askNow_sparql = SparqlSelector.sparqlSelector(ques_annotation);
			System.out.println("The generated sparql is :" + askNow_sparql);
			
			if (!askNow_sparql.equals("")){
				 askNow_answer = qaldQuery.returnResultsQald(askNow_sparql);
				 System.out.println(askNow_answer);
			}
			else{
				System.out.println("the sparql returend was null. The system can't handel the question right now.");
			}	
			
		}
		
	}
	
}
