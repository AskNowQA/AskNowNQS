package queryConstructor;

import java.util.regex.Pattern;

import question.questionAnnotation;
import token.token;

public class SparqlSelector {

	public static String sparqlSelector(questionAnnotation ques_annotation){
		
		//Based on certain patterns this function returns the type of sparql query the natural language query corresponds to
		//If token == list then we assume it to be a list query
		
		Pattern list = Pattern.compile("list |List |lists |Lists "); //add a proper regular expresion which is case insensitive
		Pattern howmany = Pattern.compile("how many |count |enumerate|how often ");
		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");
		Pattern booleanQuery = Pattern.compile("is|does|was|were|are|do|did|does|whether");
		String sparql = "";
		
		if(booleanQuery.matcher(ques_annotation.getTokenlist().get(0).getValue().toLowerCase()).find()){
			System.out.println("Its an ask query");
			sparql = askQuery.askQuerylogic(ques_annotation);
			return sparql;
		}
		for (token tk: ques_annotation.getTokenlist()){
			if (list.matcher(tk.getValue()).find()){
				System.out.println("Its a list query");
				//Add code logics here
				sparql = listQuery.listQuerylogic(ques_annotation);
				return sparql;
			}
		}
		

		if(howmany.matcher(ques_annotation.getPreProcessingQuestion().toLowerCase()).find()){
			sparql = countQuery.countQuerylogic(ques_annotation);
			return sparql;
		}
		
		else if(superlativeWordList.matcher(ques_annotation.getPreProcessingQuestion().toLowerCase()).find()){
			System.out.println("Its a superlative/cpmparative question");
		}
		
		else {
			
			System.out.println("remaining query");
			sparql = listQuery.listQuerylogic(ques_annotation);
			return sparql;
		}
		
		return sparql;
	}
}
