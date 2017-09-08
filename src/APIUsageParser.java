import java.io.File;
import java.util.ArrayList;
import config.ConfigReader;
import files.FileParser;

public class APIUsageParser {
	
	public static void main(String[] args) {
		FileParser fileParser = new FileParser();
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> classPaths = new ArrayList<String>();
		ArrayList<String> libPaths = new ArrayList<String>();
		ArrayList<String> javaPaths = new ArrayList<String>();
		String source = new String();
		
		final String configPath = "D:\\Users\\user\\git\\APIUsageParser\\src\\config.properties";
		
		source = configReader.readConfig(configPath, "source");
		classPaths.add(configReader.readConfig(configPath, "classpath"));
		libPaths = fileParser.getFilePaths(source, "jar");
		libPaths.addAll(classPaths);
		javaPaths = fileParser.getFilePaths(source, "java");
		
		for(String javaPath : javaPaths){
			String javaCode = fileParser.getFileContent(javaPath);
		}
	}	
}
