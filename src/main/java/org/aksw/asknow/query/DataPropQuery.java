package org.aksw.asknow.query;

import java.util.ArrayList;
import org.aksw.asknow.Dictionary;
import lombok.extern.slf4j.Slf4j;

/** TODO KO@Mohnish: Add Javadoc */
@Slf4j public class DataPropQuery {


	ArrayList<String> getDataProperty(String desire, String r2, ArrayList<String> resourceResults) {
		// TODO Auto-generated method stub
		ArrayList<String> PossibleMatch  = new ArrayList<>();
		ArrayList<String> DesireList = new ArrayList<>();
		Dictionary Dict = new Dictionary();
		DesireList = Dict.getDesire(desire, r2);
		log.trace("ckp2");
		for (String DesireListMember : DesireList ){
			log.trace("ckp3");
			for (String string : resourceResults) {
				if(string.toLowerCase().contains(DesireListMember.toLowerCase())){
					PossibleMatch.add(string);
				}
			}
		}
		return PossibleMatch;
	}

}
