package org.aksw.qct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public static void main(String[] args) throws JWNLException{  
		//missions
		System.out.println(getSynonyms("study"));
	}

	public static Set<String> getSynonyms(String args1){
		Dictionary d = null;
		try {

			d	= Dictionary.getDefaultResourceInstance();


			if (args1.equals("")){
				args1="source";}
			//				
			Set<String> SetOfSynonyms = new HashSet<String>();

			//	WordNetDatabase database = WordNetDatabase.getFileInstance();
			IndexWord iw = d.getIndexWord(POS.NOUN, args1);
			List<Synset> synsets = iw.getSenses();

			if (!synsets.isEmpty())	{
				System.out.println(":::::"+synsets.toString());
				for (Synset synset: synsets ){
					SetOfSynonyms.addAll(synset.getWords().stream().map(w->w.getLemma()).collect(Collectors.toSet()));
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
