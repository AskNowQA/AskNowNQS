package org.aksw.nqs.util;
import java.util.*;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.*;
import net.sf.extjwnl.dictionary.Dictionary;

/** Returns wordnet synonyms from a local dictionary. **/
public class WordNetSynonyms
{
	/** Returns wordnet synonyms from a local dictionary. **/
	@SneakyThrows(JWNLException.class)
	public static Set<String> getSynonyms(String args1)
	{
		Dictionary d = null;
			d	= Dictionary.getDefaultResourceInstance();
			if (args1.isEmpty()) {args1="source";}

			Set<String> synonyms = new HashSet<>();

			//	WordNetDatabase database = WordNetDatabase.getFileInstance();
			IndexWord iw = d.getIndexWord(POS.NOUN, args1);
			List<Synset> synsets = iw.getSenses();

			if (!synsets.isEmpty())
			{
				//				System.out.println(":::::"+synsets.toString());
				for (Synset synset: synsets )
				{
					synonyms.addAll(synset.getWords().stream().map(w->w.getLemma()).collect(Collectors.toSet()));
				}
			}
			else
			{
				System.err.println("No synsets exist that contain " +
						"the word form '" + args1 + "'");
			}
			return synonyms;
	}
	
}