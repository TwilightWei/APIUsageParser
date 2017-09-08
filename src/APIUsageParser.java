import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ast.CustomASTParser;
import config.ConfigReader;
import file.FileParser;
import hashmap.APIHashMap;

public class APIUsageParser {
	
	public static void main(String[] args) {
		APIHashMap apiHashMap = new APIHashMap();
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
			astParser.parse(javaCode, javaFile, apiHashMap);
		}
		
		System.out.println("Finished parsing AST");
	}	
}
