package ast;
import java.io.File;
import java.util.Map;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

//import ast.finder.AllFinder;
import hashmap.APIHashMap;

 
public class CustomASTParser {
	private static String[] classpaths;
	private static String[] sources;
	
	public CustomASTParser(String[] classpaths, String[] sources) {
		CustomASTParser.classpaths = classpaths;
		CustomASTParser.sources = sources;
	}
	
	/*
	 * Set up environment, create AST and parse it
	 */
	public void parse(String javaCode, File filePath, APIHashMap apiHashMap) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		
		Map<String, String> options = JavaCore.getOptions();
		
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		parser.setUnitName(filePath.getName());
		parser.setCompilerOptions(options);
		parser.setEnvironment(classpaths, sources, new String[] {"UTF-8"}, true);
		parser.setSource(javaCode.toCharArray());
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		if(!cu.getAST().hasBindingsRecovery()) {
			System.out.println("Binding not activated!");
		}
		
		//AllFinder allFinder = new AllFinder(apiHashMap);
		//cu.accept(allFinder);
	}
}