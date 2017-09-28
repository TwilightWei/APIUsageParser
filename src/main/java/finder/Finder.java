package main.java.finder;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import main.java.hashmap.APIHashMap;
import main.java.node.GenericClassUsage;
import main.java.node.MethodInvoUsage;
import main.java.node.AnnotationUsage;
import main.java.node.ConstructorInvoUsage;
//import main.java.node.ConstructorInvoUsage;
//import main.java.node.FieldAccessUsage;
//import main.java.node.MethodInvoUsage;
import main.java.node.FieldAccessUsage;

public class Finder extends ASTVisitor {
	private APIHashMap apiHashMap;
	private APIHashMap methodHashMap;
	private APIHashMap fieldHashMap;
	
	// Call apiHashMap by reference
	public Finder(APIHashMap apiHashMap, APIHashMap methodHashMap, APIHashMap fieldHashMap){
		this.apiHashMap = apiHashMap;
		this.methodHashMap = methodHashMap;
		this.fieldHashMap = fieldHashMap;
	}
	
	public boolean visit(TypeDeclaration node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		genericUsage.find(node, apiHashMap);
		return true;
	}
	
	public boolean visit(MethodDeclaration node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		AnnotationUsage annotationUsage = new AnnotationUsage();
		genericUsage.find(node, apiHashMap);
		annotationUsage.find(node, apiHashMap);
		return true;
	}
	
	public boolean visit(VariableDeclarationStatement node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		genericUsage.find(node, apiHashMap);
		return true;
	}
	
	public boolean visit(FieldDeclaration node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		genericUsage.find(node, apiHashMap);
		return true;
	}
	
	public boolean visit(SingleVariableDeclaration node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		genericUsage.find(node, apiHashMap);
		return true;
	}
	
	// This may be constructor invocation by getting method binding
	public boolean visit(ClassInstanceCreation node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		ConstructorInvoUsage constructorInvoUsage = new ConstructorInvoUsage();
		genericUsage.find(node, apiHashMap);
		constructorInvoUsage.find(node, methodHashMap);
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		GenericClassUsage genericUsage = new GenericClassUsage();
		MethodInvoUsage methodInvoUsage = new MethodInvoUsage();
		genericUsage.find(node, apiHashMap); // Replace StaticClassUsage
		methodInvoUsage.find(node, methodHashMap);	
		return true;
	}

	public boolean visit(QualifiedName node){
		FieldAccessUsage fieldAccessUsage = new FieldAccessUsage();
		fieldAccessUsage.find(node, fieldHashMap);
		return true;
	}
}