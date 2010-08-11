package de.bomzhi.mockitoviz.visitor;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;

public class BindingHandler {
	public String getTypeName(Type type) {
		ITypeBinding typeBinding = type.resolveBinding();
		return getName(typeBinding);
	}

	public String getTypeName(Expression expression) {
		ITypeBinding typeBinding = expression.resolveTypeBinding();
		return getName(typeBinding);
	}

	private String getName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		return typeBinding.getName();
	}

	public boolean isStaticMockitoMethod(MethodInvocation node) {
		IMethodBinding methodBinding = node.resolveMethodBinding();
		if(methodBinding==null){
			return false;
		}
		int modifiers = methodBinding.getModifiers();
		if(!Modifier.isStatic(modifiers)){
			return false;
		}
		ITypeBinding declaringClass = methodBinding.getDeclaringClass();
		String qualifiedName = declaringClass.getQualifiedName();
		return qualifiedName.equals("org.mockito.Mockito");
	}
}
