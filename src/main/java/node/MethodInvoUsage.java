package main.java.node;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import main.java.hashmap.APIHashMap;

public class MethodInvoUsage {
	public void find(MethodInvocation node, APIHashMap methodHashMap){
		if(node.getExpression() != null && node.resolveMethodBinding() != null) {
			//System.out.println("\n---------MethodInvocation---------");
			//System.out.println("Node: " + node);
			//System.out.println("Node: " + node.getExpression());
			String classLevel = node.getExpression().resolveTypeBinding().getQualifiedName();
			if(classLevel.contains("<")) {
				classLevel = classLevel.substring(0, classLevel.indexOf("<"));
			}
			String meth = classLevel + "." +  node.resolveMethodBinding().getName();
			meth += "(";
			ITypeBinding[] paras = node.resolveMethodBinding().getParameterTypes();
			for(int i=0; i<paras.length; i++){
				if(i==0){
					meth += paras[i].getName();
				}
				else{
					meth += ", " + paras[i].getName();
				}
			}
			meth += ")";
			//System.out.println("Method: "+ meth);
			methodHashMap.countAPI(meth);
			methodHashMap.addAPI(meth, "Methods");
		}
	}
}
