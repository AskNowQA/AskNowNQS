package org.aksw.asknow.util;
/*
 * Read QALD query
 * Annotate query
 * Write the result
 */
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EntityAnnotate {
	public static void main(String args[]) {
	//	System.out.println(Fox.annotate("Katie Holmes got divorced from Tom Cruise in Germany "));
		//System.out.println("Result is "+Spotlight.getDBpLookup("Katie Holmes got divorced from Tom Cruise in Germany ")+" :");
		//System.out.println("Result is "+Spotlight.getDBpLookup(" got divorced from  in many ")+" :");
	
		readQALDfile();
		
	}
	
	public static String annotation(String entity){
		
		String uri="";
		uri =Spotlight.getDBpLookup(entity);
		if (uri == "")
			uri = Fox.annotate(entity);
		return uri;
		
	}
	static void readQALDfile() {


		DocumentBuilderFactory qaldFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder qaldBuilder;

		JSONParser parser = new JSONParser();
		String anno="";
		String unanno="";
		int failcount=0;
		try {
			qaldBuilder = qaldFactory.newDocumentBuilder();
			Document doc = qaldBuilder.newDocument();
			Element mainRootElement = doc.createElementNS("http://github.com/AKSW/AskNow", "NQSforQALD");
			doc.appendChild(mainRootElement);

			Object obj = parser.parse(new FileReader("/Users/mohnish/git2/sandbox/src/main/resources/qald6test.json"));
			
			JSONObject jsonObject = (JSONObject) obj;
			//JSONArray questions = (JSONArray) jsonObject.get("questions");

			//String output = null;

			JSONArray quald = (JSONArray) jsonObject.get("questions");
			Iterator<JSONObject> questions = quald.iterator();
			while (questions.hasNext()) {
				JSONObject quesObj = questions.next();
				Object ids = quesObj.get("id");
				//int idi = Integer.parseInt(ids);
				//if (idi<=300){
				//	continue;
				//}
				String ques = null;
				//ystem.out.println(id );
				JSONArray alllang = (JSONArray) quesObj.get("question");
				Iterator<JSONObject> onelang = alllang.iterator();
				while (onelang.hasNext()) {
					JSONObject engques = onelang.next();
					ques = (String) engques.get("string");
					break;
				}
					
				anno = Fox.annotate(ques);
				if (anno==""){
					anno =Spotlight.getDBpLookup(ques);
				}
				//mainRootElement.appendChild(getNQSxml(doc, ids.toString() , ques, getNQS(ques),nertags.toString()));
				if (anno!="")
					System.out.println("Id is "+ ids.toString() +"  " + anno );
				else
					{unanno=unanno+ ids.toString()+"  " +ques+"\n";
					failcount ++;
					}

			}
			System.out.println("Fail count for fox is"+ failcount +"\n" + unanno);
			
			//System.out.println(output);
			//try{
				//out.println( output);
				//Transformer transformer = TransformerFactory.newInstance().newTransformer();
				//transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
				//DOMSource source = new DOMSource(doc);
				//StreamResult result = new StreamResult(new 
						//File("/Users/mohnish/git2/AskNow/src/main/resources/qald6test-nqs.xml"));
				//StreamResult console = new StreamResult(System.out);
				//transformer.transform(source, result);
				//out.println( console);

				//System.out.println("\nXML DOM Created Successfully..");
			//} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//} catch (TransformerException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}



}
