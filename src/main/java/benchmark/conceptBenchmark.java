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
import java.io.IOException;
import java.nio.file.Files;








import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import question.quesOrch;
import question.questionAnnotation;

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
		questionList.add("Give me all cosmonauts.");
		questionList.add("To which countries does the Himalayan mountain system extend?");
		questionList.add("Who was the father of Queen Elizabeth II?");

		for(String question: readLinesUsingFileReader()){
			System.out.println("");
//			System.out.println(question);
			quesOrch question_orch = new quesOrch();
			//Now pass it to phrase merger module
			phraseOrch phrase = new phraseOrch();
			questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
			System.out.println(ques_annotation.getpreProcessingQuestion());
			
			
			ArrayList<phrase> metaPhrase = phrase.startPhraseMerger(ques_annotation);
			phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
			ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, metaPhrase);
//			phraseMergerOrchestrator.printConceptList(conceptList);
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
