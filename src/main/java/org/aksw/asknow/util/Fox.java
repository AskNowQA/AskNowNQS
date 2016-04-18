package org.aksw.asknow.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

public class Fox {
	static Logger log = LoggerFactory.getLogger(Fox.class);

	private String requestURL = "http://139.18.2.164:4444/api";
	private String outputFormat = "N-Triples";
	private String taskType = "NER";
	private String inputType = "text";

	public static void main(String args[]) {
		Fox fox = new Fox();

		//String sentence = "what is the capital of India.";
		String sentence = "Which actors play in The Big Bang Theory";
		Map<String, List<Entity>> list;
		try {
			list = fox.getEntities(sentence);

			for (String key : list.keySet()) {
				System.out.println(key);
				for (Entity entity : list.get(key)) {
					System.out.println("\t" + entity.label + " ->" + entity.type);
					for (String r : entity.posTypesAndCategories) {
						System.out.println("\t\tpos: " + r);
					}
					for (String r : entity.uris) {
						System.out.println("\t\turi: " + r);
					}
				}
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String annotate(String sentence) {
		Fox fox = new Fox();
		Map<String, List<Entity>> list;
		String annotation="";
		try {
			
			list = fox.getEntities(sentence);
			for (String key : list.keySet()) {
				//System.out.println(key);
				for (Entity entity : list.get(key)) {
					annotation = annotation + entity.label;
					//System.out.println("\t" + entity.label + " ->" + entity.type);
					//for (String r : entity.posTypesAndCategories) {
					//	System.out.println("\t\tpos: " + r);
					//}
					for (String r : entity.uris) {
						annotation = annotation + " ;; "+ r;
						//System.out.println("\t\turi: " + r);
					}
					//annotation = annotation +"\n";	
				}
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return annotation;
	}

	private class Entity {

		public List<String> uris = Lists.newArrayList();
		public List<String> posTypesAndCategories = Lists.newArrayList();
		public String type;
		public String label;

	}

	private String doTASK(String inputText) throws MalformedURLException, IOException, ProtocolException {

		String urlParameters = "type=" + inputType;
		urlParameters += "&task=" + taskType;
		urlParameters += "&output=" + outputFormat;
		urlParameters += "&input=" + URLEncoder.encode(inputText, "UTF-8");

		return POST(urlParameters, requestURL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aksw.hawk.nlp
	 */
	private String POST(String urlParameters, String requestURL) throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(requestURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		connection.setRequestProperty("Content-Length", String.valueOf(urlParameters.length()));

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();

		InputStream inputStream = connection.getInputStream();
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(in);

		StringBuilder sb = new StringBuilder();
		while (reader.ready()) {
			sb.append(reader.readLine());
		}

		wr.close();
		reader.close();
		connection.disconnect();

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aksw.hawk.nlp.NERD_module#getEntities(java.lang.String)
	 */
	public Map<String, List<Entity>> getEntities(String question) throws ParseException, MalformedURLException, ProtocolException, IOException {
		HashMap<String, List<Entity>> tmp = new HashMap<String, List<Entity>>();
		String foxJSONOutput = doTASK(question);

		JSONParser parser = new JSONParser();
		JSONObject jsonArray = (JSONObject) parser.parse(foxJSONOutput);
		String output = URLDecoder.decode((String) jsonArray.get("output"), "UTF-8");

		String baseURI = "http://dbpedia.org";
		Model model = ModelFactory.createDefaultModel();
		RDFReader r = model.getReader("N3");
		r.read(model, new StringReader(output), baseURI);

		ResIterator iter = model.listSubjects();
		ArrayList<Entity> tmpList = new ArrayList<>();
		while (iter.hasNext()) {
			Resource next = iter.next();
			StmtIterator statementIter = next.listProperties();
			Entity ent = new Entity();
			while (statementIter.hasNext()) {
				Statement statement = statementIter.next();
				String predicateURI = statement.getPredicate().getURI();
				if (predicateURI.equals("http://www.w3.org/2000/10/annotation-ns#body")) {
					ent.label = statement.getObject().asLiteral().getString();
				} else if (predicateURI.equals("http://ns.aksw.org/scms/means")) {
					String uri = statement.getObject().asResource().getURI();
					String encode = uri.replaceAll(",", "%2C");
					ResourceImpl e = new ResourceImpl(encode);
					ent.uris.add(e.getURI().toString());
				} else if (predicateURI.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
					ent.posTypesAndCategories.add(statement.getObject().asResource().getURI().toString());
				}
			}
			tmpList.add(ent);
		}
		tmp.put("en", tmpList);

		if (!tmp.isEmpty()) {
			log.debug("\t" + Joiner.on("\n").join(tmp.get("en")));
		}
		return tmp;
	}

}