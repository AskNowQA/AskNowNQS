package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class wordNet {
	public static IRAMDictionary dict ;
	
	public static IRAMDictionary testRAMDictionary ( File wnDir ) throws Exception {
		
		
		dict = new RAMDictionary ( wnDir , ILoadPolicy . NO_LOAD ) ;
		dict.open ();
		System.out.println("Loading Wordnet into memory");
		dict.load(true);
		System.out.println("done loading the dictionary in the RAM ");	
		return dict;
	}
	
	
	
	public static void getSynonyms (String word) {

		IIndexWord idxWord = dict.getIndexWord(word, POS.NOUN) ;
		IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
		IWord iword = dict.getWord(wordID) ;
		ISynset synset = iword.getSynset() ;
		List<IWordID> relatedWord = iword.getRelatedWords();
		System.out.println("synonims are ");
		for( IWord w : synset.getWords () ){
			System . out . println(w.getLemma()) ;
		 }
			System.out.println("related words are ");
		for (IWordID w : relatedWord){
			System.out.println("blahh");
			System.out.println(w.getLemma());
		}
	}
	
}

