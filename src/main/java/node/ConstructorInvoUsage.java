package main.java.node;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ITypeBinding;

import main.java.hashmap.APIHashMap;

public class ConstructorInvoUsage {
	public void find(ClassInstanceCreation node, APIHashMap methodHashMap) {
		if(node.getType() != null && node.getType().resolveBinding() != null) {
			//System.out.println("\n---------ClassInstanceCreation---------");
			//System.out.println("Node: " + node);
			//System.out.println("Node: " + node.getType());
			String classLevel = node.getType().resolveBinding().getQualifiedName();
			if(classLevel.contains("<")) {
				classLevel = classLevel.substring(0, classLevel.indexOf("<"));
			}
			String className = node.getType().resolveBinding().getName();
			if(className.contains("<")) {
				className = className.substring(0, className.indexOf("<"));
			}
			String cons = classLevel + "." + className;
			if (node.resolveConstructorBinding() != null) {
				cons += "(";
				ITypeBinding[] paras = node.resolveConstructorBinding().getParameterTypes();
				for(int i=0; i<paras.length; i++) {
					if(i==0) {
						cons += paras[i].getName();
					} else {
						cons += ", " + paras[i].getName();
					}
				}
				cons += ")";
			} else {
				cons += "()";
			}
			//System.out.println(cons);
			//methodHashMap.countAPI(cons);
			methodHashMap.addAPI(cons, "Method");
		}
	}
}
