package org.aksw.asknow.scripts;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.Parser;
import org.aksw.asknow.query.*;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;
import org.aksw.asknow.annotation.*;
@Slf4j public class BenchmarkAnnotation {

	public static void main(String[] args)
	{
		for(int i=0;i<100;i++)
		{	System.out.println("id: "+(i+1));
		    if (i==17||i==19||i==31||i==50||i==67||i==69||i==83)
		    	continue;
			evaluateAnnotation(i);
			
		}
	}

	//	/** @param rs a one dimensional result set
	//	 * @return a string representation of the result set.*/
	//	static String resultSetString(ResultSet rs)
	//	{
	//		return rs.toString();
	//	}

	static void evaluateAnnotation(int n)
	{
		// TODO: add hawk autosparql commons qald5 eval code usage
		log.info(query(n).toString());
		
	}

	static String query(int n)
	{
		List<Nqs> templates = Parser.parse();
		Nqs q1 = templates.get(n);
		QueryAnnotaion.annotate(q1);
		System.out.println(q1.nlQuery + "\n"+q1.Resource);
		System.out.println(q1.Predicate);
		System.out.println();
		return "";
	}

}