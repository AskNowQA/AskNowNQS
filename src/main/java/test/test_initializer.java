package test;
import java.util.ArrayList;

import nlp.nlpInit;
import nlp.nlp;
import init.initializer;



public class test_initializer {

	public static void main(String[] args) {
		//This acts like a orchestrator for the whole pipeline
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
		String question = "Who is the president of barack Obama? " ; 
		ArrayList<String> posTags = new ArrayList<String>();
		posTags = nlp_pipeline.getPosTags(nlp_pipeline.getAnnotatedToken(question));
		System.out.println(posTags);
	}

}
