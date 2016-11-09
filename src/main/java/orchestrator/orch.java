package orchestrator;

import question.quesOrch;
import question.questionAnnotation;
import init.initializer;

public class orch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//intialize the initializer
		initializer init = new initializer();
		String question = "Who is the president of United States Of America ?";
		quesOrch question_orch = new quesOrch();
		System.out.println(question_orch.questionOrchestrator(question));

	}

}
