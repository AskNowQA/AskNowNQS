package org.aksw.asknow.nqs;
import java.util.ArrayList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class CharacterizationTemplate {

	private ArrayList<QueryToken> tokens;
	private ArrayList<Role> roleTokenList;
	private ArrayList<Concept> conceptTokenList;
	private ArrayList<Clause> clauseList;
	private ArrayList<String> inputMap;
	@Getter private String wh;  //WHAT, WHERE, WHEN, etc.
	private String relation1;
	private String relation2;
	@Getter private String desire;   // Desire (or Intent)
	private String input; 
	private String inputQuantifier;
	private String desireQuantifier;
	@Getter private String characterizedString;
	private int clauseIndex =0, conceptSuper, conceptSub, roleIndex = 1, index,whIndex;
	private int sIndex = 2; //Structure Index
	//private int conceptCount=0;
	private String NOUN_MODIFIER_TAG = "_NM";
	@Getter private boolean isCharacterized = true;
	
	
	public CharacterizationTemplate(ArrayList<QueryToken> tokens) {
		this.tokens = tokens;
		roleTokenList = new ArrayList<>();
		conceptTokenList = new ArrayList<>();
		inputMap = new ArrayList<>();
	}

	public void createTemplate() {
		
		resetTemplate();
		//log.debug("QCT", tokens.toString());
		/*for(QueryToken qt:tokens)
			if(isConcept(qt))
				conceptCount++;
		*/
		fillRoleAndConceptList();
		mergeConsecutiveTokens();
		clauseList = getClauseList();
		//System.out.print("\nConcepts:"+conceptTokenList.size()+" Roles:"+roleTokenList.size()+" Clauses:"+clauseList.size());
		//log.debug("Concepts",conceptTokenList.toString());
		//log.debug("Roles",roleTokenList.toString());
		if((hasImplicitDesire() && conceptTokenList.size() == 1)
				|| ((!hasImplicitDesire()) && conceptTokenList.size() == 2)) {
			log.debug("QCT", "Simple Fit");
			fitSimpleQuery();
		}
		else if(conceptTokenList.size()>=2){
			log.debug("QCT", "Complex Fit");
			fitComplexQuery();
		} else{
			log.error("QCT","Neither simple nor complex: "+QueryModuleLibrary.getStringFromTokens(tokens));
			isCharacterized = false;
		}
	}

	

	private void fitSimpleQuery() {
		if(roleTokenList.size()>0)
			relation2 = roleTokenList.get(0).getString();
		
		if(conceptTokenList.size()>=2){
			desire = conceptTokenList.get(0).getString();
			splitDesireAndQuantifier();
			input = conceptTokenList.get(1).getString();
			splitInputAndQuantifier();
			if(wh.toLowerCase().startsWith("how"))
				handleHOW();
		}
		else if(hasImplicitDesire()){
			
			input = conceptTokenList.get(0).getString();
			splitInputAndQuantifier();
			desire = getImplicitDesire(true);	// Implicit Desire;
			if(roleTokenList.size()>1)
				mergeRolesIntoR2();
			//log.debug("QCT", "has implicit desire: "+ D);
		} else{
			log.warn("No Concept Found.");
		}
		
		
		desire = removeTHE(desire);
		input = removeTHE(input);
		inputMap.add("[I="+input+"]");
		
		if(wh.toLowerCase().trim().startsWith("how")){
			relation2 = relation1 + " " +relation2;
			relation1 = "";
		}
		
		characterizedString = "[WH] = " + wh + ", " + "[R1] = " + relation1 + ", [" + printQuantifier(desireQuantifier,"[DQ]")+" "+ printModifier(desire,"[DM]") +"[D] =" /*+ DQ +" "*/ +withoutModifier( desire) + 
				"], " + "[R2] = " + relation2 + ", ["+ printQuantifier(inputQuantifier,"[IQ]")+" "+ printModifier(input,"[IM]")+ "[I] =" /*+ IQ +" "*/ + withoutModifier(input) +"]";	
	}

	
	private String withoutModifier(String value) {
		for(String s:value.split(" ")){
			if(s.endsWith(NOUN_MODIFIER_TAG))
				value = value.replace(s, "");
		}
		return value;
	}

	private String printQuantifier(String value, String templateLable) {
		String quantifier = "";
		if(value !=null && !value.isEmpty() && value.trim().length()>0)
			quantifier = templateLable + " = " + value;
		return quantifier;
	}

	private String printModifier(String value, String templateLable) {
		String modifierString = "";
		boolean hasModifier = false;
		for(String s : value.split(" ")){
			if(s.endsWith(NOUN_MODIFIER_TAG)){
				hasModifier = true;
				modifierString += s.substring(0, s.length() - NOUN_MODIFIER_TAG.length())+" ";
			}
		}
		if(hasModifier)
			modifierString = templateLable + " = "+ modifierString;
		return modifierString;
	}

	private void mergeRolesIntoR2() {
		relation2 = "";
		for(Role r:roleTokenList){
			relation2 += r.getString()+" ";
		}
		relation2 = relation2.trim();		
	}

	private void handleHOW() {
		if(wh.contains(" ")){
			String nextWord = wh.toLowerCase().split(" ")[1];
			if(nextWord.equals("many")){
				desire = "count("+desire+")";
			} else if(nextWord.equals("much")){
				desire = "quantity("+desire+")";
			} 
		} else {
			desire = "DataProperty("+desire+")";
		}
		
	}

	private void fitComplexQuery() { /*conceptTokenList>2*/
		
		/*
		 * for(Clause token : clauseList){
			System.out.print(token.getString()+"["+token.getIndex()+"] ");
		}
		System.out.println();
		
		for(Role token : roleTokenList){
			System.out.print(token.getString()+"["+token.getIndex()+"] ");
		}
		System.out.println();

		for(Concept token : conceptTokenList){
			System.out.print(token.getString()+"["+token.getIndex()+"] ");
		}
		
*/
		int conceptIndex;
		/*Implicit Desire*/
		if(hasImplicitDesire()){
			input = conceptTokenList.get(0).getString();
			splitInputAndQuantifier();
			desire = getImplicitDesire(false);	// Implicit Desire;
			conceptIndex = 1;
		} else{
			desire = conceptTokenList.get(0).getString();
			splitDesireAndQuantifier();
			input = conceptTokenList.get(1).getString();
			conceptIndex = 2;
			if(wh.toLowerCase().startsWith("how"))
				handleHOW();
		}
		
		//if(roleTokenList.get(conceptIndex))
		if(roleTokenList.size()>0){
			if((clauseList.size()==0 && roleTokenList.get(0).getIndex() < conceptTokenList.get(conceptIndex-1).getIndex() ) 
					|| (clauseList.size()>0 && roleTokenList.get(0).getIndex()<clauseList.get(0).getIndex())){
				relation2 = roleTokenList.get(0).getString();
			} else if(roleTokenList.get(0).getIndex() > conceptTokenList.get(conceptIndex-1).getIndex() ){
				//clauseList.add(0, new Clause(roleTokenList.get(0).getToken(),roleTokenList.get(0).getIndex()));
				//roleTokenList.remove(0);
				roleIndex--;
			}
			
			// If R2 is after first Clause.
			else if(clauseList.size()>0 && roleTokenList.get(0).getIndex()>clauseList.get(0).getIndex())
				roleIndex--;
		}
		
		
		desire = removeTHE(desire);
		input = removeTHE(input);
		inputMap.add("[I="+input+"]");

		if(wh.toLowerCase().trim().startsWith("how")){
			relation2 = relation1 + " " +relation2;
			relation1 = "";
		}
		
		characterizedString = "[WH] = " + wh + ", " + "[R1] = " + relation1 + ", ["+ printQuantifier(desireQuantifier,"[DQ]")+" "+ printModifier(desire,"[DM]") + "[D] =" /*+ DQ */+" "+ withoutModifier(desire) + "], " 
								+ "[R2] = " + relation2 + ", ["+ printQuantifier(inputQuantifier,"[IQ]")+" "+ printModifier(input,"[IM]")+"[I1_1] =" /*+ IQ*/+" "+withoutModifier(input) +"]";
		
		
		
		// CC before first clause
/*		while(conceptTokenList.size()>conceptIndex && 
				(clauseList.size()==0 || conceptTokenList.get(conceptIndex).getIndex() < clauseList.get(0).getIndex()))*/
		while(conceptNext(conceptIndex)){
			String tempInput = removeTHE(conceptTokenList.get(conceptIndex).getString());
			characterizedString +=" [CC] ["+extractAndPrintQuantifier(tempInput,"[IQ]")+" "+ printModifier(tempInput,"[IM]")+" [I1_"+(conceptIndex+1)+"] = "+withoutModifierAndQuantifier(tempInput)+"]";
			inputMap.add(",[I1_"+(conceptIndex+1)+"="+ tempInput+"]");

			conceptIndex++;
		}

		
		/*If no CC */
		if(conceptIndex==1)
			conceptSuper = 2;
		else  /*If CC */
			conceptSuper = conceptIndex;
		
		
		
		while(clauseList.size()>clauseIndex || roleTokenList.size()>roleIndex){
			conceptSub=1;
			String role="";
			
			if(roleTokenList.size()>roleIndex)
				role = roleTokenList.get(roleIndex).getString();
			
			if(clausalStructureWithoutClause()){
				roleIndex++;
				characterizedString +=",\n[CL"+(sIndex)+"] = null, "+" [R"+(sIndex+1)+"]= "+role+",";
			} else{
				characterizedString +=",\n[CL"+(sIndex)+"] = "+clauseList.get(clauseIndex).getString()+", [R"+(sIndex+1)+"]= "+role+",";
				if(roleTokenList.size()>roleIndex)
					roleIndex++;
				clauseIndex++;
			}
			
			
			boolean first = true;
			
			while(conceptNext(conceptIndex)){
				
				if(!first)
					characterizedString +=" [CC] ";
				else
					first = false;
				
				String tempInput = removeTHE(conceptTokenList.get(conceptIndex).getString());
				characterizedString +=" ["+extractAndPrintQuantifier(tempInput,"[IQ]")+" "+printModifier(tempInput,"[IM]")+" [I"+sIndex+"_"+(conceptSub)+"] = "+withoutModifierAndQuantifier(tempInput)+"]";
				inputMap.add("[I"+sIndex+"_"+(conceptSub)+"="+tempInput+"]");

				conceptSub++;
				conceptIndex++;
			}
			
			conceptSuper++;
			sIndex++;
		}
	}
	
	private String withoutModifierAndQuantifier(String value) {
		for(String s:value.split(" ")){
			if(s.endsWith(NOUN_MODIFIER_TAG) || Quantifier.isQuantifier(s)){
				value = value.replace(s, "");
				log.trace("Quantifier Or Modifier:"+s);
			}
			else
				log.trace("Not Quantifier or Modifier:"+s);
		}
		return value;
	}

	private String extractAndPrintQuantifier(String value, String templateLable) {
		String quantifierString = "";
		boolean hasQuantifier = false;
		for(String s : value.trim().split(" ")){
			if(Quantifier.isQuantifier(s)){
				hasQuantifier = true;
				quantifierString += s+" ";
			}
		}
		if(hasQuantifier)
			quantifierString = templateLable + " = "+ quantifierString;
		return quantifierString;
	}

	private String removeTHE(String input) {
		if(input==null || input.isEmpty() || input.length()==0)
			return input;
		if(input.trim().startsWith("the "))
			input = input.trim().replace("the ", "");
		return input;
	}

	
	private boolean conceptNext(int conceptIndex) {
		
		boolean clausesEnded = clauseList.size()<=clauseIndex;
		boolean rolesEnded = roleTokenList.size()<=roleIndex;
		
		if(conceptTokenList.size()<=conceptIndex)
			 return false;
		
		if(!clausesEnded && conceptTokenList.get(conceptIndex).getIndex()>clauseList.get(clauseIndex).getIndex())
			return false;
		
		if(!rolesEnded && conceptTokenList.get(conceptIndex).getIndex()>roleTokenList.get(roleIndex).getIndex())
			return false;
		 
		return true;
	}

	private boolean clausalStructureWithoutClause() {
		boolean clausesEnded = clauseList.size()<=clauseIndex;
		boolean rolesEnded = roleTokenList.size()<=roleIndex;
		
		if(clausesEnded)
			return true;
		
		if(rolesEnded && !clausesEnded)
			return false;
		
		if(clauseList.get(clauseIndex).getIndex() <= roleTokenList.get(roleIndex).getIndex())
			return false;
		else
			return true;
		
	}

	private boolean hasImplicitDesire(){
		
		if(clauseList.size()==0){ /*Simple Query Case*/
			return (conceptTokenList.size()==1);
		} 
		else if(conceptTokenList.size()>=2){		/*Complex Query Case*/
			if(conceptTokenList.get(1).getIndex()<clauseList.get(0).getIndex()){
				return false;
			} else{
				return true;
			}
		} else{
			log.error("Error in finding Implicit Desire.");
			return false;
		}
		
	}

	private ArrayList<Clause> getClauseList() {
		ArrayList<Clause> clauseList = new ArrayList<>();	
		for(int i=0;i<tokens.size();i++){
			if(i!=whIndex && tokens.get(i).isClause()){
				clauseList.add(new Clause(tokens.get(i),i));
			}
		}		
		
		return clauseList;
	}

	private void splitDesireAndQuantifier() {
		Quantifier qf = new Quantifier(desire);
		desire = qf.getNonQuantifierNoun();
		desireQuantifier = qf.getQuantifier();
		log.trace("D:"+desire+"   "+"DQ:"+desireQuantifier);
	}
	
	private void splitInputAndQuantifier() {
		Quantifier qf = new Quantifier(input);
		input = qf.getNonQuantifierNoun();
		inputQuantifier = qf.getQuantifier();
	}

	private String getImplicitDesire(boolean isSimpleFit) {
		
		if(wh.equalsIgnoreCase("Where")){
			return "location("+input+")";
		} else if(wh.equalsIgnoreCase("When")){
			return "time("+input+")";
		} else if(wh.equalsIgnoreCase("What")){
			if(isSimpleFit && (inputQuantifier==null || inputQuantifier.isEmpty() || inputQuantifier.length()==0 || inputQuantifier.equalsIgnoreCase("the") || inputQuantifier.equalsIgnoreCase("a"))){
				return "definition("+input+")";
			} else{
				return "typeOf("+input+")";
			}
		} else if(wh.equalsIgnoreCase("who") || wh.equalsIgnoreCase("whom")){
			return "DataProperty (Person)";
		}
		else if(wh.toLowerCase().startsWith("how ")){
			String nextString = wh.split(" ")[1];
			if(nextString.equalsIgnoreCase("many")){
				return "count("+desire+")";
			} else if(nextString.equalsIgnoreCase("much")){
				return "quantity("+desire+")";
			} else {
				return "DataProperty("+nextString+")";
			}
		}
		
		return null;
	}

	private void resetTemplate() {
		roleTokenList.clear();
		conceptTokenList.clear();
		inputMap.clear();
		wh="";
		relation1="";
		desire="";
		relation2="";
		input="";
		characterizedString="";
		//conceptCount=0;
	}

	private void fillRoleAndConceptList() {	
		
		// If query starts with "WP"
		if(tokens.get(0).isWP()){
			wh = tokens.get(0).getString();
			whIndex = 0;
			for(int i=1;i<tokens.size();i++){
				if(isConcept(tokens.get(i)))
					conceptTokenList.add(new Concept(tokens.get(i),i));
				
				// Why Not else-if?? Can a token be both concept and role?
				else if(isRole(tokens.get(i))){
					roleTokenList.add(new Role(tokens.get(i),i));	
					
				}
				
				else if(tokens.get(i).isREL1()){
					relation1 = tokens.get(i).getString();
				}
				//else if(!tokens.get(i).getString().equals("?"))
					//log.warn("Unknown Token Type Found. Cannot be placed in template. Token:"+tokens.get(i).getString()+" " + tokens.get(i).getTag());
			}
		}
		else{  /*When Query doesn't starts with "WP" */
			
			int whIndex=-1;
			
			/*Find WP index*/
			for(int i=0;i<tokens.size();i++){
				if(tokens.get(i).isWP()){
					whIndex = i;
					
					break;
				}
			}
			
			/*If WP index is found*/
			if(whIndex!=-1){
				wh = tokens.get(whIndex).getString();
				this.whIndex = whIndex;
				int index = whIndex+1;
				
				/* Find first concept after WP's index. 
				 * Add all the roles between first concept and WP's index into role list
				 * */
				while(index<tokens.size() && !isConcept(tokens.get(index))){
					if(isRole(tokens.get(index))){
						roleTokenList.add(new Role(tokens.get(index),index));
					}
					index++;
				}
				/*If concept is found, add it to the concept list. This is out Desire(D)*/
				conceptTokenList.add(new Concept(tokens.get(index),index));

				/*Add the remaining roles and concepts after D into their respective lists*/
				for(int i=index+1;i<tokens.size();i++){
					if(isConcept(tokens.get(i))){
						conceptTokenList.add(new Concept(tokens.get(i),i));
					}
					
					// Why Not else-if?? Can a token be both concept and role?
					if(isRole(tokens.get(i))){
						roleTokenList.add(new Role(tokens.get(i),i));	
					}
					
					if(tokens.get(i).isREL1()){
						relation1 = tokens.get(i).getString();
					}
				}
				
				
				// Now go backwards from Wh index
				
				// TODO
			}
			
			else{
				// NEW this.whIndex???
			}
		}
	}
	
	private void mergeConsecutiveTokens() {
		
		//log.debug("Start RoleList:", roleTokenList.toString());
		for(int i=0;i<roleTokenList.size()-1;i++){
			if(roleTokenList.get(i).getIndex()==roleTokenList.get(i+1).getIndex()-1){
				int start = i;
				String text = roleTokenList.get(i).getString()+" "+roleTokenList.get(i+1).getString();
				i++;
				while(i<roleTokenList.size()-1 && roleTokenList.get(i).getIndex()==roleTokenList.get(i+1).getIndex()-1){
					text +=" "+roleTokenList.get(i+1).getString();
					i++;
				}
				roleTokenList.get(start).setToken(new QueryToken(text,"ROLE"));
				for(int j=start+1;j<=i;j++)
						roleTokenList.remove(start+1);
				
				i=start;
			}
		}
		//log.debug("End RoleList:", roleTokenList.toString());

		
		/*for(int i=0;i<conceptTokenList.size()-1;i++){
			if(conceptTokenList.get(i).getIndex()==conceptTokenList.get(i+1).getIndex()-1  
					&& noNERinvolved(conceptTokenList.get(i).getToken(),conceptTokenList.get(i+1).getToken())){
				int start = i;
				String text = conceptTokenList.get(i).getString()+" "+conceptTokenList.get(i+1).getString();
				i++;
				while(i<conceptTokenList.size()-1 && conceptTokenList.get(i).getIndex()==conceptTokenList.get(i+1).getIndex()-1
						&& noNERinvolved(conceptTokenList.get(i).getToken(),conceptTokenList.get(i+1).getToken())){
					text +=" "+conceptTokenList.get(i+1).getString();
					i++;
				}
				conceptTokenList.get(start).setToken(new QueryToken(text,"CONCEPT"));
				for(int j=start+1;j<=i;j++)
					conceptTokenList.remove(start+1);
			}
		}*/
		
	}

	private boolean noNERinvolved(QueryToken token1, QueryToken token2) {
		if(token1.isNNPNER() || token2.isNNPNER())
			return false;
		else
			return true;
	}

	public static boolean isConcept(QueryToken token) {
		if(token.isNounVariant() || token.isRB() || token.isCD() || token.isVBG() || token.isAdjVariant())
			return true;
		else
			return false;
	}
	
	public static boolean isRole(QueryToken token) {
		if(token.isRoleTagged() || token.isTO() || token.isIN() || token.isPRP() ||(token.isVerbVariant() && !token.isVBG()))
			return true;
		else
			return false;
	}
	
	private String toStringFromTokenList(ArrayList<QueryToken> tokenList) {
		String result = "";
		for(QueryToken token: tokenList){
			result +=token.getString()+" ";
		}
		return result.trim();
	}
	
	public String getInputs(){
		if(inputMap!=null)
			return inputMap.toString();
		else
			return null;
	}

}