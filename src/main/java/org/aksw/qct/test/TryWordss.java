package org.aksw.qct.test;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;

public class TryWordss {
	public static void main(String args[]) throws JWNLException {

		

		Dictionary wordnet = Dictionary.getDefaultResourceInstance();

		IndexWord  word = wordnet.getIndexWord(POS.VERB, "run");
		System.out.println(word);

		//IndexWord s = wordnet.
		/*IndexWordSet set = wordnet.lookupAllIndexWords("run");
		System.out.println(set.getLemma().isEmpty());

		String str = set.getLemma();
		System.out.println(str);

		IndexWord word = set.getIndexWordArray();
		System.out.println(word.length);*/

		// Turn it into an array of IndexWords
		/*IndexWord ws = set.getIndexWordArray();
		POS p = ws.getPOS(); 

		Set<String> synonyms = new HashSet<String>();
		//IndexWord indexWord = wordnet.lookupIndexWord(POS.NOUN, "wife");
		IndexWord indexWord = wordnet.lookupIndexWord(p, "director");
		List<Synset> synSets = indexWord.getSenses();
		for (Synset synset : synSets)
		{ List<Word> words = synset.getWords();
		for (Word word : words) { synonyms.add(word.getLemma());
		}
		}
		System.out.println(synonyms); */  

		}
}
