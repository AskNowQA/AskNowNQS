package notUsed;

import java.net.URI;

public class TryURI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(getEntity("http://dbpedia.org/resource/New_York_City_Fire_Department"));
		
	}
  
	public static String getEntity (String uriStirng){
		URI uri = URI.create(uriStirng);
		String path = uri.getPath();
		path = path.substring(path.lastIndexOf('/')+1);
		return path;
	}
	
	
}
