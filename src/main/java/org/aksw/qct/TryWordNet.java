package org.aksw.qct;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.JWNLIOException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;


public class TryWordNet {
	

	public static void main(String[] args) {
		
		QctTemplate q1 = new QctTemplate();
		NounSynset nounSynset;
		NounSynset[] hyponyms;

		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets("fly", SynsetType.NOUN);
		for (int i = 0; i < synsets.length; i++) {
		    nounSynset = (NounSynset)(synsets[i]);
		    hyponyms = nounSynset.getHyponyms();
		    System.err.println(nounSynset.getWordForms()[0] +
		            ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms");
		}
		
		
		}
	}


