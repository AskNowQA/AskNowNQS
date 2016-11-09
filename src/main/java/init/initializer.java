package init;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import nlp.nlpInit;

public class initializer {
//	This class initializes all the library needed by asknow which involves 
//	stanford nlp
//	TODO: Add other libraries list here. 
	

	public initializer(){
//		//initialize python project 
//		try {
////			Process p = Runtime.getRuntime().exec("python apostrophe_server.py");
//			try {
//				TimeUnit.SECONDS.sleep(60);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("done init python script");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//initializing NLP library 
		nlpInit nlp_init = new nlpInit();
		nlp_init.NlP_init(); //does the final initialization
		
	}
	
}
