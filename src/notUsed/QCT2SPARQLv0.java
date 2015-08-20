package notUsed;



public class QCT2SPARQLv0 {
	
	public static void main(String argv[]){
		
		String Wh = "What";
		String R1 = "is";
		String Desire = "capital";
		String R2 = "for";
		String Input = "India";
		System.out.println(Wh+R1+Desire+R2+Input);
		
		String SPARQL = "PREFIX dbo: <http://dbpedia.org/ontology/> "
                         +"PREFIX res: <http://dbpedia.org/resource/> "
                         +"SELECT DISTINCT ?uri "
                         +"WHERE { "
                         +" res:"+Input +" dbo:"+Desire+" ?uri . "
                         +"} ";
		
		System.out.println(SPARQL);
		
	}//end of main()
}//end of class
