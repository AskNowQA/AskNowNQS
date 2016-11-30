package orchestrator;

import java.util.ArrayList;

import annotation.AnnotationOrch;
import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import question.quesOrch;
import question.questionAnnotation;
import tokenAnnotation.token;
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
			
			annotation.startAnnotationOrch(phraseList);
			
			
			ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
			
//			phraseMergerOrchestrator.printConceptList(conceptList);
		}
		
		
		
//		System.out.println(question_orch.questionOrchestrator(question));

	}

}
