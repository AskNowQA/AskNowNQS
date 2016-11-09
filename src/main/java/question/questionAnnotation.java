package question;

import nlp.nlp;

public class questionAnnotation {
	private String originalQuestion = null;
	private String annotatedOriginalQuestion = null;
	
	
	public questionAnnotation(String question){
		originalQuestion = question;
		nlp nlp_service = new nlp();
	}
	
	public void question_orchs()
	{
		//TODO:Discuss, if I should move this to another class
		//Send it to pre-processing pipeline 
		//store whatever comes from the pre-processing pipeline
		//send it to word annotation 
		//store it in apporpirate form 
		//return something. i.e whatever is needed. 
	}
	
}
