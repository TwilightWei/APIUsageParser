package main.java.json;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class JsonIO {
	public JSONObject json = new JSONObject();
	
	public void clearJson() {
		json = new JSONObject();
	}
	
	public JSONObject addString(HashMap<String, String> hashmap, String key) {
		for(Entry<String, String> entry : hashmap.entrySet()) {
			json.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
		return json;
	}
	
	public JSONObject addInt(HashMap<String, Integer> hashmap, String key) {
		for(Entry<String, Integer> entry : hashmap.entrySet()) {
			json.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
		return json;
	}
}
