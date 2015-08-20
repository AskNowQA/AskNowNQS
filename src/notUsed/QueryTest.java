package notUsed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryTest {
	//static 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Wh, R1 = null, Desire = null, R2 = null, Input = null;
		String QCTquery = "[WH] = Who, [R1] = was, [D] = vice president, [R2] = of, [I] = John F. Kennedy,";
		QCTquery = QCTquery.replace("[", "<");
		QCTquery = QCTquery.replace("] = ", ">");
		QCTquery = QCTquery.replace(",", "<,>");
		System.out.println(QCTquery);
		
		Wh = findTag("<WH>(.+?)<,>",QCTquery);
		R1 = findTag("<R1>(.+?)<,>",QCTquery);
		Desire = findTag("<D>(.+?)<,>",QCTquery);
		R2 = findTag("<R2>(.+?)<,>",QCTquery);
		Input = findTag("<I>(.+?)<,>",QCTquery);
		System.out.println(Wh+ R1+ Desire+ R2+ Input);
	}
	
	public static String findTag (String pattern1, String statement ){
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher matcher = pattern.matcher(statement);
		matcher.find();
		return matcher.group(1);
	}

}
