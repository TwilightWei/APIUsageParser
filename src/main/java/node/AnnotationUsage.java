package main.java.node;

import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import main.java.hashmap.APIHashMap;

/*
 * Get annotations
 */
public class AnnotationUsage {
	
	public void find(MethodDeclaration node, APIHashMap apiHashMap){
		try{
			if(node.resolveBinding() != null) {
				if(node.resolveBinding().getAnnotations().length > 0){
					for(IAnnotationBinding item:node.resolveBinding().getAnnotations()){
						String type = item.getAnnotationType().getQualifiedName();
						//apiHashMap.countAPI(type);
						apiHashMap.addAPI(type, "Class");
					}
				}
			}
		} catch(NullPointerException e){
			System.out.println("\n---------MethodDeclaration---------");
			System.out.println("Node: " + node);
			System.out.println("Node: " + node.resolveBinding());
		}
	}
}
