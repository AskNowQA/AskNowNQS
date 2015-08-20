package org.aksw.qct;


public class QctTemplate {
	String nlQuery ="";
	String keyword ="";
	String qct ="";
	public CharSequence getDesireBrackets;
	
	QctTemplate(){//constructor to  get values from QctPool.xml
		nlQuery="How many employees does Google have?";
		keyword="Google, employees";
		qct ="[WH] = How many, [R1] = , [D] = count(employees), [R2] = does have, [I] = Google]";
	}
	
	
	String getNLQuery(){
		return nlQuery;
	}
	
	String getKeyword(){
		return keyword;
	}
	
	String getQCT(){
		return qct;
	}
	
	public String getDesire(){
		int i, j;
		i = qct.lastIndexOf("[D] =")+5;
		j = qct.indexOf(']', i);
		return qct.substring(i, j);
	}
	
	String getRelation1(){
		int i, j;
		i = qct.lastIndexOf("[R2] =")+5;
		j = qct.indexOf(']', i);
		return qct.substring(i, j);
	}
	public String getInput(){
		int i, j;
		i = qct.lastIndexOf("[I] =")+5;
		j = qct.indexOf(']', i);
		return qct.substring(i, j);
	}
	
	String getAll(){
		return nlQuery + "\n" + keyword + "\n" + qct;
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

