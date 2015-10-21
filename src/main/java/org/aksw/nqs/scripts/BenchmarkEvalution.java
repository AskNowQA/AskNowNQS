package org.aksw.nqs.scripts;

import java.util.List;
import java.util.regex.Pattern;
import org.aksw.nqs.Parser;
import org.aksw.nqs.Template;
import org.aksw.nqs.sparqltemplate.BooleanQuery;
import org.aksw.nqs.sparqltemplate.CountQuery;
import org.aksw.nqs.sparqltemplate.ListQuery;
import org.aksw.nqs.sparqltemplate.RankingQuery;
import org.aksw.nqs.sparqltemplate.XofyQuery;



public class BenchmarkEvalution {

	public static void main(String[] args) {
		List<Template> templates = Parser.parse();
		Template q1 = templates.get(0);
		System.out.println(q1.getAll());
		System.out.println(q1.getDesire());
		
		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");
		
				if (q1.qct.contains("] =  list")){
					System.out.println("Its a list/give query");
					ListQuery.INSTANCE.execute(q1); 
				}
		
				else if (q1.qct.contains("[Concepts] = [")){
					System.out.println("Its a Boolean query");
					BooleanQuery.INSTANCE.execute(q1) ;
		
				}
		
				else if (q1.nlQuery.toLowerCase().contains("how many")){
					System.out.println("Its a count query");
					CountQuery.INSTANCE.execute(q1);
				}
		
		
				else if (superlativeWordList.matcher(q1.nlQuery).find()){
					System.out.println("Its a Ranking query");
					RankingQuery rs1 = new RankingQuery(q1);
		
				}
				else if (q1.getDesire().contains("DataProperty")){
					System.out.println("Its a XofR1Y query");
					XofyQuery xs1 = new XofyQuery(q1);
				}
		
				else
					System.out.println("Its a XofY query");
				XofyQuery xs1 = new XofyQuery(q1);
		
			}

}
