package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.spotlight;

public class checkSpotLight {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		spotlight spot = new spotlight();
		JSONArray DBpEquivalent = spot.getDBLookup("Pluto is a good boy", "0.0");
		System.out.println(DBpEquivalent.get(0));	
		 JSONObject obj2 = (JSONObject) DBpEquivalent.get(0);
//		 JSONObject obj3 = (JSONObject) DBpEquivalent.get(1);
//		 JSONObject obj4 = (JSONObject) DBpEquivalent.get(4);
//
		 System.out.println(obj2.get("uri"));
//		 System.out.println(obj3.get("name"));
//		 System.out.println(obj4.get("name"));
	}

}
