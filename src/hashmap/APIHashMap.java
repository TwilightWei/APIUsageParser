package hashmap;

import java.util.HashMap;

public class APIHashMap {
	public HashMap<String, Integer> apiCount = new HashMap<String, Integer>();
	public HashMap<String, String> apiLevel = new HashMap<String, String>();
	
	public void countAPI(String key){
		if(apiCount.containsKey(key)){
			int value = apiCount.get(key);
			apiCount.put(key, ++value);
		} else{
			apiCount.put(key, 1);
		}
	}
	
	public void addAPI(String key, String level){
		if(!apiLevel.containsKey(key)){
			apiLevel.put(key, level);
		} else{
			if(!apiLevel.get(key).equals(level)){
				System.out.println(key);
				System.err.println("API level consistency!!!: " + apiLevel.get(key));
			}
		}
	}
	
	public void print(String key){
		System.out.println(apiCount.get(key));
	}
}