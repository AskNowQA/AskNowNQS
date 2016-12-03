package test;

import java.io.File;

import utils.wordNet;

public class checkWordNet {

	public static void main(String args[]){
		File wnDir = new File("src/main/resources/dict");
		try {
			wordNet.testRAMDictionary(wnDir);
			wordNet.getSynonyms("majbokhmjgfcbggc75n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
