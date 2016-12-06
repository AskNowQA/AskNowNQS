package utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendRequest {
	
	/*
	 * This class sends post and get request to the desired url with url parameters 
	 * and retruns the response in string format.
	 * 
	 * 
	 * */
	private final String USER_AGENT = "Mozilla/5.0";
	
	
	public String sendPostRequest(String microservice_url,String microservice_url_Parameters) throws Exception{
		String url = microservice_url;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setConnectTimeout(5000);		//timeout in 5 seconds
		con.setReadTimeout(5000); // Read timeout set to 5 seconds
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("content-type","application/json");

		//		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";//this is where data is passed. 	
		String urlParameters = microservice_url_Parameters;
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

//		int responseCode = con.getResponseCode();
//		System.out.println("response code is " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
