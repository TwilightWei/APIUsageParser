import java.io.IOException;
import java.util.ArrayList;
import config.ConfigReader;
import files.APIFileIO;
import files.FileParser;

public class APIUsageParser {
	
	public static void main(String[] args) {
		FileParser fileParser = new FileParser();
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> classPaths = new ArrayList<String>();
		ArrayList<String> libPaths = new ArrayList<String>();
		ArrayList<String> JavaPaths = new ArrayList<String>();
		String source = new String();
		
		final String configPath = "D:\\Users\\user\\git\\APIUsageParser\\src\\config.properties";
		
		source = configReader.readConfig(configPath, "source");
		classPaths.add(configReader.readConfig(configPath, "classpath"));
		libPaths = fileParser.getFilePaths(source, "jar");
		libPaths.addAll(classPaths);
		JavaPaths = fileParser.getFilePaths(source, "java");
		
		
		
	}	
}
