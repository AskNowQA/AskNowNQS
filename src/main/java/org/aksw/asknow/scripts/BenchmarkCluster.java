package org.aksw.asknow.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//import java.util.regex.Pattern;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.Parser;
import org.aksw.asknow.annotation.QueryAnnotaion;
import org.aksw.asknow.query.*;
import org.apache.jena.rdf.model.RDFNode;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class BenchmarkCluster {
	static ArrayList<String> c0 = new ArrayList<String>();
	static ArrayList<String> c1 = new ArrayList<String>();
	static ArrayList<String> c2 = new ArrayList<String>();
	static ArrayList<String> c3 = new ArrayList<String>();
	public static void main(String[] args)
	{
		for(int i=0;i<90;i++)
		{	System.out.println("id: "+(i+1));
			//evaluate(i);
		     query(i);
			
		}
		System.out.println("Cluster0: "+c0.size()+c0);
		System.out.println("Cluster0: "+c1.size()+c1);
		System.out.println("Cluster0: "+c2.size()+c2);
		System.out.println("Cluster0: "+c3.size()+c3);
		
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
		//log.info(query(n).toString());
	}

	//static Set<RDFNode> query(int n)
	static void query(int n)
	{
		List<Nqs> templates = Parser.parse();
		Nqs q1 = templates.get(n);
		//QueryAnnotaion.annotate(q1);
				//Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");

		if (q1.sparqlcluster.contains("0")){
			System.out.println("Cluster 0");
			c0.add(q1.nlQuery);
			return; 
		}

		else if (q1.sparqlcluster.contains("1")){
			System.out.println("Cluster 1");
			c1.add(q1.nlQuery);
			return;
		}

		else if (q1.sparqlcluster.contains("2")){
			System.out.println("ICluster 2");
			c2.add(q1.nlQuery);
			return;
		}

		else { //q1.sparqlcluster == "3"
			System.out.println("Cluster 3");
			c3.add(q1.nlQuery);
			return;
		}
	}

}