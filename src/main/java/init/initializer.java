package init;
import nlp.nlpInit;

public class initializer {
//	This class initializes all the library needed by asknow which involves 
//	stanfordnlp
//	TODO: Add other libraries list here. 
	

	public initializer(){
		//initializing nlp library 
		nlpInit nlp_init = new nlpInit();
		nlp_init.NlP_init(); //does the final initialization
		
	}
	
}
