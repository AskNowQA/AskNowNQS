package question;

import java.util.ArrayList;

import preProcessing.preProcessing;
import tokenAnnotation.token;
import tokenAnnotation.tokenAnnotationOrch;

public class quesOrch {
	
	/*
	 * This class orchestrates the question annotation part. It takes care of pre-processing,
	 * word annotation and other such aspects
	 * */
	
	public questionAnnotation questionOrchestrator(String question)
	{
		//TODO:Discuss, if it needs to be moved to another class
		
		//question preprocessing
		questionAnnotation ques = new questionAnnotation(question);
		preProcessing pre = new preProcessing();
		String processedQuestion = pre.preProcessingOrchestrator(question);
		ques.setpreProcessingQuestion(processedQuestion);
		//word based annotation
		tokenAnnotationOrch tokenAnnotation = new tokenAnnotationOrch();
		ArrayList<token> tokenList = tokenAnnotation.getWordTokenArrayList(processedQuestion);
		ques.settokenlist(tokenList);
		return ques;
		
	}
	
}
