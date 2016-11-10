package orchestrator;

import phrase.phraseOrch;
import question.quesOrch;
import question.questionAnnotation;
import init.initializer;

public class orch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//intialize the initializer
		initializer init = new initializer();
		String question = "Which of Tim Burton's films had the highest budget and Apple ?";
		quesOrch question_orch = new quesOrch();
		//Now pass it to phrase merger module
		phraseOrch phrase = new phraseOrch();
		
		phrase.phraseMergerOrchestrator(question_orch.questionOrchestrator(question));
		
//		System.out.println(question_orch.questionOrchestrator(question));

	}

}
