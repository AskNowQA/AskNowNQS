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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class AllFeatureParser {
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
			Iterator<JSONObject> questions = quald.iterator();String sparql;
			while (questions.hasNext()) {
				counter++;
				JSONObject quesObj = questions.next();
				Object ids = quesObj.get("id");
				if(ids.toString().contains("150")||counter== 102||counter == 114||counter==70)
					{continue;
					}
					
				String ques = null;
				
				System.out.println("Id = "+ids.toString() +":: Counter = "+counter );
				
				JSONArray alllang = (JSONArray) quesObj.get("question");
				Iterator<JSONObject> onelang = alllang.iterator();
				while (onelang.hasNext()) {
					JSONObject engques = onelang.next();
					ques = (String) engques.get("string");
					
					break;
				}
				JSONObject query = (JSONObject) quesObj.get("query");
				//sparql = (String) query.get("sparql");

				//output=output+"\n"+id+"\t"+ques +"\t"+getNQS(ques);
				/*<Query id="1">
						<NLquery>Give me all ESA astronauts.</NLquery>
						<Keywords>ESA astronauts</Keywords>
						<QCT>[WH] = What, [R1] = is, [D] =  list, [R2] = of, [I] =  ESA astronauts</QCT>
						</Query>*/


				//= output+"\n <Ques id>"+ids+"\t"+ques +"\t"+getNQS(ques);
				NqsInstance n1 = new NqsInstance(ques,getNQS(ques),ids.toString(),nertags.toString());
				
				String nqs = getNQS(ques);
				//when Sparql is available : Training Data
				//mainRootElement.appendChild(getNQSxml(doc, ids.toString() , ques, nqs,nertags.toString(),
				//		SparqlFeature.getAllFeature(nqs),NLqueryFeature.feature(n1)));
				
				//when Sparql is unavailable : Testing Data 
				mainRootElement.appendChild(getNQSxml(doc, ids.toString() , ques, nqs,nertags.toString(),
						"testdata",NLqueryFeature.feature(n1)));

			}
			//System.out.println(output);
			try{
				//out.println( output);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new 
						File("/Users/mohnish/git2/AskNow/src/main/resources/qald6testdata-nqs.xml"));
				//StreamResult console = new StreamResult(System.out);
				transformer.transform(source, result);
				//out.println( console);

				System.out.println("\nXML DOM Created Successfully..");
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	}


	private static Node getNQSxml(Document doc, String id, String ques, String nqs,String ner,String sparqlfeature,String nlqueryfeature) {
		Element nqsxml = doc.createElement("QALDquestions");
		nqsxml.setAttribute("id", id);
		nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "Ques", ques));
		nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NQS", nqs));
		nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NER", ner));
		nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "SPARQLFeature", sparqlfeature));
		nqsxml.appendChild(getNQSxmlElements(doc, nqsxml, "NLQueryFeature", nlqueryfeature));
		return nqsxml;
	}

	// utility method to create text node
	private static Node getNQSxmlElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}
