package org.aksw.asknow.ml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class SparqlFeatureParser {
 
	public static void main(String[] args) {
		
		String id;
		String ques,squery;
		int feature1, feature2,feature3,feature4;
		String dataRow=null;
		String fileName = "/Users/mohnish/git2/AskNow/src/main/java/org/aksw/asknow/ml/inputfile";
	

		try (Scanner scanner = new Scanner(new File(fileName))) {
			File file = new File("/Users/mohnish/git2/AskNow/src/main/java/org/aksw/asknow/ml/SparqlClusterOutput");
			BufferedWriter bw = null;
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("id \t question \t feature1(KeyWord) \t feature2(#triples) \t feature3(#varibale)");
			while (scanner.hasNext()){
				System.out.println(scanner.nextLine());
				dataRow = scanner.nextLine();
				String[] dataArray = dataRow.split("\t");
				id=dataArray[0];
				ques = dataArray[1];
				squery = dataArray[2];
				//System.out.println(id +"\t"+ ques +"\t"+ squery);
				//break;
				feature1 = SparqlFeature.getFeature1(squery);
				feature2 = SparqlFeature.getFeature2(squery);
				feature3 = SparqlFeature.getFeature3(squery);
				//System.out.println(feature3);
				System.out.println(id +"\t"+ ques +"\t"+ feature1 +"\t"+ feature2+"\t"+ feature3);
				bw.write("\n"+id +"\t"+ ques +"\t"+ feature1 +"\t"+ feature2+"\t"+ feature3);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
