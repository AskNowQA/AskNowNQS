package org.aksw.asknow.scripts;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.Parser;
import org.aksw.asknow.annotation.QueryAnnotaion;
import org.aksw.asknow.query.*;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class BenchmarkEvalution {

	public static void main(String[] args)
	{
		for(int i=0;i<50;i++)
		{	System.out.println("id: "+(i+1));
			evaluate(i);
			
		}
	}

	//	/** @param rs a one dimensional result set
	//	 * @return a string representation of the result set.*/
	//	static String resultSetString(ResultSet rs)
	//	{
	//		return rs.toString();
	//	}

	static void evaluate(int n)
	{
		// TODO: add hawk autosparql commons qald5 eval code usage
		log.info(query(n).toString());
	}

	static Set<RDFNode> query(int n)
	{
		List<Nqs> templates = Parser.parse();
		Nqs q1 = templates.get(n);
		//System.out.println(q1.getAll());
		//System.out.println(q1.getDesire());
		QueryAnnotaion.annotate(q1);
		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");

		if (q1.qct.contains("] =  list")){
			System.out.println("Its a list/give query");
			return ListQuery.INSTANCE.execute(q1); 
		}

		else if (q1.qct.contains("[Concepts] = [")){
			System.out.println("Its a Boolean query");
			return BooleanQuery.INSTANCE.execute(q1) ;

		}

		else if (q1.nlQuery.toLowerCase().contains("how many")){
			System.out.println("Its a count query");
			return CountQuery.INSTANCE.execute(q1);
		}


		else if (superlativeWordList.matcher(q1.nlQuery).find()){
			System.out.println("Its a Ranking query");
			return RankingQuery.INSTANCE.execute(q1);

		}
		else if (q1.getDesire().contains("DataProperty")){
			System.out.println("Its a XofR1Y query");
			return XofyQuery.INSTANCE.execute(q1);
		}

		else
		{	System.out.println("Its a XofY query");
		return XofyQuery.INSTANCE.execute(q1);
		}
	}

}