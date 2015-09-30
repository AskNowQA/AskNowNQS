package org.aksw.qct;

import java.util.regex.Pattern;

import org.aksw.qct.sparqltemplate.BooleanSparql;
import org.aksw.qct.sparqltemplate.CountSparql;
import org.aksw.qct.sparqltemplate.ListSparql;
import org.aksw.qct.sparqltemplate.RankingSparql;
import org.aksw.qct.sparqltemplate.XofySparql;

/** Reads a QCT template XML file and returns a set of QCT templates.
 */
public class QctParser
{
	public static void main(String[] args){
		QctTemplate q1 = new QctTemplate();
		//System.out.println(q1.getAll());
		//System.out.println(q1.getDesire());
		
		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");
				
		
		if (q1.getQCT().contains("] =  list")){
			System.out.println("Its a list/give query");
			ListSparql ls1 = new ListSparql(q1); 
			
			}
		
		else if (q1.getQCT().contains("[Concepts] = [")){
			System.out.println("Its a Boolean query");
			BooleanSparql bs1 = new BooleanSparql(q1);
			
			}

		else if (q1.getNLQuery().toLowerCase().contains("how many")){
			System.out.println("Its a count query");
			CountSparql cs1 = new CountSparql(q1);
			}
		
		
		else if (superlativeWordList.matcher(q1.getNLQuery()).find()){
			System.out.println("Its a Ranking query");
			RankingSparql rs1 = new RankingSparql(q1);
				
			}
		else if (q1.getDesire().contains("DataProperty")){
			System.out.println("Its a XofR1Y query");
			XofySparql xs1 = new XofySparql(q1);
			}

		else
			System.out.println("Its a XofY query");
		     XofySparql xs1 = new XofySparql(q1);

	}//end of main




}//end of class




