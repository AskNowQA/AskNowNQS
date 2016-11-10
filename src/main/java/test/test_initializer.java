package test;
import tokenAnnotation.tokenAnnotationOrch;
import java.util.ArrayList;

import nlp.nlpInit;
import nlp.nlp;
import init.initializer;
import tokenAnnotation.token;

public class test_initializer {

	public static void main(String[] args) {
		//This will act like a orchestrator for the whole pipeline
		/*
		 * There is a initializer class which creates an object og nlpInit. 
		 * One of the method of nlpInit initializes pipeline.
		 * Other method returns pipeline Object
		 * nlp class does provides all the services required by the AskNow system.
		 * 
		 * */
		//Intializing the initializer 
		initializer init = new initializer();
		nlp nlp_pipeline = new nlp();
		tokenAnnotationOrch tokenizer = new tokenAnnotationOrch();
		
		String question = "Who is the president of United States Of America? " ;
		
		ArrayList<token> tokens = new ArrayList<token>();
		tokens = tokenizer.getWordTokenArrayList(question);
		System.out.println(tokens);
	}

}
