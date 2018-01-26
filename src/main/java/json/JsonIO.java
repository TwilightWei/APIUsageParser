package main.java.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

import main.java.hashmap.APIHashMap;

public class JsonIO {
	public JSONObject jsonObj = new JSONObject();
	
	public void buildAPIData(APIHashMap apiHashMap) {
		addInt(apiHashMap.apiCount, "count");
		addString(apiHashMap.apiLevel, "level");
		addString(apiHashMap.apiPackage, "package");
	}
	
	private void addString(HashMap<String, String> hashmap, String key) {
		for(Entry<String, String> entry : hashmap.entrySet()) {
			jsonObj.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
	}
	
	private void addInt(HashMap<String, Integer> hashmap, String key) {
		for(Entry<String, Integer> entry : hashmap.entrySet()) {
			jsonObj.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
	}
}
