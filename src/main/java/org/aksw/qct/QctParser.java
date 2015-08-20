package org.aksw.qct;

import java.util.regex.Pattern;

import org.aksw.qct.sparqltemplate.CountSparql;
import org.aksw.qct.sparqltemplate.ListSparql;
import org.aksw.qct.sparqltemplate.RankingSparql;

public class QctParser {

	public static void main(String[] args){
		QctTemplate q1 = new QctTemplate();
		System.out.println(q1.getAll());
		System.out.println(q1.getDesire());
		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest");


		if (q1.getNLQuery().toLowerCase().contains("how many")){
			System.out.println("Its a count query");
			CountSparql cs1 = new CountSparql(q1);
			}
		else if(q1.getDesire().equalsIgnoreCase("list")){
			System.out.println("Its a list query");
			ListSparql ls1 = new ListSparql(q1); //sparqlCall
			}
		else if (superlativeWordList.matcher(q1.getNLQuery()).find()){
			System.out.println("Its a Ranking query");
			RankingSparql rs1 = new RankingSparql(q1);
				
			}
		else if (q1.getDesire().contains("DataProperty")){
			System.out.println("Its a XofR1Y query");
			}



	}//end of main




}//end of class




