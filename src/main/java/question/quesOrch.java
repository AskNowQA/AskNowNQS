package question;

import preProcessing.preProcessing;

public class quesOrch {
	
	/*
	 * This class orchestrates the question annotation part. It takes care of pre-processing,
	 * word annotation and other such aspects
	 * */
	
	public String questionOrchestrator(String question)
	{
		//TODO:Discuss, if it needs to be moved to another class
		
		questionAnnotation ques = new questionAnnotation(question);
		preProcessing pre = new preProcessing();
		String processedQuestion = pre.preProcessingOrchestrator(question);
		ques.setpreProcessingQuestion(processedQuestion);
		return processedQuestion;
		
		
		//store whatever comes from the pre-processing pipeline
		//send it to word annotation 
		//store it in apporpirate form 
		//return something. i.e whatever is needed. 
	}
	
}
