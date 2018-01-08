package main.java;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import main.java.ast.CustomASTParser;
import main.java.config.ConfigReader;
import main.java.file.Folder;
import main.java.file.FileParser;
import main.java.hashmap.APIHashMap;
import main.java.json.JsonIO;

public class APIUsageParser {
	
	public static void main(String[] args) {
		final String configPath = "D:\\Users\\user\\git\\APIUsageParser\\src\\config.properties";
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> sourceList = new ArrayList<String>();
		
		//Important folder path
		String dependencyFolder = "\\Dependencies";
		String outputFolder = "\\APIUsage-AllJAR";
		
		configReader.setConfig(configPath);
		sourceList = configReader.getValAsStringArrayList("sources");
		
		for(String source:sourceList) {
			APIHashMap apiHashMap = new APIHashMap();
			APIHashMap methodHashMap = new APIHashMap();
			APIHashMap fieldHashMap = new APIHashMap();
			
			FileParser fileParser = new FileParser();
			ArrayList<String> classPaths = new ArrayList<String>();
			ArrayList<String> libPaths = new ArrayList<String>();
			List<File> javaFiles = new ArrayList<File>();
			String[] classPathArray;
			String[] sourceArray;
			JsonIO classIO = new JsonIO();
			JsonIO methodIO = new JsonIO();
			JsonIO fieldIO = new JsonIO();
			Folder file;
			
			ArrayList<String> classList = new ArrayList<String>();
			ArrayList<String> methodList = new ArrayList<String>();
			ArrayList<String> fieldList = new ArrayList<String>();
			
			file = new Folder(source);
			file.createFolder(dependencyFolder);		
			
			libPaths = fileParser.getFilePaths(source + dependencyFolder, "jar");
			classPaths.add(configReader.getValue("classpath"));
			classPaths.addAll(libPaths);
			
			javaFiles = fileParser.getFiles(source, "java");
			
			classPathArray = classPaths.toArray(new String[0]);
			sourceArray = new String[] {source};
			
			for(File javaFile : javaFiles) {
				String javaCode = fileParser.getFileContent(javaFile.toString());
				CustomASTParser astParser = new CustomASTParser(classPathArray, sourceArray);
				astParser.parse(javaCode, javaFile, apiHashMap, methodHashMap, fieldHashMap);
			}
			System.out.println("Finished parsing AST");
			
			classIO.addInt(apiHashMap.apiCount, "count");
			methodIO.addInt(methodHashMap.apiCount, "count");
			fieldIO.addInt(fieldHashMap.apiCount, "count");
			
			file.clearFolder(outputFolder);
			file.writeString(outputFolder+"\\Class", classIO.json.toString());
			file.writeString(outputFolder+"\\Method", methodIO.json.toString());
			file.writeString(outputFolder+"\\Field", fieldIO.json.toString());
			
			classList.clear();
			for(Entry<String, Integer> entry : apiHashMap.apiCount.entrySet()) {
				classList.add(entry.getKey());
			}
			Collections.sort(classList);
			file.writeArrayList(outputFolder+"\\ClassList", classList);
			
			methodList.clear();
			for(Entry<String, Integer> entry : methodHashMap.apiCount.entrySet()) {
				methodList.add(entry.getKey());
			}
			Collections.sort(methodList);
			file.writeArrayList(outputFolder+"\\MethodList", methodList);
			
			fieldList.clear();
			for(Entry<String, Integer> entry : fieldHashMap.apiCount.entrySet()) {
				fieldList.add(entry.getKey());
			}
			Collections.sort(fieldList);
			file.writeArrayList(outputFolder+"\\FieldList", fieldList);
			
			System.out.println("Finished writing file");
		}
	}	
}
