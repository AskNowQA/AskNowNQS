package org.aksw.asknow.nqs;

import lombok.Getter;
import lombok.Setter;

public class Role {
	@Getter @Setter
	private QueryToken token;
	@Getter
	private int index; //Token index in the sentence
	
	public Role(QueryToken token, int index) {
		this.token = token;
		this.index = index;
	}
	
	public String getString() {
		return token.getString();
	}
	
	@Override public String toString(){
		return token.toStringWithoutTags();
	}
}
