package org.aksw.qct;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class LookDBpedia {

public static void main(String[] args) {
	ArrayList<String> ResourceResults = new ArrayList<String>();//stores all properties from dbp related to QCT's INPUT
	ArrayList<String> PossibleMatch = new ArrayList<String>();//selected property close to DESIRE
	String wh, r1 = null, desire = null, r2 = null, input = null;
	
	String QCTquery = "[WH] = Who, [R1] = , [D] = DataProperty (Person), [R2] = developed, [I] = Minecraft,";
	QCTquery = QCTquery.replace("[", "<");
	QCTquery = QCTquery.replace("] = ", ">");
	QCTquery = QCTquery.replace(",", "<,>");
	System.out.println(QCTquery);
	
	wh = findTag("<WH>(.+?)<,>",QCTquery);
	r1 = findTag("<R1>(.+?)<,>",QCTquery);
	desire = findTag("<D>(.+?)<,>",QCTquery);
	r2 = findTag("<R2>(.+?)<,>",QCTquery);
	input = findTag("<I>(.+?)<,>",QCTquery);
	System.out.println(wh+ r1+ desire+ r2+ input);
	
	input =CallSpotlight.getDBpLookup(input);
	System.out.println("checklookdbpedia");
	
	
	System.out.println(wh+" "+r1+" "+desire+" "+r2+" "+input);
	
	
	ResourceResults = getDbProperty(input);//ArrayList<String> ResourceResults 
	
   // System.out.println(ResourceResults.toString());//print all properties
    			
			if(desire.contains("DataProperty (")){
				PossibleMatch = getDataProperty(desire,r2,ResourceResults);
				
			}
			else {
	
    			for (String string : ResourceResults) {
    					if(string.toLowerCase().contains(desire.toLowerCase())){
    						PossibleMatch.add(string);
    												}
    										}
    			
    			if (PossibleMatch.toString().length()==2)
    				{WordNetSynonyms instance = new WordNetSynonyms();
    				Set<String> SynonymsWord1 = new HashSet<String>();
    				SynonymsWord1 = instance.getSynonyms(desire);
    				System.out.println(SynonymsWord1);
    				
    				// create an iterator
    				 //  ArrayList<String> newset;
					Iterator<String> iterator =  SynonymsWord1.iterator();
    				   
    				   // check values
    				   while (iterator.hasNext()){
    					   	desire=iterator.next();
    						for (String string : ResourceResults) {
    							
    							if(string.toLowerCase().contains(desire.toLowerCase())){
    								PossibleMatch.add(string);
        												}
        										}
    					}
    				
    				
    				}
			}
    			//else
    				System.out.println("chekpoin1: "+PossibleMatch.toString());
    	
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
    		    			  				+"res:"+input+" "+varible1+" ?property."
    		    			  				+"} ";
    		        
    		       //System.out.println("chekpoin: "+query1);
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
      		    			  				+"res:"+input+" "+part1+":"+part2+" ?property."
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




//-------------------------------------------------//


private static ArrayList<String> getDataProperty(String desire, String r2, ArrayList<String> resourceResults) {
	// TODO Auto-generated method stub
	ArrayList<String> PossibleMatch  = new ArrayList<String>();
	ArrayList<String> DesireList = new ArrayList<String>();
		
	
	Dictionary1 Dict = new Dictionary1();
	DesireList = Dict.getDesire(desire, r2);
	System.out.println("ckp2");
	for (String DesireListMember : DesireList ){
		System.out.println("ckp3");
		for (String string : resourceResults) {
		if(string.toLowerCase().contains(DesireListMember.toLowerCase())){
			PossibleMatch.add(string);
									}
							}
	}
	
	
	
	return PossibleMatch;
}







//-----------------------------------------------//

public static ArrayList<String> getDbProperty(String input){
	ArrayList<String> ResourceResults = new ArrayList<String>();//stores all properties from dbp related to QCT's INPUT
	
	//dbpedia spotlight
	
	
		
	String service = "http://dbpedia.org/sparql";
    String query = "PREFIX dbo: <http://dbpedia.org/ontology/>" 
    			  +"PREFIX yago: <http://dbpedia.org/class/yago/> "
    			  + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
    			  + "PREFIX res: <http://dbpedia.org/resource/> "
    			  + "SELECT DISTINCT ?property "
    			  		+ "WHERE { "
    			  				+"res:"+input+ "?property ?value."
    			  				+"} ";
    System.out.println(query);
    QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
    System.out.println(qe.toString().length());
    try {
        ResultSet results = qe.execSelect();

        for (; results.hasNext();) {
            QuerySolution sol = (QuerySolution) results.next();
            //System.out.println(sol.get("?property"));
            ResourceResults.add(sol.get("?property").toString());
        	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    						}
    	finally {
    			qe.close();
    				}
 
   // System.out.println(ResourceResults.toString());//print all properties
	return ResourceResults;
	}





	public static String getEntity (String uriStirng){
		URI uri = URI.create(uriStirng);
		String path = uri.getPath();
		path = path.substring(path.lastIndexOf('/')+1);
			return path;
		}

	public static String findTag (String pattern1, String statement ){
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher matcher = pattern.matcher(statement);
		matcher.find();
		return matcher.group(1);
	}

} // end class



