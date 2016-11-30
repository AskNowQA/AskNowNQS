package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.spotlight;

public class checkSpotLight {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		spotlight spot = new spotlight();
		JSONArray DBpEquivalent = spot.getDBLookup("United States Of America and Berlin and Barack Obama", "0.0");
		System.out.println(DBpEquivalent.get(0));	
		 JSONObject obj2 = (JSONObject) DBpEquivalent.get(0);
		 System.out.println(obj2.get("name"));
	}

}
