package benchmark;

import init.initializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import annotation.AnnotationOrch;
import annotation.relationAnnotationToken;

import java.io.IOException;
import java.nio.file.Files;








import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import question.quesOrch;
import question.questionAnnotation;
import token.token;

public class conceptBenchmark {
	/*
	 * This class reads from a trext file in resource and prints all the concepts
	 * 
	 * */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//intialize the initializer
		initializer init = new initializer();
		//		String question = "Give me all cosmonauts.";
		ArrayList<String> questionList = new ArrayList<String>();
	

		for(String question: readLinesUsingFileReader()){
			

			quesOrch question_orch = new quesOrch();
			//Now pass it to phrase merger module
			phraseOrch phrase = new phraseOrch();
			questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
			
			
			ArrayList<phrase> phraseList = phrase.startPhraseMerger(ques_annotation);
			phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
			AnnotationOrch annotation = new AnnotationOrch();
			
			ArrayList<ArrayList<relationAnnotationToken>> relAnnotation = annotation.startAnnotationOrch(phraseList,ques_annotation);
			
			
			ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
			
			
			System.out.println("phrases are");
			for(phrase ph : phraseList){
				for(token tk : ph.getPhraseToken()){
					System.out.print(tk.getValue() + " ");
					if(ph.getUri() != null){
						System.out.println(" :the list of proabable relations are");
					}
				}
				for (relationAnnotationToken relTk : ph.getListOfProbableRelation()){
					System.out.println(relTk.getTok().getValue() + " : " + relTk.getPropertyLabel() + " :" + relTk.getScore() );
				}
				System.out.println("");
			}
		
			
		}
			        
	       
	}
	
	 public static ArrayList<String> readLinesUsingFileReader() throws IOException
     {
         ArrayList<String> question = new ArrayList<String>();
         FileReader fr = new FileReader("src/main/resources/questions.txt");
         BufferedReader br = new BufferedReader(fr);
         String line;
         while((line = br.readLine()) != null){
             question.add(line);
         }
         br.close();
         fr.close();
         return question;
     }

}
