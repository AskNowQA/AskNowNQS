package preProcessing;

import utils.sendRequest;

public class preProcessing {
/*
 * Controls all pre-processing.
 * 
 * */
	public void preProcessingOrchestrator(String question){
		
	}
	
	public String resolveapostrophe (String question) throws Exception{
		
		sendRequest requester = new sendRequest();
		return requester.sendPostRequest("asa", question);
		
	}
}
