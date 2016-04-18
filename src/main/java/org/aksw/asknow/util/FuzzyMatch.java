package org.aksw.asknow.util;
import java.util.Set;

/*
 * 
 */
import org.apache.commons.lang3.StringUtils;

public class FuzzyMatch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(matchvalue("developers","developer"));
		
	}
    
	
	static int matchvalue(CharSequence s1, CharSequence s2){
    	return StringUtils.getLevenshteinDistance(s1,s2);
    	
    }//getLevenshteinDistance


	public static String getmatch(String entity, Set<String> setofproperties) {
		// TODO Auto-generated method stub
		int leastvalue = 100,value=0;
		String uri="",prop="";//uri of least value 
		for (String property : setofproperties) {
			prop =property.substring(property.lastIndexOf("/"));
			value=matchvalue(entity,prop);
			if(value<leastvalue){
				leastvalue =value;
				uri =property;
			}		
		}
		System.out.println("<"+uri+">");
		return "<"+uri+">";
	}


	public static String getmatchfrom(String relation, String desire, Set<String> setofproperties) {
		int leastvalue = 100,value=0;
		String uri="",prop="";//uri of least value 
		
		for (String property : setofproperties) {
			prop =property.substring(property.lastIndexOf("/"));
			value=matchvalue(relation,prop);
			if(value<leastvalue){
				leastvalue =value;
				uri =property;
			}	
			value=matchvalue(desire,prop);
			if(value<leastvalue){
				leastvalue =value;
				uri =property;
			}	
		}
		System.out.println("<"+uri+">");
		return "<"+uri+">";
	}
}
