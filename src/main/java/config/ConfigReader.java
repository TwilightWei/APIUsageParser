package main.java.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ConfigReader {
	String configPath = new String();
	
	public void setConfig(String configPath) {
		this.configPath = configPath;
	}
	
	public ArrayList<String> getValAsStringArrayList(String key) {
		String value = getValue(key);
		ArrayList<String> valueList;
		valueList = new ArrayList<String>(Arrays.asList(value.split(",")));
		return valueList;
	}
	
	public String getValAsString(String key) {
		String value = getValue(key);
		return value;
	}
	
	public String getValue(String key) {
		String value = new String();
		try {
			File configFile = new File(configPath);
		    FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
		    props.load(reader);
		    value = props.getProperty(key);
		    reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
