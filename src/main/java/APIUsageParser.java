package main.java;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.java.ast.CustomASTParser;
import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.file.FileParser;
import main.java.hashmap.APIHashMap;
import main.java.json.JsonIO;

public class APIUsageParser {
	
	public static void main(String[] args) {
		APIHashMap apiHashMap = new APIHashMap();
		APIHashMap methodHashMap = new APIHashMap();
		APIHashMap fieldHashMap = new APIHashMap();
		FileParser fileParser = new FileParser();
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> classPaths = new ArrayList<String>();
		ArrayList<String> libPaths = new ArrayList<String>();
		List<File> javaFiles = new ArrayList<File>();
		String source = new String();
		String[] classPathArray;
		String[] sourceArray;
		
		final String configPath = "D:\\Users\\user\\git\\APIUsageParser\\src\\config.properties";
		
		source = configReader.readConfig(configPath, "source");
		classPaths.add(configReader.readConfig(configPath, "classpath"));
		libPaths = fileParser.getFilePaths(source, "jar");
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
		
		// TOFIX Seperate Class, Method, Field
		JsonIO classIO = new JsonIO();
		classIO.addString(apiHashMap.apiLevel, "level");
		classIO.addInt(apiHashMap.apiCount, "count");
		
		JsonIO methodIO = new JsonIO();
		methodIO.addString(methodHashMap.apiLevel, "level");
		methodIO.addInt(methodHashMap.apiCount, "count");
		
		JsonIO fieldIO = new JsonIO();
		fieldIO.addString(fieldHashMap.apiLevel, "level");
		fieldIO.addInt(fieldHashMap.apiCount, "count");
		
		FileIO file = new FileIO(source);
		file.clearFolder("\\APIUsage");
		file.writeString("\\APIUsage\\Class", classIO.json.toString());
		file.writeString("\\APIUsage\\Method", methodIO.json.toString());
		file.writeString("\\APIUsage\\Field", fieldIO.json.toString());
		System.out.println("Finished writing file");
	}	
}
