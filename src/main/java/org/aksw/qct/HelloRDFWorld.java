package org.aksw.qct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class HelloRDFWorld {

public static void main(String[] args) {
	ArrayList<String> ResourceResults = new ArrayList<String>();//stores all properties from dbp related to QCT's INPUT
	ArrayList<String> PossibleMatch = new ArrayList<String>();//selected property close to DESIRE
	String Wh = "What";
	String R1 = "is";
	String Desire = "writer";
	String R2 = "for";
	String Input = "The_Klingon_Hamlet";
	System.out.println(Wh+" "+R1+" "+Desire+" "+R2+" "+Input);
	
	  
	   
	String service = "http://dbpedia.org/sparql";
    String query = "PREFIX dbo: <http://dbpedia.org/ontology/>" 
    			  +"PREFIX yago: <http://dbpedia.org/class/yago/> "
    			  + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
    			  + "PREFIX res: <http://dbpedia.org/resource/> "
    			  + "SELECT DISTINCT ?property "
    			  		+ "WHERE { "
    			  				+"res:"+Input+ "?property ?value."
    			  				+"} ";
    
    QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
    //System.out.println(qe.toString().length());
    try {
        ResultSet results = qe.execSelect();

        for (; results.hasNext();) {

            QuerySolution sol = (QuerySolution) results.next();

            //System.out.println(sol.get("?property"));
            ResourceResults.add(sol.get("?property").toString());

        							}

    	}catch(Exception e){

        e.printStackTrace();
    						}
    		finally {
    			qe.close();
    				}
       
       
   // System.out.println(ResourceResults.toString());//print all properties
    
    			for (String string : ResourceResults) {
    					if(string.toLowerCase().contains(Desire.toLowerCase())){
    						PossibleMatch.add(string);
    												}
    										}
    			
    			if (PossibleMatch.toString().length()==2)
    				{WordNetSynonyms instance = new WordNetSynonyms();
    				Set<String> SynonymsWord1 = new HashSet<String>();
    				SynonymsWord1 = instance.getSynonyms(Desire);
    				System.out.println(SynonymsWord1);
    				
    				// create an iterator
    				 //  ArrayList<String> newset;
					Iterator<String> iterator =  SynonymsWord1.iterator();
    				   
    				   // check values
    				   while (iterator.hasNext()){
    					   	Desire=iterator.next();
    						for (String string : ResourceResults) {
        					if(string.toLowerCase().contains(Desire.toLowerCase())){
        						PossibleMatch.add(string);
        												}
        										}
    					}
    				
    				
    				}
    			//else
    				System.out.println(PossibleMatch.toString());
    	
    				//selection from PossibleMatch and generate SPARQL
    				int count =PossibleMatch.size();
    				String varible1="";//dbo:capital
    				String service1="http://dbpedia.org/sparql";
    		        String query1="PREFIX dbo: <http://dbpedia.org/ontology/>" 
    		    			  +"PREFIX yago: <http://dbpedia.org/class/yago/> "
    		    			  + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
    		    			  + "PREFIX res: <http://dbpedia.org/resource/> "
    		    			  + "SELECT DISTINCT ?property "
    		    			  		+ "WHERE { "
    		    			  				+"res:"+Input+" "+varible1+" ?property."
    		    			  				+"} ";
    		        int i=0; String part1="", part2="";
    				for(String temp="";count>0;count--)
    				{  			
    					temp=PossibleMatch.get(count-1).toString();
    					//System.out.println(temp.toString());
    					ArrayList<String> aList= new ArrayList<String>(Arrays.asList(temp.split("/")));
    					i=aList.size();
    					//System.out.println(aList.get(i-2).toString()+count);
    					if (aList.get(i-2).equals("ontology"))
    							part1="dbo";
    					else if (aList.get(i-2).equals("property"))
    							part1="dbp";
    					part2=aList.get(i-1).toString();
    					varible1=part1+":"+part2;
    					
    					query1= "PREFIX dbo: <http://dbpedia.org/ontology/>" 
      		    			  + "PREFIX yago: <http://dbpedia.org/class/yago/> "
    						  + "PREFIX dbp: <http://dbpedia.org/property/> "
      		    			  + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
      		    			  + "PREFIX res: <http://dbpedia.org/resource/> "
      		    			  + "SELECT DISTINCT ?property "
      		    			  		+ "WHERE { "
      		    			  				+"res:"+Input+" "+part1+":"+part2+" ?property."
      		    			  				+"} ";
    					//System.out.println(query1);
    					
    					QueryExecution e=QueryExecutionFactory.sparqlService(service1, query1);
    					ResultSet rs=e.execSelect();
    					while (rs.hasNext()) {
    								QuerySolution qs=rs.nextSolution();
    								System.out.println(qs);
  		        							}
    				}		
} // end method
} // end class