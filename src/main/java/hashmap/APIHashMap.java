package main.java.hashmap;

import java.util.ArrayList;
import java.util.HashMap;

public class APIHashMap {
	public HashMap<String, String> apiLevel = new HashMap<String, String>();
	public HashMap<String, Integer> apiCount = new HashMap<String, Integer>();
	public HashMap<String, String> apiPackage = new HashMap<String, String>();

	public void addAPI(String key, String level){	
		updateAPICount(key);
		setAPILevel(key, level);
		setAPIPackage(key, level);
	}
	
	public void a(HashMap<String, Integer> countMap, HashMap<String, Integer> calledCountMap) {
		
	}
	
	private void updateAPICount(String key){
		if(apiCount.containsKey(key)){
			int value = apiCount.get(key);
			apiCount.replace(key, ++value);
		} else{
			apiCount.put(key, 1);
		}
	}
	
	private void setAPILevel(String key, String level) {
		if(!apiLevel.containsKey(key)){
			apiLevel.put(key, level);
		} else{
			if(!apiLevel.get(key).equals(level)){
				System.out.println(key);
				System.err.println("API level consistency!!!: " + apiLevel.get(key));
			}
		}
	}
	
	private String getPackage(String key) {
		String pkg = "";
		int index = 0;
		char ch;
		String dot = ".";
		int dotIndex = key.indexOf(dot);
		if('.' != key.charAt(0) && !Character.isUpperCase(key.charAt(0))) {
			while(dotIndex >= 0 && dotIndex<key.length()-1) {
				ch = key.charAt(dotIndex+1);
				if(Character.isUpperCase(ch)) {
					index = dotIndex;
					break;
				}
			    dotIndex = key.indexOf(dot, dotIndex + 1);
			}
		}
		if(index>0) {
			pkg = key.substring(0, dotIndex);
		}
		return pkg;
	}
	
	private void setAPIPackage(String key, String level) {
		String pkg = "";
		String[] keyArray = key.split("\\.");
		
		if(!apiPackage.containsKey(key)) {
			if(level == "Class") {
				pkg = getPackage(key);
			} else if((level == "Method" || level == "Field") && keyArray.length>2) {
				pkg = getPackage(key);
			}
			apiPackage.put(key, pkg);
		}
	}
	
	public void removeInternalAPI() {
		ArrayList<String> internalAPI = new ArrayList<String>();
		for(String key : apiPackage.keySet()) {
			if(apiPackage.get(key).equals("")/* || apiPackage.get(key).contains("org.springframework.boot")*/) {
				internalAPI.add(key);
		    }
		}
		for(String key:internalAPI) {
			apiLevel.remove(key);
			apiCount.remove(key);
			apiPackage.remove(key);
		}
	}
}