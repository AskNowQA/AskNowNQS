package annotation;

import phrase.phrase;
import token.token;

public class relationAnnotationToken {

	
	private token tok;
	private String uri;
	private float score ;
	private boolean isIncomingProperty = false;
	private boolean isOutgoingProperty = false;
	private String propertyLabel;
	private phrase ph;
	
	
	public boolean isIncomingProperty() {
		return isIncomingProperty;
	}
	public void setIncomingProperty(boolean isIncomingProperty) {
		this.isIncomingProperty = isIncomingProperty;
	}
	public boolean isOutgoingProperty() {
		return isOutgoingProperty;
	}
	public void setOutgoingProperty(boolean isOutgoingProperty) {
		this.isOutgoingProperty = isOutgoingProperty;
	}
	public String getPropertyLabel() {
		return propertyLabel;
	}
	public void setPropertyLabel(String propertyLabel) {
		this.propertyLabel = propertyLabel;
	}
	public phrase getPh() {
		return ph;
	}
	public void setPh(phrase ph) {
		this.ph = ph;
	}
	public token getTok() {
		return tok;
	}
	public void setTok(token tok) {
		this.tok = tok;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
}
