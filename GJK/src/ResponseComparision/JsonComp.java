package ResponseComparision;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonComp {
	public boolean isJsonExp(String str) {
		try {
//			System.out.println(str);
			JSONObject obj = new JSONObject(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean compare(String str1, String str2) {
		JSONParser parser = new JSONParser();
		try {
//			org.json.simple.JSONObject o1 = (org.json.simple.JSONObject) parser.parse("{\"name\":\"2g\", \"class\":\"x\"}");
//			org.json.simple.JSONObject o2 = (org.json.simple.JSONObject) parser.parse("{\"class\":\"x\", \"name\":\"2g\"}");
			org.json.simple.JSONObject o1 = (org.json.simple.JSONObject) parser.parse(str1);
			org.json.simple.JSONObject o2 = (org.json.simple.JSONObject) parser.parse(str2);
			if(o1.equals(o2))
				return true;
			else
				return false;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			return false;
		}
	}
	
	
}
