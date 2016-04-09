package org.aksw.asknow.ml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class FeatureParser {
 
	public static void main(String[] args) {
		
		String id;
		String ques,squery;
		int feature1, feature2;
		String dataRow=null;
		String fileName = "/Users/mohnish/git2/AskNow/src/main/java/org/aksw/asknow/ml/inputfile";
	

		try (Scanner scanner = new Scanner(new File(fileName))) {
			File file = new File("/Users/mohnish/git2/AskNow/src/main/java/org/aksw/asknow/ml/outputfile");
			BufferedWriter bw = null;
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("id \t feature1 \t feature2");
			while (scanner.hasNext()){
				//System.out.println(scanner.nextLine());
				dataRow = scanner.nextLine();
				//System.out.println("rrr"+dataRow);
				String[] dataArray = dataRow.split("\t");
				id=dataArray[0];
				ques = dataArray[1];
				squery = dataArray[2];
				System.out.println(id +"\t"+ ques +"\t"+ squery);
				//break;
				feature1 = Feature.getFeature1(squery);
				feature2 = Feature.getFeature2(squery);
				System.out.println(id +"\t"+ feature1 +"\t"+ feature2);
				bw.write("\n"+id +"\t"+ feature1 +"\t"+ feature2);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
