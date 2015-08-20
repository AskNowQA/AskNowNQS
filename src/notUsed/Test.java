package notUsed;
import java.util.HashSet;
import java.util.Set;

import qct2sparql.BOAcall;
import qct2sparql.WordNetSynonyms;
public class Test {
	public static void main(String[] args) throws Exception{
		BOAcall instance1 = new BOAcall();
		System.out.println("ans"+instance1.getBOAequivalent("population"));
		System.out.println("test run");
		WordNetSynonyms instance = new WordNetSynonyms();
		Set<String> SynonymsWord1 = new HashSet<String>();
		//Set<String> SynonymsWord2 = new HashSet<String>();
		SynonymsWord1 = instance.getSynonyms("tall");
		System.out.println(SynonymsWord1);
		//SynonymsWord2 = instance.getSynonyms("leader");
		//System.out.println(SynonymsWord2);
		
		//SynonymsWord1.retainAll(SynonymsWord2);
		//System.out.println("\n\n "+SynonymsWord1);*/
		
	}
}
