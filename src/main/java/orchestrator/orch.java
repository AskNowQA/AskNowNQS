package orchestrator;

import java.util.ArrayList;

import annotation.AnnotationOrch;
import annotation.relationAnnotationToken;
import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import question.quesOrch;
import question.questionAnnotation;
import token.token;
import init.initializer;

public class orch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//intialize the initializer
		initializer init = new initializer();
//		String question = "Give me all cosmonauts.";
		ArrayList<String> questionList = new ArrayList<String>();
		questionList.add("Give me all cosmonauts.");
		questionList.add("To which countries does the Himalayan mountain system extend?");
		questionList.add("Who was the father of Queen Elizabeth II?");
		
		for(String question: questionList){
			quesOrch question_orch = new quesOrch();
			//Now pass it to phrase merger module
			phraseOrch phrase = new phraseOrch();
			questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
			
			
			ArrayList<phrase> phraseList = phrase.startPhraseMerger(ques_annotation);
			phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
			AnnotationOrch annotation = new AnnotationOrch();
			
			ArrayList<ArrayList<relationAnnotationToken>> relAnnotation = annotation.startAnnotationOrch(phraseList,ques_annotation);
			
			
			ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
			
			
			System.out.println("phrases are");
			
			ques_annotation.setPhraseList(phraseList);
			
			for(phrase ph : phraseList){
				for(token tk : ph.getPhraseToken()){
					System.out.print(tk.getValue() + " ");
					if(ph.getUri() != null){
						System.out.println(" :the list of proabable relations are");
					}
				}
				for (relationAnnotationToken relTk : ph.getListOfProbableRelation()){
					System.out.println(relTk.getTok().getValue() + " : " + relTk.getPropertyLabel() + " :" + relTk.getScore() );
				}
				System.out.println("");
			}
		}
		
		
		
		
//		System.out.println(question_orch.questionOrchestrator(question));

	}

}
