package org.aksw.qct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;


/**
 *
 */
public class WordNetSynonyms
{
	//public static void main(String[] args){  
	//missions
	//System.out.println(getSynonyms("missions"));
	//}

	public Set<String> getSynonyms(String args1){
		Dictionary d = null;
		try {
			d	= Dictionary.getDefaultResourceInstance();



			if (args1==""){
				args1="source";}
			//				
			Set<String> SetOfSynonyms = new HashSet<String>();

			//		WordNetDatabase database = WordNetDatabase.getFileInstance();
			IndexWord iw = d.getIndexWord(POS.NOUN, args1);

			List<Synset> synsets = iw.getSenses();
			if (!synsets.isEmpty())	{
				for (Synset synset: synsets ){
					String[] wordForms = synset.getVerbFrames();
					for (int j = 0; j < wordForms.length; j++){
						SetOfSynonyms.add(wordForms[j]);
					}

				}

			}
			else
			{
				System.err.println("No synsets exist that contain " +
						"the word form '" + args1 + "'");
			}

			return SetOfSynonyms;

		} catch (JWNLException e) {
			throw new RuntimeException(e);
		}
	}
}
