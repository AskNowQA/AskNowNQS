package notUsed;



/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Dictionary {

	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		Map<String, String> dictionary = new HashMap<String, String>();
		List<String> valSetOne = new ArrayList<String>();
        valSetOne.add("Apple");
         valSetOne.add("value2");
		dictionary.put("key", valSetOne.toString());
		
		String value = dictionary.get("key");
		System.out.println(value);
	}
}