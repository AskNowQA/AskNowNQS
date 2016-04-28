package org.aksw.asknow.query;

import java.util.*;
import org.aksw.asknow.Nqs;
import org.aksw.asknow.annotation.Spotlight;
import org.aksw.asknow.query.sparql.PropertyValue;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

/** A boolean (yes/no) {@link Query}. */
public class BooleanQuery implements Query{

	private BooleanQuery() {}
	public static final BooleanQuery INSTANCE = new BooleanQuery();
	
	static final Set<RDFNode> TRUE = Collections.singleton(ResourceFactory.createTypedLiteral("true", XSDDatatype.XSDboolean));
	static final Set<RDFNode> FALSE = Collections.singleton(ResourceFactory.createTypedLiteral("false", XSDDatatype.XSDboolean));
	
	private String cleanConcept(String tempConcept) {
		tempConcept = tempConcept.replace(" a ", ",").trim();
		tempConcept = tempConcept.replace(", , ", ",").trim();
		tempConcept = tempConcept.replace(",,", ",").trim();
		return tempConcept;
	}

	@Override public Set<RDFNode> execute(Nqs nqs)
	{
		//if(1==1) throw new NotImplementedException("TODO: Ko@Mohnish check if this is correct first");
		Set<String> properties = new HashSet<>();
		String tempConcept = nqs.getConcepts();
		tempConcept = cleanConcept(tempConcept);
		System.out.println(tempConcept);
		String[] parts = tempConcept.split(",");
		System.out.println(parts[0]);
		if(nqs.getRoles().contains("than")){
			System.out.println(nqs.getRoles());
		
		}//compare boolean
		
		else{
			System.out.println("is-atypeOfsubset boolean");
			String dbpRes1="";
			for(String s: nqs.Resource){
				dbpRes1 =s;
				break;
			}
			//String dbpRes1 = Spotlight.getDBpLookup(parts[0]);
			//System.out.println("value bahi"+dbpRes1);
			properties = PropertyValue.getProperties(dbpRes1);
			for (String prop : properties) {
				if(prop.toLowerCase().contains(parts[parts.length -1].trim())){
					return TRUE;
					}
				}
			properties = PropertyValue.getValues(dbpRes1);
			for (String prop : properties) {
				if(prop.toLowerCase().contains(parts[1].trim().toLowerCase().replaceAll(" ", ""))){
					return TRUE;
					}
				}
		}
		System.out.println("ans is False");
		return FALSE;
	}

}