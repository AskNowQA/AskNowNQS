package org.aksw.asknow.ml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feature {

	//feature1: Input: SPARQL query 
	//			Output: Number of triples in SPARQL	
	static int getFeature1(String squery){
		int triplecount=0;
        Pattern pattern = Pattern.compile(" . ");
        Matcher  matcher = pattern.matcher(squery);
        while (matcher.find())
        	triplecount++;
        
		return triplecount;
	}
	
	//feature2: Input: SPARQL query 
	//			Output: query type based on keyword{COUNT=1, ORDER=2, ASK=3, NONE=4}
	static int getFeature2(String squery){
		if (squery.contains("COUNT"))
			return 1;
		else if (squery.contains("ORDER"))
			return 2;
		else if (squery.contains("ASK"))
			return 3;
		else
			return 4;
	}
}
