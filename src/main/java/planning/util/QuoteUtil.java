package planning.util;

import org.json.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class QuoteUtil {
	
	public static String getQuote() throws Exception {
		HttpResponse<JsonNode> jsonResponse = Unirest.post("http://api.forismatic.com/api/1.0/")
				.field("method", "getQuote")
				.field("format", "json")
				.field("lang", "en")
				.asJson();
		
		JSONObject json = new JSONObject(jsonResponse);
		JSONArray arr = json.getJSONObject("body").getJSONArray("array");
		//return arr.get(0).toString();
		return ((JSONObject) arr.get(0)).getString("quoteText");
		
	}
}
