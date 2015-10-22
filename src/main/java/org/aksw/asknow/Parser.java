package org.aksw.asknow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;

import org.aksw.asknow.util.XmlUtil;
import org.w3c.dom.*;
import lombok.SneakyThrows;

/** Reads a QCT template XML file and returns a set of QCT templates. */
public class Parser
{
	/**
	 * Parses the default benchmark (QALD-5 NQS)
	 * @return List of NQS
	 */
	public static List<Nqs> parse()
	{
		// TODO: change to inputstream
		return parse(()->Parser.class.getClassLoader().getResourceAsStream("benchmark.xml"));
	}

	@SneakyThrows
	// TODO: detailed exception handling later
	public static List<Nqs> parse(Supplier<InputStream> in)
	{
		List<Nqs> templates = new ArrayList<>();
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
				templates.add(new Nqs(nlQuery,qct,queryid));
			}
		}
		return templates;
	}


	//	public static void main(String[] args){
	//		

}