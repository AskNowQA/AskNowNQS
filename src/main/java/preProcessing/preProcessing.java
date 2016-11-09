package preProcessing;

import init.configuration;
import utils.sendRequest;

public class preProcessing {
/*
 * Controls all pre-processing.
 * 
 * */
	
	public String preProcessingOrchestrator(String question){
		//TODO:Add more logics and control here
		try {
			return resolveApostrophe(question);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String resolveApostrophe (String question) throws Exception{
		
		sendRequest requester = new sendRequest();
		configuration config = new configuration();
		return requester.sendPostRequest(config.getPythonMicroserviceUrl(), question);
		
	}
}
