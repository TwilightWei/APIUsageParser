package main.java.node;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import main.java.hashmap.APIHashMap;

//Get variable declaration usage
public class GenericClassUsage {
	
	// TOFIX: Unknown exceptions
	public void find(TypeDeclaration node, APIHashMap apiHashMap) {
		if(node.getSuperclassType() != null) {
			try {
				String type = node.getSuperclassType().resolveBinding().getQualifiedName();
				type = replace(type);
				recurse(type, apiHashMap);
			} catch(NullPointerException e) {
				System.out.println("\n---------TypeDeclaration---------");
				System.out.println("Node: " + node);
			} 
		} 
	} 
	
	// TOFIX: Unknown exceptions
	public void find(MethodDeclaration node, APIHashMap apiHashMap) {
		if(node.getReturnType2() != null) {
			try {
				String type = node.getReturnType2().resolveBinding().getQualifiedName();
				type = replace(type);
				recurse(type, apiHashMap);
			} catch(NullPointerException e) {
				System.out.println("\n---------MethodDeclaration---------");
				System.out.println("Node: " + node);
				System.out.println("Node: " + node.getReturnType2());
			} 
		} 
	} 
	
	public void find(VariableDeclarationStatement node, APIHashMap apiHashMap) {
		try {
			String type = node.getType().resolveBinding().getQualifiedName();
			type = replace(type);
			recurse(type, apiHashMap);
		} catch(NullPointerException e) {
			System.out.println("\n---------VariableDeclarationStatement---------");
			System.out.println("Node: " + node);
			System.out.println("Node: " + node.getType());
		} 
	} 
	
	public void find(FieldDeclaration node, APIHashMap apiHashMap) {
		try {
			String type = node.getType().resolveBinding().getQualifiedName();
			type = replace(type);
			recurse(type, apiHashMap);
		} catch(NullPointerException e) {
			System.out.println("\n---------FieldDeclaration---------");
			System.out.println("Node: " + node);
			System.out.println("Node: " + node.getType());
		} 
	} 
	
	// TOFIX: Unknown exceptions
	public void find(SingleVariableDeclaration node, APIHashMap apiHashMap) {
		try {
			String type = node.getType().resolveBinding().getQualifiedName();
			type = replace(type);
			recurse(type, apiHashMap);
		} catch(NullPointerException e) {
			System.out.println("\n---------SingleVariableDeclaration---------");
			System.out.println("Node: " + node);
			System.out.println("Node: " + node.getType());
		} 
	} 
	
	// TOFIX: Weird usage: org.apache.cxf.common.util.SortedArraySet<T>.SASIterator<T>
	// It is local constructor which extends class in framework
	public void find(ClassInstanceCreation node, APIHashMap apiHashMap) {
		try {
			String type = node.getType().resolveBinding().getQualifiedName();
			type = replace(type);
			recurse(type, apiHashMap);
		} catch(NullPointerException e) {
			System.out.println("\n---------ClassInstanceCreation---------");
			System.out.println("Node: " + node);
			System.out.println("Node: " + node.getType());
			System.out.println("Node: " + node.getType().resolveBinding());
		} 
	} 
	
	public void find(MethodInvocation node, APIHashMap apiHashMap) {
		MethodInvocation classNode = express(node);
		if(classNode.getExpression() != null && classNode.getExpression().resolveTypeBinding() != null) {
			try {
				String type = classNode.getExpression().resolveTypeBinding().getQualifiedName();
				type = replace(type);
				recurse(type, apiHashMap);
			} catch(NullPointerException e) {
				System.out.println("\n---------MethodInvocation---------");
				//System.out.println("Node: " + classNode);
				//System.out.println("Node: " + classNode.getExpression());
				System.out.println("Name: " + classNode.getExpression().getClass().getName());
			}
		} 
	} 
	
	public MethodInvocation express(MethodInvocation node) {
		if(node.getExpression() instanceof MethodInvocation) {
			node = (MethodInvocation) node.getExpression();
			return express(node);
		} else {
			return node;
		}
	}
	
	public String replace(String string) {
		string = string.replace(" super ", "");
		string = string.replace("? extends ", "");
		string = string.replace("?", "");
		string = string.replace("<>", "");
		return string;
	} 
	
	public int countSub(String string, String sub) {
		int count = 0;
		for(int index = string.indexOf(sub);
				index >= 0;
				index = string.indexOf(sub, index + 1)) {
			count++;
		} 
		return count;
	} 
	
	public ArrayList<String> slice(String type) {
		ArrayList<String> rightTypes = new ArrayList<String>();
		int leftIndex = 0;
		for(int rightIndex = type.indexOf(",");
				rightIndex >= -1;
				rightIndex = type.indexOf(",", rightIndex + 1)) {
			
			if(rightIndex == -1) {
				rightTypes.add(type.substring(leftIndex, type.length()));
				break;
			} else {
				String leftSub = type.substring(leftIndex, rightIndex);
				int leftCount = countSub(leftSub, "<");
				int rightCount = countSub(leftSub, ">");
				if(leftCount==rightCount) {
					rightTypes.add(leftSub);
					leftIndex = rightIndex+1;
				} 
			} 
		} 
		return rightTypes;
	} 
	
	public void recurse(String type, APIHashMap apiHashMap) {
		
		if(type.contains("<") && type.contains(">")) {	
			for(int i = type.indexOf(".");
					i >= 0;
					i = type.indexOf(".", i + 1)) {
				if(type.substring(i-1, i).equals(">")) {
					type = type.substring(0, i);
					break;
				}
			}
			
			String leftType = type.substring(0, type.indexOf("<"));
			if(type.length() > 0) {
				//apiHashMap.countAPI(leftType);
				apiHashMap.addAPI(leftType, "Class");
			}

			ArrayList<String> rightTypes = slice(type.substring(type.indexOf("<") + 1, type.lastIndexOf(">")));
			for(String rightType:rightTypes) {
				recurse(rightType, apiHashMap);
			} 
		} else {
			if(type.length() > 0) {
				//apiHashMap.countAPI(type);
				apiHashMap.addAPI(type, "Class");
			}
		} 
	}
} 
