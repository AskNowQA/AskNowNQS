package org.aksw.asknow.nqs;

import java.util.ArrayList;
import java.io.*;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j public class Tester {
	private static QueryBuilder qb;
	static int correctDesires = 0, correctInputs = 0, bothCorrect = 0;;
	static int incorrectDesires = 0, incorrectInputs, notcharacterized=0;
	static WritableWorkbook workbooktowrite;
	static WritableSheet incorrectSheet;
	static ArrayList<Integer> twoSets;
	static ArrayList<Integer> threeSets;
	static int how=0,what=0,when=0,where=0,which=0,who=0,nonwh=0;
	static int ehow=0,ewhat=0,ewhen=0,ewhere=0,ewhich=0,ewho=0,enonwh=0;
	static ArrayList<String> nonwhString;
	public static void main(String[] args) throws IOException, WriteException {
		qb = new QueryBuilder();
		nonwhString = new ArrayList<>();
		//qb.setQuery("Who was the first president of independent India?");
		qb.setQuery("What are some fresh water lakes in lower Himalayas?");
		qb.buildQuery();
		log.debug("QCT",qb.getCharacterizedString());
		log.debug("TAGGED",qb.getTaggedString());
	//	queryTest();
		//sparqlTest();	
		//encartaTest();
	//	qald5Train();
		
		 /*workbooktowrite = Workbook.createWorkbook(new File("OWL-stc/IncorrectOutputs.xls"));
		 incorrectSheet = workbooktowrite.createSheet("Output", 1); 
		 twoSets = new ArrayList<Integer>();
		 threeSets = new ArrayList<Integer>();
		

		for(int i=1;i<12;i++){
			if(i!=4 && i!=7)
				worksheetHandeler(i);

		}
		
		workbooktowrite.write();
		workbooktowrite.close();*/
		/*log.debug("Input", "In which country Sachin Tendulkar was born?");
		qb.setQuery("In which country Sachin Tendulkar was born?");
		qb.buildQuery();
		log.debug("QCT", qb.getCharacterizedString()); 
		
		log.debug("Input", "Where was Sachin Tendulkar born?");
		qb.setQuery("Where was Sachin Tendulkar born?");
		qb.buildQuery();
		log.debug("QCT", qb.getCharacterizedString()); 
		
		log.debug("Input", "Sachin Tendulkar was born in which country?");
		qb.setQuery("Sachin Tendulkar was born in which country?");
		qb.buildQuery();
		log.debug("QCT", qb.getCharacterizedString()); */
		
		/*try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("OWL-stc/hasThree.txt"), "utf-8"))) {
			for(int i=0;i<threeSets.size();i++)
				writer.write(threeSets.get(i)+"\n");
		}catch(Exception e){
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("OWL-stc/hasTwo.txt"), "utf-8"))) {
			for(int i=0;i<twoSets.size();i++)
				writer.write(twoSets.get(i)+"\n");
		}catch(Exception e){
		}*/
		
		log.debug("How", how);
		log.debug("What", what);
		log.debug("Where", where);
		log.debug("Which", which);
		log.debug("When", when);
		log.debug("Who", who);
		log.debug("nonWh", nonwh);
		log.debug("eHow", ehow);
		log.debug("eWhat", ewhat);
		log.debug("eWhere", ewhere);
		log.debug("eWhich", ewhich);
		log.debug("eWhen", ewhen);
		log.debug("eWho", ewho);
		log.debug("nonWhString", nonwhString.toString());
		log.debug("notcharacterized", notcharacterized);
		log.debug("Correct Desire", correctDesires);
		log.debug("Incorrect Desire", incorrectDesires);		
		log.debug("Correct Input", correctInputs);
		log.debug("Incorrect Input", incorrectInputs);
		log.debug("Both correct", bothCorrect);
	}
		
	private static void qald5Train() {
		BufferedReader br, brKey;
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("qald4Train_Output"), "utf-8"))) {

			br = new BufferedReader(new FileReader("qald4Train")); 
			brKey = new BufferedReader(new FileReader("qald4trainKeywords"));
			try {
				String line = br.readLine();
				String keys = brKey.readLine();
				int i = 1;
				while (line != null) {
					//log.debug("Input",line);
					qb.setQuery(line);
					qb.buildQuery();
					updateWhCount();
					if(qb.getCt().isCharacterized()){
						/*writer.write("\n\nQuery "+i+":"+line);
						writer.write("\nKeywords: "+keys);
						writer.write("\n"+qb.getCharacterizedString());*/
						//writer.write("\n"+qb.getTaggedString());
						
						String desire = qb.getCt().getDesire();
						String input = qb.getCt().getInputs();
						boolean desirenotfound = true, inputnotfound=true;
						for(String s:keys.split(",")){
							if(desire!=null && !desire.trim().isEmpty() && (desire.toLowerCase().trim().replaceAll(" ", "").contains(s.toLowerCase().trim().replaceAll(" ", ""))
									|| keys.toLowerCase().trim().replaceAll(" ", "").contains(desire.toLowerCase().trim().replaceAll(" ", "")))){
								if(desirenotfound)
									correctDesires++;
								desirenotfound = false;
							}
							
							if(input!=null && !input.trim().isEmpty() && ( input.toLowerCase().trim().replaceAll(" ", "").contains(s.toLowerCase().trim().replaceAll(" ", "")))){
								if(inputnotfound)
									correctInputs++;
								inputnotfound = false;
							}
						}
						boolean errorUpdated = false;
						if(desire!=null && !desire.trim().isEmpty() && desirenotfound){
							writer.write("\n\nQuery "+i+":"+line);
							writer.write("\nKeywords: "+keys);
							writer.write("\n"+qb.getCharacterizedString());
							//writer.write("\nincorrectDesires: required:"+keys+"   evaluated:"+desire);								
							incorrectDesires++;
							updateErrorCount();
							errorUpdated = true;
						}
						
						if(input!=null && !input.trim().isEmpty() &&inputnotfound){
							
							if(!errorUpdated){
								writer.write("\n\nQuery "+i+":"+line);
								writer.write("\nKeywords: "+keys);
								writer.write("\n"+qb.getCharacterizedString());
								updateErrorCount();
							}
							//writer.write("\nincorrectInput: required:"+keys+"   evaluated:"+input);
							incorrectInputs++;
							errorUpdated = true;
						}
						
						if(!desirenotfound && !inputnotfound){
							writer.write("\n\nQuery "+i+":"+line);
							writer.write("\nKeywords: "+keys);
							writer.write("\n"+qb.getCharacterizedString());
							bothCorrect++;
						}
					
					} else{
						notcharacterized++;
						writer.write("\n\nQuery Not Characterized: "+line);
					}
					
					line = br.readLine();
					keys = brKey.readLine();
					i++;
					//log.debug("Input Characterized:",qb.getCharacterizedString());
				}
				log.error("Input", "EOF");
			}catch(Exception e){
				log.error("Reader","Exception");
				e.printStackTrace();
			}
			finally {
				br.close();
			}
		}catch(Exception e){
			log.error("Writer","Exception");
			e.printStackTrace();
		}
		finally {
			//br.close();
		}
		
	}




	private static void worksheetHandeler(int num) {
		try {
			Workbook workbooktoread = Workbook.getWorkbook(new File("OWLstcDatasets/Dataset_v"+num+".xls"));
			Sheet sheet = workbooktoread.getSheet(0);

			WritableWorkbook workbooktowrite = Workbook.createWorkbook(new File("OWL-stc/OutputNew"+num+".xls"));
			WritableSheet sheettowrite = workbooktowrite.createSheet("Output", 1);
			Label label = new Label(0, 0, "Sr. No."); 
			sheettowrite.addCell(label);
			label = new Label(1, 0, "Input String" ); 
			sheettowrite.addCell(label);
			label = new Label(2, 0, "Original Inputs"); 
			sheettowrite.addCell(label);
			label = new Label(3, 0, "Original Outputs"); 
			sheettowrite.addCell(label);
			label = new Label(4, 0, "Evaluated Inputes"); 
			sheettowrite.addCell(label);
			label = new Label(5, 0, "Evaluated Outputs"); 
			sheettowrite.addCell(label);
			label = new Label(6, 0, "Evaluated QCT"); 
			sheettowrite.addCell(label);	 



			int numRows = sheet.getRows();        // getColumns() returns an int
			int j=1;
			for(int i = 1; i < numRows; i++) {
				try{
					Cell[] column = sheet.getRow(i);
					String firstCellValue = column[0].getContents().trim();
					if(firstCellValue!=null && !firstCellValue.isEmpty() && firstCellValue.length()>0){
						String inputs = sheet.getRow(i)[3].getContents();
						String desires = sheet.getRow(i)[4].getContents();
						int k = i+1;
						while(k<numRows && (sheet.getRow(k)[0].getContents()==null || sheet.getRow(k)[0].getContents().trim().isEmpty())){
							if(sheet.getRow(k)[3].getContents()!=null && !sheet.getRow(k)[3].getContents().isEmpty())
								inputs += ", "+sheet.getRow(k)[3].getContents();

							if(sheet.getRow(k)[4].getContents()!=null && !sheet.getRow(k)[4].getContents().isEmpty())
								desires += ", "+sheet.getRow(k)[4].getContents();
							k++;
						}
						//log.debug("K","k="+k+" inputs:"+inputs+" outputs:"+desires);
						boolean hasThree = false;
						if(column.length>5 && column[5].getContents()!=null && !column[5].getContents().isEmpty()){
							insertIntoSheet(sheettowrite, j , firstCellValue, column[5].getContents(), inputs,desires);		    		     
							j++;
						}
						if(column.length>6 && column[6].getContents()!=null && !column[6].getContents().isEmpty()){
							insertIntoSheet(sheettowrite, j , firstCellValue, column[6].getContents(), inputs,desires);
							j++;
						}
						if(column.length>7 && column[7].getContents()!=null && !column[7].getContents().isEmpty()){
							insertIntoSheet(sheettowrite, j , firstCellValue, column[7].getContents(), inputs,desires);
							j++;
							hasThree = true;
						}
						i=k-1;
						
						if(hasThree){
							threeSets.add(Integer.parseInt(firstCellValue.trim()));
						}
						else{
							//log.debug("Has < 3 varients", "i:"+i+"  cellvalue:"+firstCellValue);
							twoSets.add(Integer.parseInt(firstCellValue.trim()));
						}
					}
					/*System.out.println(column[0].getContents());
		     System.out.println(column[3].getContents());
		     System.out.println(column[4].getContents());*/
					/*for(Cell cell:column) {
		           System.out.println(cell.getContents());
		     }*/
				} catch (Exception x){
					x.printStackTrace();
				} 
			}
				workbooktowrite.write();
				workbooktowrite.close();	

			} catch (BiffException | IOException e) {
				e.printStackTrace();
			} catch (Exception r){
				r.printStackTrace();
			}

		}

		private static void insertIntoSheet(WritableSheet sheettowrite, int j,
				String firstCellValue, String contents, String Orginal_inputs,
				String Original_desires) {
			try{
				Label label = new Label(0, j, firstCellValue); 		   	 
				sheettowrite.addCell(label);			
				label = new Label(1, j, contents); 
				sheettowrite.addCell(label);
				label = new Label(2, j, Orginal_inputs); 
				sheettowrite.addCell(label);
				label = new Label(3, j, Original_desires); 
				sheettowrite.addCell(label);
				qb.setQuery(contents);
				qb.buildQuery();
				label = new Label(4, j, qb.getCt().getInputs()); 
				sheettowrite.addCell(label);
				label = new Label(5, j, qb.getCt().getDesire()); 
				sheettowrite.addCell(label);
				label = new Label(6, j, qb.getCharacterizedString()); 
				sheettowrite.addCell(label);
				updateWhCount();
				boolean desireFound = false;
				for(String desire : Original_desires.split(",")){
					if(desire!=null && !desire.trim().isEmpty() && qb.getCt().getDesire()!=null){
						if(qb.getCt().getDesire().toLowerCase().replaceAll(" ", "").contains(desire.toLowerCase().trim()))
							desireFound = true;
					}
				}
				
				if(desireFound)
					correctDesires++;
				else{
					incorrectDesires++;
					label = new Label(0, incorrectDesires, firstCellValue); 		   	 
					incorrectSheet.addCell(label);			
					label = new Label(1, incorrectDesires, contents); 
					incorrectSheet.addCell(label);
					label = new Label(2, incorrectDesires, Orginal_inputs); 
					incorrectSheet.addCell(label);
					label = new Label(3, incorrectDesires, Original_desires); 
					incorrectSheet.addCell(label);
					label = new Label(4, incorrectDesires, qb.getCt().getInputs()); 
					incorrectSheet.addCell(label);
					label = new Label(5, incorrectDesires, qb.getCt().getDesire()); 
					incorrectSheet.addCell(label);
					label = new Label(6, incorrectDesires, qb.getCharacterizedString()); 
					incorrectSheet.addCell(label);
				}
				
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private static void updateWhCount() {
			String wh = qb.getCt().getWh().toLowerCase().trim();
			if(wh.contains("what"))
				what++;
			else if(wh.contains("how"))
				how++;
			else if(wh.contains("when"))
				when++;
			else if(wh.contains("where"))
				where++;
			else if(wh.contains("which"))
				which++;
			else if(wh.contains("who"))
				who++;
			else {
				nonwh++;
				nonwhString.add(wh);
			}
		}
		
		private static void updateErrorCount() {
			String wh = qb.getCt().getWh().toLowerCase().trim();
			if(wh.contains("what"))
				ewhat++;
			else if(wh.contains("how"))
				ehow++;
			else if(wh.contains("when"))
				ewhen++;
			else if(wh.contains("where"))
				ewhere++;
			else if(wh.contains("which"))
				ewhich++;
			else if(wh.contains("who"))
				ewho++;
		}


		private static void OWLstc(int num) {
			BufferedReader br;

			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("OWL-stc/OWLstc_Output"+num+".txt"), "utf-8"))) {

				WritableWorkbook workbook = Workbook.createWorkbook(new File("OWL-stc/OWLstc_Output"+num+".xlsx"));
				WritableSheet sheet = workbook.createSheet("Output"+num+"", 1);


				br = new BufferedReader(new FileReader("OWL-stc/OWLstc_Input"+num+".txt"));
				try {
					String line = br.readLine();
					int i = 1;

					while (line != null) {	 
						System.out.println(line);
						qb.setQuery(line);
						qb.buildQuery();
						writer.write("\n\nQuery "+i+":"+line);
						writer.write("\n"+qb.getCharacterizedString());
						writer.write("\n"+qb.getTaggedString());	        	
						System.out.println(qb.getCt().getDesire());
						Label label = new Label(0, i-1, line); 
						sheet.addCell(label);
						label = new Label(1, i-1, qb.getCt().getDesire()); 
						sheet.addCell(label);
						label = new Label(2, i-1, qb.getCt().getInputs()); 
						sheet.addCell(label);
						label = new Label(3, i-1, qb.getCharacterizedString()); 
						sheet.addCell(label);	    		
						i++;	        	
						line = br.readLine();
					}
				}catch(Exception e){
					log.error("BufferefReaderException", e.getMessage());
				}
				finally {
					br.close();
				}

				workbook.write();
				workbook.close();
			}catch(Exception e){
				log.error("BufferefWriterException", e.getMessage());

			}
			finally {
				//br.close();
			}

		}

		private static void encartaTest() {
			BufferedReader br;
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("Encarta_Output.txt"), "utf-8"))) {

				br = new BufferedReader(new FileReader("Encarta_Input.txt"));
				try {
					String line = br.readLine();
					int i = 1;
					while (line != null) {
						//log.debug("Input",line);
						qb.setQuery(line);
						qb.buildQuery();
						updateWhCount();
						writer.write("\n\nQuery "+i+":"+line);
						writer.write("\n"+qb.getCharacterizedString());
						//writer.write("\n"+qb.getTaggedString());
						line = br.readLine();
						i++;
						//log.debug("Input Characterized:",qb.getCharacterizedString());
					}
					log.error("Input", "EOF");
				}catch(Exception e){
					log.error("Reader","Exception");
					e.printStackTrace();
				}
				finally {
					br.close();
				}
			}catch(Exception e){
				log.error("Writer","Exception");
				e.printStackTrace();
			}
			finally {
				//br.close();
			}

		}



		private static void queryTest() {
			//QueryTokenizer qt = new QueryTokenizer("What is the subcategories of science?");
			//qt.createAndGetTokenList("I-I I I am Sam?");


			//System.out.println(qt.getTaggedString());

			String[] queries = {/*"What is the capital of Karnataka?",
									"What is a cat?",
									"When is the next solar eclipse?",
									"In which country DA-IICT is located?",
									"Where DA-IICT is located?",
									"Which is the longest river in Africa?",
									"What animals are mammals?",
									"What animal lays eggs?",
									"What is the capital of The United States Of America?",
									"What is Taj Mahal?",
									"What does Jane Doe drink?",
									"When is Thanksgiving?",
									"Where is The Leaning Tower Of Pisa?",
									"Where is Niagara Falls?",
									"How much does the bag cost?",
									"How far is Tampa from Miami?",
									"What are some dangerous plants?",
									"Where is Buffalo?",*/
					"When did Jane Doe visit The United States Of America?",
					"When did Jane Doe visit Buffalo located in New York state?",
					"What is the name of the highest mountain which is located in Himalayas.",
					"Which is the smallest lake in lower Himalayas?",
					"Who is the most successful captain of Indian Cricket Team?",
					"What are some salt lakes in lower Himalayas?",
					"Who was the prime minister of Unite States of America in 1992?",
					"Where is the state capital of Missouri which is so beautiful?",
					"What are some dangerous animals and plants which live under the sea?",
					"What is most populous democracy in the Caribbean which is geographically the largest?",
					"What is the distance between Buffalo and New York?",
					"Who was the captain of India, England and Pakistan in cricket world cup which was played in 2011?",
					"What are some wild animals which live in arctic ocean?",
			"Which was the most popular song in Bollywood in 2003 and 2014?"};

			String[] qald4TrainQueries = {"Which diseases is Cetuximab used for?",
					"What are the diseases caused by Valdecoxib?",
					"What is the side effects of drugs used for Tuberculosis?",
					"What are the side effects of Valdecoxib?",
					"Which genes are associated with breast cancer?",
					"Which drugs have fever as a side effect?",
					/*"Give me diseases treated by tetracycline",*/
					"What diseases are treated by tetracycline",
					"Which drugs interact with allopurinol?",
					"What are side effects of drugs used for asthma?",
					"Which foods does allopurinol interact with?",
					"What are enzymes of drugs used for anemia?",
					"What is the target drug of Vidarabine?",
					"Which drugs target Multidrug resistance protein 1?",
					/*"Give me drug references of drugs targeting Prothrombin.",*/
					"What are the drug references of drugs targeting Prothrombin.",
					"Which genes are associated with diseases treated with Cetuximab?",
					"Which drugs have hypertension and vomiting as side-effects?",
					"Which are possible drugs against rickets?",
					"What are the common side effects of Doxil and Bextra?",
					"Which are the drugs whose side effects are associated with the gene TRPM6?",
					"Which are the side effects of Penicillin G?",
					"Which diseases are associated with the gene FOXP2?",
					"Which are possible drugs for diseases associated with the gene ALD?",
					"Which are targets of Hydroxocobalamin?",
					"Which are targets for possible drugs for diseases associated with the gene ALD?",
			"Which genes are associated with diseases whose possible drugs target Cubilin?"};
			
			String[] qald4TrainQueries2 ={/*"What is Vidarabine's target drug",*/
					/*"Allopurinol interacts with which food",*/ 
					"Penicillin G has which side effects",
					"Gene FOXP2 is associated to which diseases",
					"Breast Cancer is associated to which gene",
					"Gene ALD is associated to which drugs for diseases",
					"With which drugs allopurinol interacts",
					/*"Against rickets which are the possible drugs",*/
					"Valdecoxib has what kind of side effects"};
			
			String[] qald4TrainQueries3 = {
					
			};

			String[] qald4TestQueries = {"Which genes are associated with Endothelin receptor type B?",
					"Which genes are associated with subtypes of rickets?",
					"Which drug has the highest number of side-effects?",
					"Which drug has the highest side-effects?",
					"Which is the highest number in a set",
					/*"List drugs that lead to strokes and arthrosis.",*/
					"Which drugs leads to strokes and arthrosis.",
					"Which drugs have a water solubility of 2.78e-01 mg/mL?",
					/*"Give me the side-effects drugs with a solubility of 3.24e-02 mg/mL.",*/
					"What are the side-effects of drugs with a solubility of 3.24e-02 mg/mL",
					"Which diseases are associated with SAR1B?",
					"Which experimental drugs interact with food?",
					"Which approved drugs interact with fibers?",
					"Which drugs interact with food and have HIV infections as side-effects?",
					/*"Give me diseases whose possible drugs target the elongation factor 2.",*/
					"What are the diseases whose possible drugs target the elongation factor 2?",
					"Which drugs achieve a protein binding of 100%?",
					/*"List illnesses that are treated by drugs whose mechanism of action involves norepinephrine and serotonin.",*/
					"Which illnesses are treated by drugs whose mechanism of action involves norepinephrine and serotonin?",
					"Which is the least common chromosome location?",
					"Are there drugs that target the Protein kinase C beta type?",
					/*"Give me all diseases of the connective tissue class.",*/
					"Which diseases belongs to a connective tissue class?",
					"Which targets are involved in blood clotting?",
					/*"List the number of distinct side-effects of drugs which target genes whose general function involves cell division.",*/
					"What are the distinct side-effects of the drugs which target genes whose general function involves cell division?",
					"Which drugs have no side-effects?",
					/*"List diseases whose possible drugs have no side effects.",*/
					"Which diseases have the possible drugs with no side effects?",
					/*"Give me the drug categories of Desoxyn.",
											 "Give me drugs in the gaseous state.",*/
					"What are the drug categories of Desoxyn.",
					"What drugs are in the gaseous state.",
					"Which disease has the largest size?",
					"Which drugs have bipolar disorder as indication?",
					"Which diseases have a class degree of 11?"};

			//ner_resolver ner = new ner_resolver();
			//List<Triple<String, Integer, Integer>> nerTags;
			int i=1;
			for(String query : queries){
				qb.setQuery(query);
				qb.buildQuery();
				System.out.println("\nQuery "+i+":"+query);//+"\n"+qb.getTaggedString());
				System.out.println(qb.getCharacterizedString());
				//nerTags = ner.getNERTags(query,true);
				//System.out.println(ner.)
				i++;
			}
			//nerTags = ner.getNERTags("What is Statue of Liberty?",false);
			/*POStag tagger = new POStag();
				System.out.println(tagger.getTaggedSentence("In which country DA-IICT is located?"));*/
		}
		
}
		
