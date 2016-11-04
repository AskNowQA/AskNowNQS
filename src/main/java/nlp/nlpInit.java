package nlp;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;


public class nlpInit {
	/*
	 * This class initializes the pipeline objects. It has two methods
	 * One which inits the pipeline
	 * another is getter for passing the pipeline object.
	 * Usually used by nlp class defined in the same pacakage
	 * 
	 * */
	
	
	//stanford pipeline object which gets passed around in the whole pipeline
	static private StanfordCoreNLP pipeline = null;
	

	public void NlP_init(){
		//This function initializes the nlp pipieline.
		//Did not initialize the parser.model pipeline model 
		//TODO: implement parser.model part also. 
				pipeline = new StanfordCoreNLP(
				PropertiesUtils.asProperties(
							"annotators", "tokenize,ssplit,pos,lemma,parse,natlog,ner,entitymentions",
							"ssplit.isOneSentence", "true",
							"tokenize.language", "en"));
				return;
	}
	
	public StanfordCoreNLP getNLPPipeline(){
//		returns a pipeline object
		return pipeline;
	}
}
