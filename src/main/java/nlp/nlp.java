package nlp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.MentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;



public class nlp {
	//constructor creates a nlp pipeline object
	/*
	 * This class provides all the services of nlp which are needed by the AskNow pipeline. 
	 * Whenever an object of this class in created it getd an instance of pipeline object 
	 * 
	 * */
	
	static StanfordCoreNLP pipeline = null;
	static Annotation document = null; 
	
	public nlp(){
		nlpInit init = new nlpInit();
		pipeline = init.getNLPPipeline();
	}
	
	public Annotation getAnnotatedToken(String question){
		document = new Annotation(question);
		pipeline.annotate(document);
		return document;
	}
	
	public ArrayList<String> getPosTags(Annotation document){
		
/*		returns an arraylist of postags for the given sentence/document
 * 		Input is an annotated document , annotated from stanford core nlp toolkit. 
	*/	
		ArrayList<String> posTags = new ArrayList<String>();
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				 //generates Pos tsd
			    posTags.add(token.get(PartOfSpeechAnnotation.class));
			  }
		}  
		return posTags;
	}
	
	public ArrayList<String> getTokens(Annotation document){
		
		/*		returns an arraylist of tokens for the given sentence/document
		 * 		Input is an annotated document , annotated from stanford core nlp toolkit. 
			*/	
				ArrayList<String> tokens = new ArrayList<String>();
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				for(CoreMap sentence: sentences) {
					  // traversing the words in the current sentence
					  // a CoreLabel is a CoreMap with additional token-specific methods
					  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
						 //generates Pos tsd
						  tokens.add(token.get(TextAnnotation.class));
					  }
				}  
				return tokens;
			}
	public ArrayList<String> getNERTags(Annotation document){
		/*		returns an array list of NER tags for the given sentence/document
		 * 		Input is an annotated document , annotated from stanford core nlp toolkit. 
			*/	
				ArrayList<String> NERTags = new ArrayList<String>();
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				for(CoreMap sentence: sentences) {
					  // traversing the words in the current sentence
					  // a CoreLabel is a CoreMap with additional token-specific methods
					  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
						 //generates NER labels
						  NERTags.add(token.get(NamedEntityTagAnnotation.class));
					  }
				}  
				return NERTags;
	}
	
	public ArrayList<String> getLemmas(Annotation document){
		/*		returns an array list of NER tags for the given sentence/document
		 * 		Input is an annotated document , annotated from stanford core nlp toolkit. 
			*/	
				ArrayList<String> Lemmas = new ArrayList<String>();
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				for(CoreMap sentence: sentences) {
					  // traversing the words in the current sentence
					  // a CoreLabel is a CoreMap with additional token-specific methods
					  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
						 //generates NER labels
						  Lemmas.add(token.get(LemmaAnnotation.class));
					  }
				}  
				return Lemmas;
	}
	
	//TODO: Implement all other functionality
	
}
