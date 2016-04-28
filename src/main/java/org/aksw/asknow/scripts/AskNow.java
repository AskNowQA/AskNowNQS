package org.aksw.asknow.scripts;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

import org.aksw.asknow.Nqs;
import org.aksw.asknow.annotation.QueryAnnotaion;
import org.aksw.asknow.nqs.QueryBuilder;
import org.aksw.asknow.nqs.ner_resolver;
import org.aksw.asknow.query.BooleanQuery;
import org.aksw.asknow.query.CountQuery;
import org.aksw.asknow.query.ListQuery;
import org.aksw.asknow.query.RankingQuery;
import org.aksw.asknow.query.XofyQuery;
import org.apache.jena.rdf.model.RDFNode;

public class AskNow {
	private static QueryBuilder qb;
	static ArrayList<String> nertags = new ArrayList<String>();
	public static void main(String[] args) {
		
		qb = new QueryBuilder();
		System.out.println(giveanswer("What is the capital of India ?").toString());
		//readQALDfile();
		
	}
	//read text QALDxml
	//get NQS per query
	//write NQS-QALD.xml
	//feature: Question Type    Answer Resource Type    Wh-type    #Token    Limit (includes order by and offset) 
	//			Comparative    Superlative    Person    Location    Organization    Misc
	
	

	static String getNQS(String ques){
		//qb = new QueryBuilder();
		qb.setQuery(ques);
		qb.buildQuery();
		String nqs = qb.getCharacterizedString();
		nertags = ner_resolver.nertag;
		return nqs;
	}
	
	private static Set<RDFNode> giveanswer(String question) {
		// TODO Auto-generated method stub
		Nqs q1 = new Nqs(question,getNQS(question),"","");
		
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