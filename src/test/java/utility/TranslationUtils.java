package utility;

import io.restassured.response.Response;

import org.json.JSONObject; 

public class TranslationUtils {
    public static String translateText(String text) {
       JSONObject obj = new JSONObject();
        
        obj.put("from", "es");
        obj.put("to", "en");
        obj.put("q", text);
        
    	Response response= BaseAPI.sendPostApiRequest("https://rapid-translate-multi-traduction.p.rapidapi.com", "/t", obj);
        String translatedText = response.getBody().asString();
        
		return translatedText.replace("[","").replace("]", "").replace("\"", "");
    }
}
