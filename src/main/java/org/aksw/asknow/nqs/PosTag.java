package org.aksw.asknow.nqs;


import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosTag { 
	String sentence;
	MaxentTagger tagger;

	public PosTag(){
		tagger = new MaxentTagger("stanford-postagger-2014-01-04/models/english-bidirectional-distsim.tagger"); 
	}
	
	public String getTaggedSentence(String input){
		return tagger.tagString(input);
	}
	
	public List<TaggedWord> getTaggedWordsList(List<? extends HasWord> list){
		return tagger.tagSentence(list);
	}
	
	/*		TAGS:

			CC Coordinating conjunction
			CD Cardinal number
			DT Determiner
			EX Existential there
			FW Foreign word
			IN Preposition or subordinating conjunction
			JJ Adjective
			JJR Adjective, comparative
			JJS Adjective, superlative
			LS List item marker
			MD Modal
			NN Noun, singular or mass
			NNS Noun, plural
			NNP Proper noun, singular
			NNPS Proper noun, plural
			PDT Predeterminer
			POS Possessive ending
			PRP Personal pronoun
			PRP$ Possessive pronoun
			RB Adverb
			RBR Adverb, comparative
			RBS Adverb, superlative
			RP Particle
			SYM Symbol
			TO to
			UH Interjection
			VB Verb, base form
			VBD Verb, past tense
			VBG Verb, gerund or present participle
			VBN Verb, past participle
			VBP Verb, non­3rd person singular present
			VBZ Verb, 3rd person singular present
			WDT Wh­determiner
			WP Wh­pronoun
			WP$ Possessive wh­pronoun
			WRB Wh­adverb
			
//			http://stackoverflow.com/questions/1833252/java-stanford-nlp-part-of-speech-labels
	 */
}




