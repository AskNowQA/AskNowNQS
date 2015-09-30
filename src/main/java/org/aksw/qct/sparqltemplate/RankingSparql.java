package org.aksw.qct.sparqltemplate;

import java.util.ArrayList;
import org.aksw.qct.QctTemplate;
import org.aksw.qct.jena.RankingJena;
import org.aksw.qct.util.Spotlight;

public class RankingSparql {

	public RankingSparql(QctTemplate q1) {
		
		if(q1.getDesire().contains("DataProperty (Person)")){
			
			dbpRes1 = Spotlight.getDBpLookup(cleanEntry(q1.getInput()));
			dbpRes2= "Person";
		}
		else {
			dbpRes1 = Spotlight.getDBpLookup(cleanEntry(q1.getInput()));
			dbpRes2 = cleanEntry(q1.getDesire());
					dbpRes2 = dbpRes2.substring(0, 1).toUpperCase() + dbpRes2.substring(1);
		}
		dbpParameter = findParameter(q1.nlQuery); 
		
		RankingJena.pattern1(dbpRes1, dbpRes2, dbpParameter, topfirst);
		
	}
		/*
			ResourceResults = Jena.getDbProperty(dbpRes1);
			int possibleMatchSize=0;
			for (String string : ResourceResults) {
				if((string.toLowerCase().contains("highest"))||(string.toLowerCase().contains("fastest"))||
						(string.toLowerCase().contains("longest"))||(string.toLowerCase().contains("lowest"))
						||(string.toLowerCase().contains("largest"))||(string.toLowerCase().contains("youngest"))||(string.toLowerCase().contains("deepest"))){
					PossibleMatch.add(string); 
					possibleMatchSize++;
					CountJena.pattern1(PossibleMatch,dbpRes);
				}

			}	
			if ((q1.getDesire().toLowerCase().contains("defination"))||(q1.getInput().toLowerCase().contains("world"))||(q1.getInput().toLowerCase().contains("earth"))){
				{System.out.println("Its a list query qtr1");

				}

			}

		}*/

	private String findParameter(String nlQuery) {
		if(nlQuery.contains("lowest"))
			return "elevation";
		else if (nlQuery.contains("highest"))
			{topfirst = false;
			return "elevation";}
		
		else if (nlQuery.contains("tallest"))
			return "height";
		else if (nlQuery.contains("shortest"))
			{topfirst = false;
			return "height";}

		else if (nlQuery.contains("oldest"))
			return "birthday";
		else if (nlQuery.contains("youngest"))
			{topfirst = false;
			return "birthDate";}
		
		else if (nlQuery.contains("lightest"))
			return "weight";
		else if (nlQuery.contains("heaviest"))
			{topfirst = false;
			return "weight";}
		return null;
	}

	

	private String cleanEntry(String input) {
		String temp = input;//highest|lowest|deepest|fastest|longest|largest|youngest
		temp = temp.replace("highest", "");
		temp = temp.replace("lowest", "");
		temp = temp.replace("deepest", "");///todo
		temp = temp.replace("fastest", "");///todo
		temp = temp.replace("longest", "");
		temp = temp.replace("youngest", "");
		temp = temp.replace("oldest", "");
		temp = temp.replace("heaviest", "");
		temp = temp.replace("lightest", "");
		temp = temp.replace("tallest", "");
		temp = temp.replace("shortest", "");
		temp = temp.replace("the", "");
		System.out.println(temp.trim());
		return temp.trim();
	}

	ArrayList<String> ResourceResults = new ArrayList<>();
	ArrayList<String> PossibleMatch = new ArrayList<>();

	String dbpRes1;
	String dbpRes2;
	String dbpParameter;
	Boolean topfirst = true;
	
	Spotlight sp1 = new Spotlight();


}
