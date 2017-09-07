package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class FileParser {
	
	public ArrayList<String> getFilePaths(String rootPath, String extension) {
		ArrayList<String> filePaths = new ArrayList<String>();
		File rootFolder = new File(rootPath);
		List<File> files = getFiles(rootFolder, extension);
		
		for(File file : files) {
			filePaths.add(file.toString());
		}
		
		return filePaths;
	}
	
	// get all path of files in root with specific extension
	public List<File> getFiles(File folder, String extension) {
		List<File> filePaths = new ArrayList<File>();

	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	filePaths.addAll(getFiles(fileEntry, extension));
	        }
	        else if (FilenameUtils.getExtension(fileEntry.toString()).endsWith(extension)) {
	        	filePaths.add(fileEntry);
	        }
	    }
	    
		return filePaths;
	}

}
