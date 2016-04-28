package org.aksw.asknow.ml;


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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.aksw.asknow.nqs.QueryBuilder;
import org.aksw.asknow.nqs.ner_resolver;
import org.aksw.mlqa.feature.Nqs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WekaWriter {
	private static QueryBuilder qb;
	static ArrayList<String> nertags = new ArrayList<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		qb = new QueryBuilder();
		readQALDfile();
		//System.out.println(getNQS("Who was the first president of independent India?"));
	}
	//read text QALDxml
	//get NQS per query
	//write NQS-QALD.xml
	//feature: Question Type    Answer Resource Type    Wh-type    #Token    Limit (includes order by and offset) 
	//			Comparative    Superlative    Person    Location    Organization    Misc

	static String getNQS(String ques){
		//qb = new QueryBuilder();
		qb.setQuery(ques);
		qb.buildQuery();
		String nqs = qb.getCharacterizedString();
		nertags = ner_resolver.nertag;
		return nqs;
	}

	static void readQALDfile() {


		DocumentBuilderFactory qaldFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder qaldBuilder;

		JSONParser parser = new JSONParser();
		File file = new File("/Users/mohnish/git2/AskNow/src/main/resources/test.arff");
		BufferedWriter bw = null;
		//return questionType(n) +", "+ whType (n)+", "+ tokenCount(n)+", "+ isSuperlative(n)+", "+ isPerson(n) +", "+ 
		//isLocation(n) +", "+ isLocation( n)+", " + isOrganization( n)+", " +isMisc( n);
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("@RELATION asknow"+
					"\n@ATTRIBUTE queryId  NUMERIC"+
					// "\n@ATTRIBUTE cluster  {1,2,3,4,5}"+
					"\n@ATTRIBUTE cluster  {TagMe}"+
					 "\n@ATTRIBUTE questionType {LIST, BOOLEAN,NUMBER,RESOURCE}"+
				     "\n@ATTRIBUTE whType {ASK, COMMAND,LIST,which,what,when,who,how,howmany,howmuch,that}"+
					"\n@ATTRIBUTE tokenCount NUMERIC"+
					"\n@ATTRIBUTE isPerson {true,false}"+
				     "\n@ATTRIBUTE isSuperlative {true,false}"+
				     "\n@ATTRIBUTE isLocation {true,false}"+
				     "\n@ATTRIBUTE isOrganization {true,false}"+
				     "\n@ATTRIBUTE isMisc {true,false}"+
				     "\n@DATA");
				     
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			qaldBuilder = qaldFactory.newDocumentBuilder();
			Document doc = qaldBuilder.newDocument();
			Element mainRootElement = doc.createElementNS("http://github.com/AKSW/AskNow", "NQSforQALD");
			doc.appendChild(mainRootElement);

			Object obj = parser.parse(new FileReader("/Users/mohnish/git2/AskNow/src/main/resources/qald6test.json"));

			JSONObject jsonObject = (JSONObject) obj;
			//JSONArray questions = (JSONArray) jsonObject.get("questions");

			//String output = null;
			int counter =0;
			JSONArray quald = (JSONArray) jsonObject.get("questions");
			Iterator<JSONObject> questions = quald.iterator();
			String sparql;


			while (questions.hasNext()) {
				counter++;
				JSONObject quesObj = questions.next();
				Object ids = quesObj.get("id");
				if(ids.toString().contains("150")||counter== 102||counter == 114||counter == 70)
						//||counter == 3||counter == 23||counter == 44||counter == 43||counter == 55||counter == 61
						//||counter == 195||counter == 196 ||counter == 197||counter == 198||counter ==199)
				{continue;
				}
				
				String arff;	
				String ques = null;

				System.out.println("Id = "+ids.toString() +":: Counter = "+counter );

				JSONArray alllang = (JSONArray) quesObj.get("question");
				Iterator<JSONObject> onelang = alllang.iterator();

				while (onelang.hasNext()) {
					JSONObject engques = onelang.next();
					ques = (String) engques.get("string");

					break;
				}
				/*
				JSONObject query = (JSONObject) quesObj.get("query");
			
				sparql = (String) query.get("sparql");
				if(sparql==""){
					continue;}
				System.out.println(sparql);
				
				try{
				sparql=sparql.replaceAll("<", "#OPEN ");
				sparql=sparql.replaceAll(">", " #CLOSE");
				}
				catch (NullPointerException ex)
				{
					sparql="#OPEN USER need to add SPARQL #CLOSE";
				}
				*/

				NqsInstance n1 = new NqsInstance(ques,getNQS(ques),ids.toString(),nertags.toString());

				
				arff = "\n"+ids.toString()+","
				//+SparqlFeature.getFeature1(sparql)
				+"TagMe "
				+","+NLqueryFeature.feature1(n1).replace(" ","");
				
				
				System.out.println(arff);
				try {
					bw.write(arff);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//mainRootElement.appendChild(getNQSxml(doc, ids.toString() , ques, nqs,nertags.toString(),
				//SparqlFeature.getAllFeature(nqs),NLqueryFeature.feature(n1),sparql));

				//when Sparql is unavailable : Testing Data 
				//mainRootElement.appendChild(getNQSxml(doc, ids.toString() , ques, nqs,nertags.toString(),
				//"testdata",NLqueryFeature.feature(n1),sparql));


				//System.out.println(output);
				}
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
		
		try {
				bw.close();
				System.out.println("features saved");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//print array list

		
	}

		private static Node getNQSxml(Document doc, String id, String ques, String nqs,String ner,String sparqlfeature,String nlqueryfeature,String SPARQL) {
			Element nqsxml = doc.createElement("QALDquestions");
			nqsxml.setAttribute("id", id);
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "Ques", ques));
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NQS", nqs));
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NER", ner));
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "SPARQLFeature", sparqlfeature));
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NLQueryFeature", nlqueryfeature));
			nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "SPARQL", SPARQL));
			return nqsxml;
		}

		// utility method to create text node
		private static Node getNQSxmlElements(Document doc, Element element, String name, String value) {
			Element node = doc.createElement(name);
			node.appendChild(doc.createTextNode(value));
			return node;
		}
	}
