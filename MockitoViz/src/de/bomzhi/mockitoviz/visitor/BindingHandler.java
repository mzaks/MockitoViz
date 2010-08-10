package de.bomzhi.mockitoviz.visitor;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;


public class BindingHandler {
	public String getTypeName(Type type){
		ITypeBinding typeBinding = type.resolveBinding();
		return getName(typeBinding);
	}

	public String getTypeName(Expression expression) {
		ITypeBinding typeBinding = expression.resolveTypeBinding();
		return getName(typeBinding);
	}

	private String getName(ITypeBinding typeBinding) {
		if(typeBinding==null){
			return null;
		}
		return typeBinding.getName();
	}
}
