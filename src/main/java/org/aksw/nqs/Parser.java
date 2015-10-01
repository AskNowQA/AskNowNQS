package org.aksw.nqs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.aksw.nqs.util.XmlUtil;
import org.w3c.dom.*;
import lombok.SneakyThrows;

/** Reads a QCT template XML file and returns a set of QCT templates. */
public class Parser
{
	public static List<Template> parse()
	{
		// TODO: change to inputstream
		return parse(()->Parser.class.getClassLoader().getResourceAsStream("benchmark.xml"));
	}

	@SneakyThrows
	// TODO: detailed exception handling later
	public static List<Template> parse(Supplier<InputStream> in)
	{
		List<Template> templates = new ArrayList<>();
		//			File fXmlFile = new File("C:/Users/Mohnish/Dropbox/QA/asknow/src/main/java/org/aksw/qct/QctPool.xml");
		Document doc;
		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in.get());
		doc.getDocumentElement().normalize();
		if(!XmlUtil.validateAgainstXSD(in.get(), Parser.class.getClassLoader().getResourceAsStream("benchmark.xsd")))
		{throw new IllegalArgumentException("QCT template file not valid against the XSD.");}

		NodeList nList = doc.getElementsByTagName("Query");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String queryid = eElement.getAttribute("id");
				String nlQuery = eElement.getElementsByTagName("NLquery").item(0).getTextContent();
				String qct = eElement.getElementsByTagName("QCT").item(0).getTextContent();
				templates.add(new Template(nlQuery,qct,queryid));
			}
		}
		return templates;
	}


	//	public static void main(String[] args){
	//		QctTemplate q1 = new QctTemplate();
	//		//System.out.println(q1.getAll());
	//		//System.out.println(q1.getDesire());
	//
	//		Pattern superlativeWordList = Pattern.compile("highest|lowest|deepest|fastest|longest|largest|youngest|oldest|heaviest|lightest|tallest|shortest");
	//
	//
	//		if (q1.getQCT().contains("] =  list")){
	//			System.out.println("Its a list/give query");
	//			ListSparql ls1 = new ListSparql(q1); 
	//
	//		}
	//
	//		else if (q1.getQCT().contains("[Concepts] = [")){
	//			System.out.println("Its a Boolean query");
	//			BooleanSparql bs1 = new BooleanSparql(q1);
	//
	//		}
	//
	//		else if (q1.nlQuery.toLowerCase().contains("how many")){
	//			System.out.println("Its a count query");
	//			CountSparql cs1 = new CountSparql(q1);
	//		}
	//
	//
	//		else if (superlativeWordList.matcher(q1.nlQuery).find()){
	//			System.out.println("Its a Ranking query");
	//			RankingSparql rs1 = new RankingSparql(q1);
	//
	//		}
	//		else if (q1.getDesire().contains("DataProperty")){
	//			System.out.println("Its a XofR1Y query");
	//			XofySparql xs1 = new XofySparql(q1);
	//		}
	//
	//		else
	//			System.out.println("Its a XofY query");
	//		XofySparql xs1 = new XofySparql(q1);
	//
	//	}

}