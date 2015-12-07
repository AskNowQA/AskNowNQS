package org.aksw.asknow;

import java.util.ArrayList;
//import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class Dictionary {//Who developed Minecraft?//[WH] = Who, [R1] = , [D] = DataProperty (Person), [R2] = developed, [I] = Minecraft//

	public ArrayList<String> getDesire(String desire, String r2){
		ArrayList<String> ListDesire  = new ArrayList<>();
		desire= desire.replaceAll("DataProperty ","");
		desire= desire.replaceAll("","");
		log.debug("ckp1"+desire);

		if (desire.contains("Person")){
			if (r2.contains("developed")){
				ListDesire.add("developer");
			}
			
		}
		
		return ListDesire;
	}
	// public static void main(String[] args){
	//	 System.out.println(getDesire("DataProperty (Person)","developed").toString());
	 //}
	
}