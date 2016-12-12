package benchmark;

import java.util.ArrayList;

import annotation.AnnotationOrch;
import annotation.relationAnnotationToken;
import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import question.quesOrch;
import question.questionAnnotation;

public class executeQuestion {

	
	/*
	 * This starts the processing pipelines. Also has a verbose variable which prints accordingly.
	 * 
	 * 
	 * */
	
	public static void execute(String question, Boolean verbose){
		if(verbose){
			System.out.println("The question is " + question );
		}
		
		quesOrch question_orch = new quesOrch();
		questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
		phraseOrch phrase = new phraseOrch();
		ArrayList<phrase> phraseList = phrase.startPhraseMerger(ques_annotation);
		phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
		AnnotationOrch annotation = new AnnotationOrch();
		ArrayList<ArrayList<relationAnnotationToken>> relAnnotation = annotation.startAnnotationOrch(phraseList,ques_annotation);
		ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
		ques_annotation.setPhraseList(phraseList);
		if(verbose){
			
		}
		
		
	}
}
