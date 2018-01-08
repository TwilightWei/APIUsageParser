package main.java.node;

import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.QualifiedName;

import main.java.hashmap.APIHashMap;

// TOFIX: add name
public class FieldAccessUsage {
	public void find(QualifiedName node, APIHashMap apiHashMap){
		if(node.resolveBinding() != null) {
			if(node.resolveBinding().getClass().getName().equals("org.eclipse.jdt.core.dom.VariableBinding")){
				if(((IVariableBinding) node.resolveBinding()).getDeclaringClass() != null) {
					//System.out.println("\n---------QualifiedName---------");
					//System.out.println("Node: " + node);
					//System.out.println("Tyep: " + ((IVariableBinding) node.resolveBinding()).getDeclaringClass().getQualifiedName());
					//System.out.println(((IVariableBinding) node.resolveBinding()).getName());
					String fieldAccess = ((IVariableBinding) node.resolveBinding()).getDeclaringClass().getQualifiedName()+"."+((IVariableBinding) node.resolveBinding()).getName();
					apiHashMap.countAPI(fieldAccess);
				}
			}
		}
	}
}
