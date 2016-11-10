package phrase;

import question.questionAnnotation;
import utils.spotlight;

public class phraseOrch {
	/*
	 * 
	 * This class contains rules to merge tokens into phrases and also access definers for phrases. 
	 * 
	 * */
	
	public void phraseMergerOrchestrator(questionAnnotation questionAnnotation){
		
		//pass it through DbPedia spotlight
		spotlightAnnotation(questionAnnotation.getpreProcessingQuestion());
		
	}
	
	public void spotlightAnnotation(String question)
	{
		//calling spotlight
//		spotlight spot = new spotlight()
		System.out.println(spotlight.getDBpLookup(question));
	}
}
