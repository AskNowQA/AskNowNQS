package org.aksw.qct;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QctTemplate {
	String nlQuery ="";
	String keyword ="";
	String qct ="";
	String queryid ="";
	public CharSequence getDesireBrackets;

	public static void main(String[] args) {

		QctTemplate q1 = new QctTemplate();
		System.out.println(q1.getAll());

	}






	QctTemplate(){

		try {

			File fXmlFile = new File("C:/Users/Mohnish/Dropbox/QA/asknow/src/main/java/org/aksw/qct/QctPool.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Query");

			//System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					queryid = eElement.getAttribute("id");
					nlQuery = eElement.getElementsByTagName("NLquery").item(0).getTextContent();
					keyword = eElement.getElementsByTagName("Keywords").item(0).getTextContent();
					qct = eElement.getElementsByTagName("QCT").item(0).getTextContent();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//-----------------------------------------------------------------------//
	public String getConcepts(){
		String temp = qct;

		temp = temp.substring(qct.indexOf("[Concepts] = [")+14);//, qct.indexOf("]"));
	
		temp= temp.substring(0, temp.indexOf("]"));
		System.out.println(temp);
	
		return temp;
				
	}
	public String getRoles(){
		String temp = qct;
		temp = temp.substring(qct.indexOf("[Roles] = [")+11);//, qct.indexOf("]"));
		temp= temp.substring(0, temp.indexOf("]"));
		System.out.println(temp);
		return temp;
		
	}
//-----------------------------------------------------------------------//
	public String getNLQuery(){
		return nlQuery;
	}

	public String getKeyword(){
		return keyword;
	}

	public String getQCT(){
		return qct;
	}

	public String getDesire(){
		String temp;
		temp = qct.substring(qct.indexOf("[D] =")+5);
		temp = temp.substring(0, temp.indexOf(","));
		return temp.trim();
	}
	public String getRelation2(){
		String temp;
		temp = qct.substring(qct.indexOf("[R2] =")+6);
		temp = temp.substring(0, temp.indexOf(","));
		return temp.trim();
	}
	public String getRelation1(){
		String temp;
		temp = qct.substring(qct.indexOf("[R1] =")+6);
		temp = temp.substring(0, temp.indexOf(","));
		return temp.trim();
	}
	public String getInput(){
		String temp;
		temp = qct.substring(qct.indexOf("[I] =")+5);
		temp = temp.substring(0, temp.indexOf(","));
		return temp.trim();
	}

	public String getAll(){
		return nlQuery + "\n" + keyword + "\n" + qct;
	}

	public String getAllInputs(){
		String tempinput="";
		if(qct.contains("[I]"))
			return getInput();
		else
			{tempinput = qct.substring(qct.indexOf("[I1_1]"),qct.indexOf ("["));
			tempinput =tempinput +", "+ qct.substring(qct.indexOf("[I1_2]"),qct.indexOf ("["));
			tempinput =tempinput +", "+ qct.substring(qct.indexOf("[I1_3]"),qct.indexOf ("["));
			return tempinput;
			}
	}
	public String getDesireBrackets() {
		//[D] = count(languages)
		String s1;
		s1 = getDesire();
		int i , j;
		i = s1.lastIndexOf("(")+1;
		j = s1.indexOf(')', i);
		return s1.substring(i, j);
	}

	







}

