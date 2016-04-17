package org.aksw.asknow.util;
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
}
