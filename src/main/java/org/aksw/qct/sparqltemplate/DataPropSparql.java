package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;

import org.aksw.qct.Dictionary1;

public class DataPropSparql {
	

	 ArrayList<String> getDataProperty(String desire, String r2, ArrayList<String> resourceResults) {
		// TODO Auto-generated method stub
		ArrayList<String> PossibleMatch  = new ArrayList<>();
		ArrayList<String> DesireList = new ArrayList<>();
		Dictionary1 Dict = new Dictionary1();
		DesireList = Dict.getDesire(desire, r2);
		System.out.println("ckp2");
		for (String DesireListMember : DesireList ){
			System.out.println("ckp3");
			for (String string : resourceResults) {
			if(string.toLowerCase().contains(DesireListMember.toLowerCase())){
				PossibleMatch.add(string);
																			}
													}
									}
		return PossibleMatch;
	 }

}
